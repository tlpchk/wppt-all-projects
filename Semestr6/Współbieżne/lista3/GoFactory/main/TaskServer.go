package main

import "fmt"

type TaskServer struct{
	readRequests   chan *TaskRequest
	writeRequests  chan *TaskRequest
	lengthRequests chan *LengthTasksRequest
}

type TaskRequest struct{
	task   TaskType
	status chan bool
}

type LengthTasksRequest struct{
	len chan int
}


func (server *TaskServer) goServer() {
	tasks := &Queue{}
	tasks.queue = make([]interface{}, 0)
	for {
		select {
		case write := <-server.writeRequests:
			if tasks.len() < TASKS_CAPACITY {
				tasks.push(write.task)
				server.log("Task_Type was added to queue")
				write.status <- true
			} else {
				server.log("No place for new task")
				write.status <- false
			}

		case read := <-server.readRequests:
			if tasks.isEmpty()  {
				server.log("No tasks")
				read.status <- false
			} else {
				read.task = tasks.pop().(TaskType)
				read.status <- true
			}

		case lenRequest := <-server.lengthRequests:
			printTasks(tasks)
			lenRequest.len <- tasks.len()

		}
	}
}

func printTasks(queue *Queue) {
	for i := range queue.queue{
		task := queue.queue[i].(TaskType)
		fmt.Println(task.Arg1, task.Op, task.Arg2)
	}
}

func (server *TaskServer) log(format string, a ...interface{}) {
	if Mode == LIVE_MODE && !LISTA_3 {
		fmt.Printf("Task_Type Server: " + format + "\n",a...)
	}
}

