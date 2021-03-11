package main

import (
	"fmt"
	"time"
)

type ServiceServer struct {
	workerFree     chan *serviceWorker
	ComputerToFix  chan *ComputerImpl
	Computers      [COMPUTERS]*ComputerImpl
	ServiceWorkers [SERVICE_WORKERS]*serviceWorker
}

func (service *ServiceServer) GoService() {
	// Initialization
	service.workerFree = make(chan *serviceWorker, SERVICE_WORKERS)
	service.ComputerToFix = make(chan *ComputerImpl, COMPUTERS)
	service.initServiceWorkers(service)

	//Stateful variable
	schedule := service.initSchedule() // worker.Id -> computer.Id

	for {
		select {
		case compToFix := <-service.ComputerToFix:
			if service.computerIsFixed(compToFix, schedule) == false {
				service.log("Comp #%d is broken?",compToFix.id)
				worker := service.findFreeWorker(schedule)
				worker.request <- &serviceWorkerRequest{compToFix,service}
				service.log("Send service worker #%d to fix comp#%d",worker.id,compToFix.id)
				schedule[worker] = compToFix
			}
		case worker := <-service.workerFree:
			service.log("Good job, service worker #%d", worker.id)
			schedule[worker] = nil
		}
	}
}

func (service *ServiceServer) findFreeWorker(schedule map[*serviceWorker]*ComputerImpl) *serviceWorker {
	for {
		for worker, computer := range schedule {
			if computer == nil {
				return worker
			}
		}
		time.Sleep(time.Millisecond * time.Duration(COMPUTER_FIXING_TIME))
	}
}

func (service *ServiceServer) log(format string, a ...interface{}) {
	if Mode == LIVE_MODE {
		fmt.Printf("Service Server: "+format+"\n", a...)
	}
}

func (service *ServiceServer) computerIsFixed(
	compToFix *ComputerImpl,
	schedule map[*serviceWorker]*ComputerImpl) bool {

	for _, computer := range schedule {
		if computer == compToFix {
			return true
		}
	}
	// There is no worker fixing computer
	return false
}

func (service *ServiceServer) initServiceWorkers(server *ServiceServer) {
	for i := 1; i <= SERVICE_WORKERS; i++ {
		serviceWorker := &serviceWorker{
			i,
			make(chan *serviceWorkerRequest),
		}
		go serviceWorker.goWorker()
		service.ServiceWorkers[i-1] = serviceWorker
	}

}

func (service *ServiceServer) initSchedule() map[*serviceWorker]*ComputerImpl {
	schedule := make(map[*serviceWorker]*ComputerImpl)
	for _,worker := range service.ServiceWorkers{
		schedule[worker] = nil
	}
	return schedule
}

