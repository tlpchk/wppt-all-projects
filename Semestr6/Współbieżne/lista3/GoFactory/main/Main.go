package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	rand.Seed(time.Now().UTC().UnixNano()) // pseudorandomness

	taskServer := &TaskServer{readRequests: make(chan *TaskRequest),
		writeRequests:  make(chan *TaskRequest),
		lengthRequests: make(chan *LengthTasksRequest)}

	itemServer := &ItemServer{readRequests: make(chan *ItemRequest),
		writeRequests:  make(chan *ItemRequest),
		lengthRequests: make(chan *LengthItemsRequest)}

	boss := &Boss{taskServer: taskServer}

	var computers [COMPUTERS]*ComputerImpl
	var workers [WORKERS]*Worker

	done := make(chan bool)

	printStartMenu()

	readErr := readMode()
	if readErr != nil {
		return
	}

	go taskServer.goServer()
	go itemServer.goServer()
	go boss.goBoss()

	for i := range computers {
		computers[i] = &ComputerImpl{i+1,make(chan TaskType, 1),make(chan bool,1),false}
		go GoComputer(computers[i])
	}

	serviceServer := &ServiceServer{Computers:computers}

	go serviceServer.GoService()


	for i := 1; i <= WORKERS; i++ {
		worker := &Worker{
			id:             i,
			patient:        randBool(),
			doneTasks:      0,
			taskServer:     taskServer,
			itemServer:     itemServer,
			computers:   computers,
			lengthRequests: make(chan *LengthTasksRequest),
			service: serviceServer,
		}
		go worker.goWorker()
		workers[i-1] = worker
	}

	for i := 1; i <= CLIENTS; i++ {
		client := &Client{id: i,
			itemServer: itemServer,
		}
		go client.goClient()
	}

	printModeMenu(taskServer, itemServer, workers, done)

	<-done
}

func printModeMenu(tServer *TaskServer, iServer *ItemServer, workers [WORKERS]*Worker, done chan<- bool) {
	switch Mode {
	case LIVE_MODE:
		fmt.Println("Press Enter when you get tired\n")
		go liveMode(done)
	case INTERACTIVE_MODE:
		go interactiveMode(tServer, iServer, workers, done)
	}
}

func liveMode(done chan<- bool) {
	fmt.Scanln()
	done <- true
}

func interactiveMode(tServer *TaskServer, iServer *ItemServer, workers [WORKERS]*Worker, done chan<- bool) {
	for {
		printInteractiveMenu()

		var opt int
		_, err := fmt.Scanf("%d", &opt)
		if err != nil {
			return
		}

		switch opt {
		case 1:
			request := &LengthTasksRequest{len: make(chan int, 1),}
			tServer.lengthRequests <- request
			printLengthTaskList(<-request.len)
		case 2:
			request := &LengthItemsRequest{len: make(chan int, 1),}
			iServer.lengthRequests <- request
			printLengthWarehouseList(<-request.len)
		case 3:
			printWorkerStatistic(workers)
		default:
			done <- true
			return
		}
	}
}

func printWorkerStatistic(workers [WORKERS]*Worker) {
	for i := 0; i < WORKERS; i++ {
		worker := workers[i]
		lenRequest := &LengthTasksRequest{make(chan int, 1)}
		worker.lengthRequests <- lenRequest
		fmt.Printf("Worker %d (patient = %t) has done %d tasks\n", worker.id, worker.patient, <-lenRequest.len)
	}
}

func printLengthWarehouseList(len int) {
	fmt.Printf("There is %d available items\n", len)
}

func printLengthTaskList(len int) {
	fmt.Printf("There is %d unsolved tasks\n", len)
}

func printInteractiveMenu() {
	fmt.Println()
	fmt.Println("1. See actual number of tasks")
	fmt.Println("2. See actual number of items")
	fmt.Println("3. Worker statistic")
	fmt.Println("4. Exit")
	fmt.Print("Your choice: ")
}

func printStartMenu() {
	fmt.Println("This is GoFactory\n")
	fmt.Println("Press mode:")
	fmt.Println("\t1.Live (see all operations immediatly)")
	fmt.Println("\t2.Interactive (see status of GoFactory on demand)")
	fmt.Print("Your choice: ")
}

func readMode() error {
	_, err := fmt.Scanf("%d", &Mode)
	return err
}
