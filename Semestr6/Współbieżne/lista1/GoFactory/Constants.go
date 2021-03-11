package main

const(
	ADD = "+"
	SUB = "-"
	MUL = "*"
	DIV = "/"

	MAX_BOSS_BREAK_TIME int = 300
	WORKER_BREAK_TIME   int = 1000
	CLIENT_BREAK_TIME   int = 1000

	MAX_ARG_VALUE int = 1000
	MIN_ARG_VALUE int = 1
	TASKS_CAPACITY = 10
	WAREHOUSE_CAPACITY = 10

	LIVE_MODE = 1
	INTERACTIVE_MODE = 2

	WORKERS = 1
	CLIENTS = 3
)

var operators = [...]string{ADD, SUB, MUL, DIV}
var mode int

