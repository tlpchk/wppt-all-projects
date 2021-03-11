package main

import "fmt"

type ItemServer struct{
	readRequests   chan *ItemRequest
	writeRequests  chan *ItemRequest
	lengthRequests chan *LengthItemsRequest
}

type ItemRequest struct{
	item int
	status chan bool
}

type LengthItemsRequest struct{
	len chan int
}


func (server *ItemServer) goServer() {
	items := &Queue{}
	items.queue = make([]interface{}, 0)
	for {
		select {
		case write := <-server.writeRequests:
			if items.len() < WAREHOUSE_CAPACITY {
				items.push(write.item)
				server.log("Item was added to queue")
				write.status <- true
			} else {
				server.log("No place for new item")
				write.status <- false
			}

		case read := <-server.readRequests:
			if items.isEmpty()  {
				server.log("No items")
				read.status <- false
			} else {
				read.item = items.pop().(int)
				read.status <- true
			}

		case lenRequest := <-server.lengthRequests:
			printItems(items)
			lenRequest.len <- items.len()

		}
	}
}

func printItems(queue *Queue) {
	for i := range queue.queue{
		item := queue.queue[i].(int)
		fmt.Println(item)
	}
}

func (server *ItemServer) log(format string, a ...interface{}) {
	if Mode == LIVE_MODE && !LISTA_3 {
		fmt.Printf("Item Server: " + format + "\n",a...)
	}
}

