package main

import "fmt"

type TaskServer struct{
	readRequests   chan *TaskRequest
	writeRequests  chan *TaskRequest
	lengthRequests chan *LengthTasksRequest
}

type TaskRequest struct{
	task Task
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
				server.log("Task was added to queue")
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
				read.task = tasks.pop().(Task)
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
		task := queue.queue[i].(Task)
		fmt.Println(task.arg1, task.op, task.arg2)
	}
}

func (server *TaskServer) log(format string, a ...interface{}) {
	if mode == LIVE_MODE {
		//fmt.Printf("Task Server: " + format + "\n",a...)
	}
}

