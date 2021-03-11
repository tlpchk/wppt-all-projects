package com.am.todo.model

class Model{
    private val goals = arrayListOf<Goal>()

    fun sortByDays() {
        goals.sortWith(Comparator { x: Goal, y: Goal -> Goal.daysToDeadline(x.deadline) - Goal.daysToDeadline(y.deadline) })
    }

    fun groupByTeacher() {
        goals.sortWith(Comparator { x: Goal, y: Goal -> x.imgRes - y.imgRes })
    }

    fun removeAt(id: Int){
        goals.removeAt(id)
    }

    fun add(goal: Goal){
        goals.add(goal)
        goal.refreshPriority()
    }

    fun get(id : Int) : Goal{
        return goals[id]
    }

    fun size(): Int {
        return goals.size
    }
}