package main

import (
	"fmt"
	"math"
	"math/rand"
	"time"
)


type Computer interface {
	getTask() chan TaskType
	compute(task TaskType) int
	log(format string, a ...interface{})
	enduranceTest()
	GoComputer()
}

type ComputerImpl struct{
	id int
	Task chan TaskType
	backdoor chan bool
	broken bool
}

func (computer *ComputerImpl) getTask() chan TaskType {
	return computer.Task
}

func (computer *ComputerImpl) compute(task TaskType) int{
	time.Sleep(time.Millisecond * time.Duration(COMPUTER_COMPUTATION_TIME))
	if task.Op == ADD {
		solution := task.Arg1 + task.Arg2
		return solution
	} else if task.Op == MUL {
		solution := task.Arg1 * task.Arg2
		return solution
	}
	fmt.Println("Invalid operation")
	return math.MaxInt32
}

func (computer *ComputerImpl) log(format string, a ...interface{}) {
	if Mode == LIVE_MODE {
		a = append([]interface{}{computer.id}, a...)
		fmt.Printf("Comp %d: "+format+"\n", a...)
	}
}

func (computer *ComputerImpl) enduranceTest(){
	val := rand.Float32()
	if val < PROBABILITY_OF_BREAKING {
		computer.broken = true
		computer.log("I'have broken :(")
	}
}

func GoComputer(computer *ComputerImpl) {
	for {
		select {
		case task := <-computer.getTask():
			solution := computer.compute(task)
			computer.enduranceTest()
			if computer.broken {
				task.Result <- nil
			}else{
				if task.Op == ADD{
					computer.log("%d + %d = %d", task.Arg1, task.Arg2,solution)
				}else if task.Op == MUL{
					computer.log("%d * %d = %d", task.Arg1, task.Arg2, solution)
				}
				task.Result <- &solution
			}
		case <-computer.backdoor:
			computer.broken = false
		}
	}
}
