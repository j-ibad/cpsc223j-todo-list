package com.group7.android.todolist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import java.io.File

class ToDo : Fragment() {
    // adding task to display
    val tasks : ArrayList<String> = ArrayList<String>()
    lateinit var taskAdapter : ArrayAdapter<String>
    lateinit var taskDisplay : ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        readTasks()
        val root = inflater.inflate(R.layout.tab_1_to_do, container, false)
        taskDisplay = root.findViewById(R.id.task_display)
        taskAdapter = ArrayAdapter<String>(root.context,
            android.R.layout.simple_list_item_1, tasks)
        taskDisplay.adapter = taskAdapter
        return root
    }

    fun addTasks(taskTitle : String) {
        tasks.add(taskTitle)
        val m_file = File(this.context?.getExternalFilesDir(null), "Test")

        if(!m_file.exists()) {
            m_file.createNewFile()
        }
        m_file.printWriter().use { out ->
            out.println(taskTitle)
            /** Add more info and format if necessary */
        }
    }

    fun readTasks() {
        val m_file = File(this.context?.getExternalFilesDir(null), "Test")

        if(!m_file.exists()) {
            m_file.createNewFile()
        }else {
            val lines: List<String> = m_file.readLines()
            lines.forEach {line->
                tasks.add(line)
            }
        }
    }
}