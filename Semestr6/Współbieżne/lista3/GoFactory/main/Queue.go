package main

type Queue struct{
	queue []interface{}
}

func (q *Queue) push(x interface{}){
	q.queue = append(q.queue, x)
}
func (q *Queue) pop() interface{}{
	x := q.queue[0]
	q.queue = q.queue[1:]
	return x
}
func (q *Queue) len() int{
	return len(q.queue)
}

func (q *Queue) isEmpty() bool{
	return q.len() == 0
}

