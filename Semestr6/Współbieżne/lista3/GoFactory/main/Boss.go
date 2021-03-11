package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Boss struct {
	taskServer *TaskServer
}

func (boss *Boss) goBoss(){
	for {
		task := boss.createNewTask()
		boss.log("New task: %d %s %d", task.Arg1, task.Op, task.Arg2)
		boss.addTask(task)
		boss.rest()
	}
}

func (boss *Boss) createNewTask() TaskType {
	task := TaskType{
		Arg1:   MIN_ARG_VALUE + rand.Intn(MAX_ARG_VALUE-MIN_ARG_VALUE),
		Arg2:   MIN_ARG_VALUE + rand.Intn(MAX_ARG_VALUE-MIN_ARG_VALUE),
		Op:     operators[(rand.Intn(len(operators)))],
		Result: make(chan *int,1),
	}
	return task
}

func (boss *Boss) addTask(task TaskType) {
	request := &TaskRequest{
		task: task,
		status: make(chan bool,1),
	}
	boss.taskServer.writeRequests <- request
	status := <-request.status

	if status == false {
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
	if Mode == LIVE_MODE && !LISTA_3 {
		fmt.Printf("Boss: " + format + "\n",a...)
	}
}

