package main

import "fmt"

const(
	ADD = "+"
	SUB = "-"
	MUL = "*"
	DIV = "/"

	MAX_BOSS_BREAK_TIME int = 500
	WORKER_BREAK_TIME   int = 100
	IMPATIENT_WORKER_TIME_WAIT int = 100
	CLIENT_BREAK_TIME   int = 1000
	COMPUTER_COMPUTATION_TIME  int = 1000

	MAX_ARG_VALUE int = 1000
	MIN_ARG_VALUE int = 1
	TASKS_CAPACITY = 10
	WAREHOUSE_CAPACITY = 10

	ADD_COMPUTERS = 3
	MUL_COMPUTERS = 3

	LIVE_MODE = 1
	INTERACTIVE_MODE = 2

	WORKERS = 3
	CLIENTS = 3
)

var operators = [...]string{ADD, MUL}
var mode int

func logFunction(name string) func(format string, a ...interface{}) {
	return func(format string, a ...interface{}) {
		if mode == LIVE_MODE {
			fmt.Printf(name + ": " + format + "\n",a...)
		}
	}
}
