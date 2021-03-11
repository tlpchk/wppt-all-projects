package com.am.todo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Toast
import com.am.todo.adapter.GoalListAdapter
import com.am.todo.adapter.TeacherSpinnerAdapter
import com.am.todo.model.Goal
import com.am.todo.model.Model
import com.am.todo.model.TeacherSpinnerItem
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    private val PICK_DEADLINE_DIALOG = 99
    private val model = Model()

    private val teachers = arrayListOf(
        TeacherSpinnerItem(R.drawable.zawada, "Aplikacje mobilne"),
        TeacherSpinnerItem(R.drawable.kobylanski, "Niezawodne systemy informatyczne"),
        TeacherSpinnerItem(R.drawable.kik, "Programowanie współbieżne")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null){
            val size = savedInstanceState.getInt("size")
            for (i in 0 until size){
                val goal = getGoalFromSavedInstanceState(savedInstanceState,i)
                model.add(goal)
            }
        }else{
            model.add(Goal("Lista 3", R.drawable.zawada, LocalDate.of(2019,4,7)))
            model.add(Goal("Lista 4", R.drawable.zawada, LocalDate.of(2019,4,21)))
            model.add(Goal("Lista 1", R.drawable.kobylanski, LocalDate.of(2019,4,4)))
            model.add(Goal("Lista 2", R.drawable.kik, LocalDate.of(2019,4,9)))
            model.add(Goal("Kolokwium 1", R.drawable.zawada, LocalDate.of(2019,4,12)))
            model.add(Goal("Lista 2", R.drawable.kobylanski, LocalDate.of(2019,4,23)))
            model.add(Goal("Konsultacje", R.drawable.kik, LocalDate.of(2019,3,30)))
        }

        val goalListAdapter = GoalListAdapter(this, model)
        goalListView.adapter = goalListAdapter
        goalListView.onItemLongClickListener = removeOnLongClick()

        val spinnerAdapter = TeacherSpinnerAdapter(this, teachers)
        spinnerView.adapter = spinnerAdapter

        addButtonView.setOnClickListener { onAddClick() }
        sortByDaysCheckBox.setOnClickListener { sortByCheckBox() }
        groupByTeacherCheckBox.setOnClickListener { sortByCheckBox() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val size = model.size()
        outState.putInt("size",size)

        for (i in 0 until size){
            val goal = model.get(i)
            outState.putInt("imgRes$i",goal.imgRes)
            outState.putString("description$i",goal.description)
            outState.putString("deadline$i",goal.deadline.toString())
        }
    }

    private fun getGoalFromSavedInstanceState(savedInstanceState: Bundle, i : Int): Goal {
        val imgRes = savedInstanceState.getInt("imgRes$i")
        val description = savedInstanceState.getString("description$i")
        val deadLine = LocalDate.parse(savedInstanceState.getString("deadline$i"))

        val goal = Goal(description!!,imgRes,deadLine)
        goal.refreshPriority()
        return goal
    }

    private fun removeOnLongClick(): AdapterView.OnItemLongClickListener =
        AdapterView.OnItemLongClickListener { _, _, i, _ ->
            removeItemDialog(i)
            return@OnItemLongClickListener true
        }

    private fun onAddClick() {
        if (descriptionEditView.text.isEmpty()) {
            Toast.makeText(this, "Please, write a description", Toast.LENGTH_SHORT).show()
        } else {
            showDialog(PICK_DEADLINE_DIALOG)
        }
    }

    private val myDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        val description = descriptionEditView.text.toString()
        val imageResource = spinnerView.tag as Int
        val deadline = LocalDate.of(year, month + 1, day)

        val newGoal = Goal(description, imageResource, deadline)
        newGoal.refreshPriority()

        model.add(newGoal)
        sortByCheckBox()
        descriptionEditView.text = null
        (goalListView.adapter as BaseAdapter).notifyDataSetChanged()
    }

    override fun onCreateDialog(id: Int): Dialog? {
        val today = LocalDate.now()
        if (id == PICK_DEADLINE_DIALOG) {
            return DatePickerDialog(this, myDateListener, today.year, today.monthValue - 1, today.dayOfMonth)
        }
        return null
    }

    private fun removeItemDialog(i: Int) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Do you want delete this goal?")
        dialog.setTitle("Delete")
        dialog.setPositiveButton("YES") { _, _ ->
            model.removeAt(i)
            (goalListView.adapter as BaseAdapter).notifyDataSetChanged()
        }
        dialog.setNegativeButton("Cancel") { _, _ ->
            // do nothing
        }

        val alertDialog = dialog.create()
        alertDialog.show()
    }

    private fun sortByCheckBox() {
        if (sortByDaysCheckBox.isChecked) {
            model.sortByDays()
        }
        if (groupByTeacherCheckBox.isChecked) {
            model.groupByTeacher()
        }
        (goalListView.adapter as BaseAdapter).notifyDataSetChanged()
    }

}
