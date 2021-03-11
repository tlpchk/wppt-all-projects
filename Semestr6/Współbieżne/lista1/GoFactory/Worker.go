package main

import (
	"fmt"
	"time"
)

type Worker struct{
	id int
	tasks <-chan Task
	warehouse chan<- int
}

func (worker Worker)ticker() <-chan time.Time {
	 return time.Tick(time.Duration(WORKER_BREAK_TIME) * time.Millisecond)
}

func goWorker(id int, tasks <-chan Task,warehouse chan<- int){
	worker := Worker{id,tasks,warehouse}

	for range worker.ticker() {
		select {
			case task := <-worker.tasks:
				solution := worker.compute(task)
				worker.log("%d %s %d = %d", task.arg1, task.op,task.arg2, solution)
				worker.store(solution)
			default:
				// try later
		}
	}
}

func (worker Worker) store(solution int){
	for range worker.ticker() {
		select {
		case worker.warehouse <- solution:
			worker.log("%d is stored",solution)
			return
		default:
			worker.log("Can't store %d",solution)
		}
	}
}

func (worker Worker) compute(task Task) int {
	a := task.arg1
	b := task.arg2
	switch task.op {
	case ADD:
		return a + b
	case SUB:
		return a - b
	case MUL:
		return a * b
	case DIV:
		return a / b
	default:
		fmt.Println("Unknowns operator")
		return 0
	}
}

func (worker Worker) log(format string, a ...interface{}) {
	if mode == LIVE_MODE {
		a = append([]interface{}{ worker.id }, a...)
		fmt.Printf("Worker %d: " + format + "\n", a...)
	}
}

