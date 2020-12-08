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

class Finish : Fragment() {

    val tasks : ArrayList<TaskItem> = ArrayList<TaskItem>()
    lateinit var taskDisplay : ListView
    var changed_flag: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Broker.fin = this
        readTasks()
        val root = inflater.inflate(R.layout.tab_2_finish, container, false)
        taskDisplay = root.findViewById(R.id.task_display_fin)
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

    fun readTasks() {
        System.out.println("[Finished] Reading")
        val m_file = File(this.context?.getExternalFilesDir("store"), "finished")

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
        System.out.println("[Finished] Writing")
        val m_file = File(this.context?.getExternalFilesDir("store"), "finished")

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