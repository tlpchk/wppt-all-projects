package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	rand.Seed(time.Now().UTC().UnixNano()) // pseudorandomness

	tasks := make(chan Task,TASKS_CAPACITY)
	warehouse := make(chan int,WAREHOUSE_CAPACITY)
	done := make(chan bool)

	printStartMenu()

	readErr := readMode()
	if readErr != nil {return}

	printModeMenu(tasks,warehouse,done)

	go goBoss(tasks)

	for i := 1 ; i <= WORKERS ; i++ {
		go goWorker(i, tasks, warehouse)
	}

	for i := 1 ; i <= CLIENTS ; i++ {
		go goClient(i, warehouse)
	}

	<-done
}

func printModeMenu(tasks <-chan Task, warehouse <-chan int, done chan<- bool) {
	switch mode {
	case LIVE_MODE:
		fmt.Println("Press Enter when you get tired\n")
		go liveMode(done)
	case INTERACTIVE_MODE:
		go interactiveMode(tasks,warehouse,done)
	}
}

func liveMode(done chan<- bool) {
	fmt.Scanln()
	done <- true
}

func interactiveMode(tasks <-chan Task, warehouse <-chan int, done chan<- bool) {
	for {
		printInteractiveMenu();

		var opt int
		_ , err := fmt.Scanf("%d", &opt)
		if err != nil {return}

		switch opt {
		case 1:
			printLengthTaskList(tasks);
		case 2:
			printLengthWarehouseList(warehouse);
		default:
			done <- true
			return
		}
	}
}

func printLengthWarehouseList(warehouse <-chan int) {
	fmt.Printf("There is %d available items\n",len(warehouse))
}

func printLengthTaskList(tasks <-chan Task) {
	fmt.Printf("There is %d unsolved tasks\n",len(tasks))
}

func printInteractiveMenu() {
	fmt.Println()
	fmt.Println("1. See actual number of tasks")
	fmt.Println("2. See actual number of items")
	fmt.Println("3. Exit")
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
	_ , err := fmt.Scanf("%d", &mode)
	return err
}
