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
    val tasks : ArrayList<TaskItem> = ArrayList<TaskItem>()
    lateinit var taskDisplay : ListView
    var changed_flag: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        readTasks()
        val root = inflater.inflate(R.layout.tab_1_to_do, container, false)
        taskDisplay = root.findViewById(R.id.task_display)
        val taskAdapter = TaskAdapter(this.requireContext(), tasks)
        taskDisplay.adapter = taskAdapter
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        if(changed_flag){
            rewriteTasks()
        }
    }

    fun addTasks(taskTitle : String) {
        changed_flag = true
        tasks.add( TaskItem(taskTitle) )
    }

    fun readTasks() {
        System.out.println("[TODO] Reading")
        val m_file = File(this.context?.getExternalFilesDir(null), "todo")

        if(!m_file.exists()) {
            m_file.createNewFile()
        }else {
            val lines: List<String> = m_file.readLines()
            lines.forEach {line->
                tasks.add( TaskItem(line) )
            }
        }
    }

    fun rewriteTasks(){
        System.out.println("[TODO] Writing")
        val m_file = File(this.context?.getExternalFilesDir(null), "todo")

        if(!m_file.exists()) {
            m_file.createNewFile()
        }

        m_file.printWriter().use { out ->
            tasks.forEach{ item->
                out.println(item.title)
                /** Add more info and format if necessary */
            }
        }
    }
}