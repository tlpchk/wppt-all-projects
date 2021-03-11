package main

type TaskType struct {
	Arg1   int
	Arg2   int
	Op     string
	Result chan *int
}
