package main

import (
	"fmt"
	"time"
)

type serviceWorker struct{
	id int
	request chan *serviceWorkerRequest
}

type serviceWorkerRequest struct {
	compToFix *ComputerImpl
	service *ServiceServer
}

func (serviceWorker *serviceWorker) goWorker() {
	for {
		select {
		case request := <-serviceWorker.request:
			serviceWorker.log("Going to fix computer #%d",request.compToFix.id)
			time.Sleep(time.Millisecond * time.Duration(COMPUTER_FIXING_TIME))
			request.compToFix.backdoor <- true
			serviceWorker.log("Computer #%d is fixed",request.compToFix.id)
			request.service.workerFree <- serviceWorker
		}
	}
}

func (serviceWorker *serviceWorker) log(format string, a ...interface{}) {
	if Mode == LIVE_MODE {
		a = append([]interface{}{serviceWorker.id}, a...)
		fmt.Printf("Service Worker %d: "+format+"\n", a...)
	}
}
