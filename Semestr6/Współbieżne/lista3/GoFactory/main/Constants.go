package main

import "fmt"

const (
	ADD = "+"
	MUL = "*"

	LIVE_MODE        = 1
	INTERACTIVE_MODE = 2

	LISTA_3 = true

	MAX_ARG_VALUE int = 1000
	MIN_ARG_VALUE int = 1

	MAX_BOSS_BREAK_TIME        int = 500
	WORKER_BREAK_TIME          int = 100
	IMPATIENT_WORKER_TIME_WAIT int = 100
	CLIENT_BREAK_TIME          int = 1000

	COMPUTER_COMPUTATION_TIME int = 1000
	COMPUTER_FIXING_TIME          = 1000
	PROBABILITY_OF_BREAKING       = 0.3

	TASKS_CAPACITY     = 10
	WAREHOUSE_CAPACITY = 10

	SERVICE_WORKERS = 2
	WORKERS         = 1
	CLIENTS         = 1
	COMPUTERS = 3
)

var operators = [...]string{ADD, MUL}
var Mode int

func LogFunction(name string) func(format string, a ...interface{}) {
	return func(format string, a ...interface{}) {
		if Mode == LIVE_MODE {
			fmt.Printf(name+": "+format+"\n", a...)
		}
	}
}
