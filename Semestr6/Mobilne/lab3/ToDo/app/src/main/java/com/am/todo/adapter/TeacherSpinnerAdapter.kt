package com.am.todo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import com.am.todo.R
import com.am.todo.model.TeacherSpinnerItem
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.teacher_spinner_row.view.*


class TeacherSpinnerAdapter(
    val context: Context,
    val teachers: ArrayList<TeacherSpinnerItem>
) : BaseAdapter() {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): TeacherSpinnerItem {
        return teachers[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return teachers.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val tsItem = getItem(position)

        val teacherSpinnerIcon = CircleImageView(context)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        teacherSpinnerIcon.setImageResource(tsItem.imgResourse)
        teacherSpinnerIcon.layoutParams = layoutParams
        parent.tag = tsItem.imgResourse // To pass image further

        return teacherSpinnerIcon
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val spinnerItem = inflater.inflate(R.layout.teacher_spinner_row, null)
        val tsItem = getItem(position)

        spinnerItem.photoView.setImageResource(tsItem.imgResourse)
        spinnerItem.nameView.text = tsItem.name

        return spinnerItem
    }
}