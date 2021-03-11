package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Worker struct {
	id             int
	patient        bool
	doneTasks      int
	taskServer     *TaskServer
	itemServer     *ItemServer
	computers      [COMPUTERS]*ComputerImpl
	lengthRequests chan *LengthTasksRequest
	service        *ServiceServer
}

func (worker *Worker) goWorker() {
	for {
		task := worker.getTask()
		solution := worker.solveOnComputer(task)
		worker.doneTasks += 1
		worker.store(solution)
		worker.restAndWaitForRequest()
	}
}

func (worker *Worker) getTask() TaskType {
	request := &TaskRequest{
		task:   TaskType{},
		status: make(chan bool, 1),
	}

	worker.taskServer.readRequests <- request
	status := <-request.status
	if status == true {
		return request.task
	} else {
		worker.log("Can't get task")
		worker.restAndWaitForRequest()
		return worker.getTask()
	}

}

func (worker Worker) store(solution int) {
	request := &ItemRequest{
		item:   solution,
		status: make(chan bool, 1),
	}

	worker.itemServer.writeRequests <- request
	status := <-request.status

	if status == false {
		worker.log("Can't add solution")
		worker.restAndWaitForRequest()
		worker.store(solution)
	}
}

func (worker Worker) log(format string, a ...interface{}) {
	if Mode == LIVE_MODE {
		a = append([]interface{}{worker.id}, a...)
		fmt.Printf("Worker %d: "+format+"\n", a...)
	}
}

func (worker *Worker) restAndWaitForRequest() {
	select {
	case request := <-worker.lengthRequests:
		request.len <- worker.doneTasks
	case <-time.After(time.Duration(WORKER_BREAK_TIME) * time.Millisecond):
		//End of break time
	}
}

func (worker *Worker) solveOnComputer(task TaskType) int {
	idx := 0
	var solution *int
	for {
		if worker.patient {
			idx = rand.Intn(COMPUTERS)
			worker.log("Working on Computer #%d", idx)
			worker.computers[idx].Task <- task
			solution = <-task.Result
		} else {
			select {
			case worker.computers[idx].Task <- task:
				worker.log("Waiting for Computer #%d", idx)
				solution = <-task.Result
			case <-time.After(time.Duration(IMPATIENT_WORKER_TIME_WAIT) * time.Millisecond):
				worker.log("I'm gonna try other comp :(")
				idx = (idx + 1) % COMPUTERS
			}
		}
		if solution == nil {
			worker.service.ComputerToFix <- worker.computers[idx]
			worker.log("comp #%d is broken", worker.computers[idx].id)
		}else{
			return *solution
		}
	}
}

func randBool() bool {
	return rand.Float32() < 0.5
}
