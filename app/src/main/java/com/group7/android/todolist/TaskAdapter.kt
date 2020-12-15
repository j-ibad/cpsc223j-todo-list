package com.group7.android.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.*
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import android.view.GestureDetector;
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TaskAdapter(
    context: Context,
    var textViewResourceId: Int
) :
    ArrayAdapter<TaskItem>(context, textViewResourceId) {
    private val inflater: LayoutInflater = context.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var tasks : ArrayList<TaskItem> = ArrayList<TaskItem>()

    override fun getCount(): Int{ return tasks.size }
    override fun getItem(pos: Int): TaskItem? { return tasks[pos] }
    override fun getItemId(pos: Int): Long{ return pos.toLong() }
    override fun add(task: TaskItem?) {
        super.add(task)
        if (task != null) {
            tasks.add(task)
        }
    }
    override fun insert(task: TaskItem?, position: Int) {
        super.insert(task, position)
        if (task != null) {
            tasks.add(position, task)
        }
    }
    override fun remove(task: TaskItem?) {
        super.remove(task)
        tasks.remove(task)
    }
    override fun clear() { super.clear(); tasks.clear() }

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
//        val rowView = inflater.inflate(R.layout.task_item, parent, false)
//        val titleView = rowView.findViewById(R.id.task_item_title) as TextView
//        val dueDateView = rowView.findViewById(R.id.task_item_due_date) as TextView
//        /* If more info needed, add views here */
        val task_item = getItem(pos) as TaskItem
//        titleView.text = task_item.title
        /* Fill in views with TaskItem member fields */
//
//        rowView.setOnClickListener{ view ->
//            val intent = Intent(this.context, TaskDetails::class.java).apply {
//                putExtra("TASK_TITLE", task_item.title)
//                putExtra("INDEX", pos)
//            }
//            startActivity(this.context, intent, null)
//        }

        val holder: ViewHolder?
        var view: View? = convertView
        if (view == null) {
            view = inflater.inflate(textViewResourceId, null)
            assert(view != null)
            holder = ViewHolder()
            holder.title = view.findViewById(R.id.title) as TextView
            holder.dueDate = view.findViewById(R.id.dueDate) as TextView
            holder.draggable = view.findViewById(R.id.draggable) as TextView
            view.setTag(holder)
        } else {
            holder = view.getTag() as ViewHolder?
        }
        val tmp_task: TaskItem = tasks.get(pos)

        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        holder?.title?.setText(tmp_task.title)// + " " + sdf.format(tmp_task.dueDate.time))
        holder?.dueDate?.setText(sdf.format(tmp_task.dueDate.time))

        holder?.title?.setOnClickListener{ view ->
            val intent = Intent(this.context, TaskDetails::class.java).apply {
                putExtra("TASK_TITLE", task_item.title)
                putExtra("TASK_DUE_DATE", task_item.dueDate.time)
                putExtra("INDEX", pos)
            }
            startActivity(this.context, intent, null)
        }

        holder?.draggable?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {
                if (motionEvent.getAction() === MotionEvent.ACTION_DOWN) {
                    if (Broker.focused == 0) {
                        Broker.todo.startDrag(tmp_task)
                    } else {
                        Broker.fin.startDrag(tmp_task)
                    }
                    return true
                }
                return false
            }
        })

        val drag_string : TaskItem
        if (Broker.focused == 0) {
            drag_string = Broker.todo.drag_string
        } else {
            drag_string = Broker.fin.drag_string
        }

        if (drag_string.equals(tmp_task)) {
            view?.setBackgroundColor(Color.parseColor("#9933b5e5"))
        } else {
            view?.setBackgroundColor(Color.TRANSPARENT)
        }

        return view!!
    }
}

class ViewHolder {
    var title: TextView? = null
    var dueDate: TextView? = null
    var draggable: TextView? = null
}