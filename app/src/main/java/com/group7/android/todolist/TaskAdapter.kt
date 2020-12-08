package com.group7.android.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

class TaskAdapter(private val context: Context, private val tasks: ArrayList<TaskItem>) :
        BaseAdapter(){
    private val inflater: LayoutInflater = context.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int{ return tasks.size }
    override fun getItem(pos: Int): Any { return tasks[pos] }
    override fun getItemId(pos: Int): Long{ return pos.toLong() }
    @SuppressLint("ViewHolder")
    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.task_item, parent, false)
        val titleView = rowView.findViewById(R.id.task_item_title) as TextView
        /* If more info needed, add views here */

        val task_item = getItem(pos) as TaskItem
        titleView.text = task_item.title
        /* Fill in views with TaskItem member fields */

        rowView.setOnClickListener{view ->
            val intent = Intent(this.context, TaskDetails::class.java).apply {
                putExtra("TASK_TITLE", task_item.title)
                putExtra("INDEX", pos)
            }
            startActivity(this.context, intent, null)
        }

        return rowView
    }
}