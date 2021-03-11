package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Boss struct {
	tasks chan<- Task
}

func goBoss(tasks chan<- Task){
	boss := &Boss{tasks}
	for {
		task := boss.createNewTask()
		boss.addTask(task)
		boss.rest()
	}
}

func (boss *Boss) createNewTask() Task {
	return Task{
		arg1: MIN_ARG_VALUE + rand.Intn(MAX_ARG_VALUE-MIN_ARG_VALUE),
		arg2: MIN_ARG_VALUE + rand.Intn(MAX_ARG_VALUE-MIN_ARG_VALUE),
		op:   operators[(rand.Intn(len(operators)))]}
}

func (boss *Boss) addTask(task Task) {
	select {
		case boss.tasks <- task:
			boss.log("New task: %d %s %d", task.arg1, task.op, task.arg2 )
		default:
			boss.log("Can't add task")
			boss.rest()
			boss.addTask(task)
	}
}

func (boss *Boss) rest(){
	i := rand.Intn(MAX_BOSS_BREAK_TIME)
	time.Sleep(time.Millisecond * time.Duration(i))
}

func (boss *Boss) log(format string, a ...interface{}) {
	if mode == LIVE_MODE {
		fmt.Printf("Boss: " + format + "\n",a...)
	}
}

