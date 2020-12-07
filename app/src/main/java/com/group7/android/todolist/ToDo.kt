package com.group7.android.todolist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class ToDo : Fragment() {
    // adding task to display
    val tasks : ArrayList<String> = ArrayList<String>()
    lateinit var taskAdapter : ArrayAdapter<String>
    lateinit var taskDisplay : ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.tab_1_to_do, container, false)
        taskDisplay = root.findViewById(R.id.task_display)
        taskAdapter = ArrayAdapter<String>(root.context,
            android.R.layout.simple_list_item_1, tasks)
        taskDisplay.adapter = taskAdapter
        return root
    }

    fun addTasks(taskTitle : String) {
        tasks.add(taskTitle)
    }


}