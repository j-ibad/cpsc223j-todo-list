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


class TaskAdapter(
    context: Context,
    var textViewResourceId: Int
) :
    ArrayAdapter<String>(context, textViewResourceId) {
    private val inflater: LayoutInflater = context.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var tasks : ArrayList<String> = ArrayList<String>()

    override fun getCount(): Int{ return tasks.size }
    override fun getItem(pos: Int): String? { return tasks[pos] }
    override fun getItemId(pos: Int): Long{ return pos.toLong() }
    override fun add(task_title: String?) {
        super.add(task_title)
        if (task_title != null) {
            tasks.add(task_title)
        }
    }
    override fun insert(task_title: String?, position: Int) {
        super.insert(task_title, position)
        if (task_title != null) {
            tasks.add(position, task_title)
        }
    }
    override fun remove(task_title: String?) {
        super.remove(task_title)
        tasks.remove(task_title)
    }
    override fun clear() { super.clear(); tasks.clear() }

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
//        val rowView = inflater.inflate(R.layout.task_item, parent, false)
//        val titleView = rowView.findViewById(R.id.task_item_title) as TextView
//        /* If more info needed, add views here */
        val task_item = getItem(pos) as String
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
            holder.draggable = view.findViewById(R.id.draggable) as TextView
            view.setTag(holder)
        } else {
            holder = view.getTag() as ViewHolder?
        }
        val string: String = tasks.get(pos)

        holder?.title?.setText(string)

        holder?.title?.setOnClickListener{ view ->
            val intent = Intent(this.context, TaskDetails::class.java).apply {
                putExtra("TASK_TITLE", task_item)
                putExtra("INDEX", pos)
            }
            startActivity(this.context, intent, null)
        }

        holder?.draggable?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {
                if (motionEvent.getAction() === MotionEvent.ACTION_DOWN) {
                    if (Broker.focused == 0) {
                        Broker.todo.startDrag(string)
                    } else {
                        Broker.fin.startDrag(string)
                    }
                    return true
                }
                return false
            }
        })

        val drag_string : String
        if (Broker.focused == 0) {
            drag_string = Broker.todo.drag_string
        } else {
            drag_string = Broker.fin.drag_string
        }

        if (drag_string == string) {
            view?.setBackgroundColor(Color.parseColor("#9933b5e5"))
        } else {
            view?.setBackgroundColor(Color.TRANSPARENT)
        }

        return view!!
    }
}

class ViewHolder {
    var title: TextView? = null
    var draggable: TextView? = null
}