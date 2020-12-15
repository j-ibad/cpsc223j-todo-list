package com.group7.android.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import java.io.File
import kotlin.math.roundToInt

class Finish : Fragment() {
    // adding task to display
    lateinit var taskDisplay : ListView
    lateinit var taskAdapter: TaskAdapter
    var changed_flag: Boolean = false
    var is_sortable : Boolean = false
    var drag_string : String = ""
    var task_position : Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Broker.fin = this
        val root = inflater.inflate(R.layout.tab_2_finish, container, false)
        taskDisplay = root.findViewById(R.id.task_display_fin)

        taskAdapter = TaskAdapter(this.requireContext(), R.layout.task_row)
        readTasks()
        taskDisplay.adapter = taskAdapter

        taskDisplay.setOnTouchListener(object : View.OnTouchListener {
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(view: View, event: MotionEvent): Boolean {
                if (!is_sortable) {
                    return false
                }
                when (event.getAction()) {
                    MotionEvent.ACTION_DOWN -> { }
                    MotionEvent.ACTION_MOVE -> {
                        val position: Int = (taskDisplay.pointToPosition(event.getX().roundToInt(), event.getY().roundToInt()))
                        if (position < 0) return false
                        if (position != task_position) {
                            changed_flag = true;
                            task_position = position
                            taskAdapter.remove(drag_string)
                            taskAdapter.insert(drag_string, task_position)
                        }
                        return true
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                        stopDrag()
                        return true
                    }
                }
                return false
            }
        })

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
                taskAdapter.tasks.add(line)
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
            taskAdapter.tasks.forEach{ item->
                out.println(item)
                /** Add more info and format if necessary */
            }
        }
    }

    fun startDrag(string: String) {
        task_position = -1
        is_sortable = true
        drag_string = string
        taskAdapter.notifyDataSetChanged()
    }

    fun stopDrag() {
        task_position = -1
        is_sortable = false
        drag_string = ""
        taskAdapter.notifyDataSetChanged()
    }
}