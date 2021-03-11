package com.am.todo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.am.todo.R
import com.am.todo.model.Goal
import com.am.todo.model.Model
import kotlinx.android.synthetic.main.list_item_goal.view.*

class ViewHolder(
    val photoView: ImageView,
    val nameView: TextView,
    val deadLineView: TextView
)

class GoalListAdapter(
    context: Context,
    val model: Model) : BaseAdapter(){
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): Goal {
        return model.get(position)
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
        return model.size()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder : ViewHolder
        if (convertView == null) {
            val goalItemView = inflater.inflate(R.layout.list_item_goal, null)

            viewHolder = ViewHolder(goalItemView.photoView,
                                    goalItemView.nameView,
                                    goalItemView.deadineView)

            convertView!!.setTag(viewHolder)
        }
        else{
            viewHolder = convertView.getTag() as ViewHolder
        }
        val goal = getItem(position)

        viewHolder.photoView.setImageResource(goal.imgRes)
        viewHolder.nameView.text = goal.description
        viewHolder.deadLineView.text = "${Goal.daysToDeadline(goal.deadline)} d"
        viewHolder.deadLineView.setTextColor(goal.priority.textColor)
        viewHolder.deadLineView.setBackgroundResource(goal.priority.drawableCircle)

        return convertView
    }

    /*@SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val goalItemView = inflater.inflate(R.layout.list_item_goal,null)
        val goal = getItem(position)

        goalItemView.photoView.setImageResource(goal.imgRes)
        goalItemView.nameView.text = goal.description
        goalItemView.deadineView.text = "${Goal.daysToDeadline(goal.deadline)} d"
        goalItemView.deadineView.setTextColor(goal.priority.textColor)
        goalItemView.deadineView.setBackgroundResource(goal.priority.drawableCircle)

        return goalItemView
    }*/
}