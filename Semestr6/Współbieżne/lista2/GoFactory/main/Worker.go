package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Worker struct {
	id           int
	patient      bool
	doneTasks 	 int
	taskServer   *TaskServer
	itemServer   *ItemServer
	addComputers [ADD_COMPUTERS]*AddComputer
	mulComputers [MUL_COMPUTERS]*MulComputer
	lengthRequests chan *LengthTasksRequest
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

func (worker *Worker) getTask() Task {
	request := &TaskRequest{
		task:   Task{},
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
	if mode == LIVE_MODE {
		a = append([]interface{}{worker.id}, a...)
		fmt.Printf("Worker %d: "+format+"\n", a...)
	}
}

func (worker *Worker) restAndWaitForRequest() {
	select {
	case request := <- worker.lengthRequests:
		request.len <- worker.doneTasks
	case <-time.After(time.Duration(WORKER_BREAK_TIME) * time.Millisecond):
		//End of break time
	}
}

func (worker *Worker) solveOnComputer(task Task) int {
	idx := 0
	for {
		switch task.op {
		case ADD:
			if worker.patient {
				idx = rand.Intn(ADD_COMPUTERS)
				worker.log("comp #%d",idx)
				worker.addComputers[idx].task <- task
				solution := <-task.result
				return solution
			} else {
				select {
				case worker.addComputers[idx].task <- task:
					worker.log("comp #%d",idx)
					solution := <-task.result
					return solution
				case <-time.After(time.Duration(IMPATIENT_WORKER_TIME_WAIT) * time.Millisecond):
					worker.log("I'm gonna try other comp :(")
					idx = (idx + 1) % ADD_COMPUTERS
				}
			}
		case MUL:
			if worker.patient {
				idx = rand.Intn(MUL_COMPUTERS)
				worker.log("comp #%d",idx)
				worker.mulComputers[idx].task <- task
				solution := <-task.result
				return solution
			} else {
				select {
				case worker.mulComputers[idx].task <- task:
					worker.log("comp #%d",idx)
					solution := <- task.result
					return solution
				case <-time.After(time.Duration(IMPATIENT_WORKER_TIME_WAIT) * time.Millisecond):
					idx = (idx + 1) % MUL_COMPUTERS
				}
			}
		}
	}
}


func randBool() bool {
	return rand.Float32() < 0.5
}
