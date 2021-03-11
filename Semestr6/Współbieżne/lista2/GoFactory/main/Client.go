package main

import (
"fmt"
"time"
)

type Client struct{
	id int
	itemServer *ItemServer
}


func (client *Client) goClient(){
	for {
		item := client.getItem()
		client.log("Thank you for %d! See you later!",item)
		client.rest()
	}
}

func (client *Client)  getItem() int{
	request := &ItemRequest{
		item: 0,
		status: make(chan bool,1),
	}
	client.itemServer.readRequests <- request
	status := <-request.status

	if status == true {
		return request.item
	} else {
		client.log("There is no items, i'll come later")
		client.rest()
		return client.getItem()
	}

}

func (client Client) log(format string, a ...interface{}) {
	if mode == LIVE_MODE {
		a = append([]interface{}{ client.id }, a...)
		fmt.Printf("Client %d: " + format + "\n", a...)
	}
}

func (client *Client) rest() {
	time.Sleep(time.Millisecond * time.Duration(CLIENT_BREAK_TIME))
}


