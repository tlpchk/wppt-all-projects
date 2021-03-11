package main

type Task struct {
	arg1 int
	arg2 int
	op string
	result chan int
}
