package main

import (
	"fmt"
	"math"
	"time"
	)


type Computer interface {
	getTask() chan Task
	compute(task Task) int
	log(format string, a ...interface{})
}

type MulComputer struct{
	task chan Task
}

type AddComputer struct{
	task chan Task
}


func (computer MulComputer) getTask() chan Task{
	return computer.task
}

func (computer AddComputer) getTask() chan Task{
	return computer.task
}


func (computer MulComputer) compute(task Task) int{
	if task.op == MUL {
		time.Sleep(time.Millisecond * time.Duration(COMPUTER_COMPUTATION_TIME))
		solution := task.arg1 * task.arg2
		computer.log("%d * %d = %d", task.arg1, task.arg2,solution)
		return solution
	}
	fmt.Println("Invalid operation")
	return math.MaxInt32
}

func (computer AddComputer) compute(task Task) int{
	if task.op == ADD {
		time.Sleep(time.Millisecond * time.Duration(COMPUTER_COMPUTATION_TIME))
		solution := task.arg1 + task.arg2
		computer.log("%d + %d = %d", task.arg1, task.arg2,solution)
		return solution
	}
	fmt.Println("Invalid operation")
	return math.MaxInt32
}

func (computer MulComputer) log(format string, a ...interface{}) {
	f := logFunction("Mul Comp")
	f(format, a...)
}

func (computer AddComputer) log(format string, a ...interface{}) {
	f := logFunction("Add Comp")
	f(format, a...)
}


func goComputer(computer Computer) {
	for {
		select {
		case task := <-computer.getTask():
			solution := computer.compute(task)
			task.result <- solution
		}
	}
}

