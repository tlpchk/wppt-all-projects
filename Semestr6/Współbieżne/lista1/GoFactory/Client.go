package main

import (
	"fmt"
	"time"
)

type Client struct{
	id int
	warehouse <-chan int
}

func goClient(id int, warehouse <-chan int){
	client := Client{id,warehouse}

	for range client.ticker() {
		select {
		case item := <-client.warehouse:
			client.log("Thank you for %d! See you later!",item)
		default:
			client.log("There is no items, i'll come later")
			// try later
		}
	}
}

func (client Client) log(format string, a ...interface{}) {
	if mode == LIVE_MODE {
		a = append([]interface{}{ client.id }, a...)
		fmt.Printf("Client %d: " + format + "\n", a...)
	}
}

func (client Client) ticker() <-chan time.Time {
	return time.Tick(time.Duration(CLIENT_BREAK_TIME) * time.Millisecond)
}