package com.group7.android.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import java.io.File
import java.util.*
import kotlin.math.roundToInt

class ToDo : Fragment() {
    // adding task to display
    lateinit var taskDisplay : ListView
    lateinit var taskAdapter: TaskAdapter
    var changed_flag: Boolean = false
    var is_sortable : Boolean = false
    var drag_string : TaskItem = TaskItem("", Date(0))
    var task_position : Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Broker.todo = this
        val root = inflater.inflate(R.layout.tab_1_to_do, container, false)
        taskDisplay = root.findViewById(R.id.task_display)

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

    fun addTasks(task : TaskItem) {
        changed_flag = true
        taskAdapter.add( task )
    }

    fun readTasks() {
        System.out.println("[TODO] Reading")
        val m_file = File(this.context?.getExternalFilesDir(null), "todo")

        if(!m_file.exists()) {
            m_file.createNewFile()
        }else {
            var tmp_scanner = Scanner(m_file)
            while(tmp_scanner.hasNext()){
                var tmp_str = tmp_scanner.nextLine()
                var date = tmp_scanner.nextLong()
                tmp_scanner.nextLine()
                taskAdapter.add( TaskItem(tmp_str, Date(date)))
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
            taskAdapter.tasks.forEach{ item->
                out.println(item.title)
                /** Add more info and format if necessary */
                out.println(item.dueDate.time)
            }
        }
    }

    fun startDrag(task: TaskItem) {
        task_position = -1
        is_sortable = true
        drag_string = task
        taskAdapter.notifyDataSetChanged()
    }

    fun stopDrag() {
        task_position = -1
        is_sortable = false
        drag_string = TaskItem("", Date(0))
        taskAdapter.notifyDataSetChanged()
    }
}