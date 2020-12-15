package com.group7.android.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_task_details.*

class TaskDetails : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        val titleView = findViewById<TextView>(R.id.task_details_title)
        titleView.text = intent.getStringExtra("TASK_TITLE");

        val index = intent.getIntExtra("INDEX", 0);

        val removeButton = findViewById<Button>(R.id.task_remove_button)
        val finishButton = findViewById<Button>(R.id.task_finish_button)
        val saveButton = findViewById<Button>(R.id.task_save_button)
        val editTask = findViewById<EditText>(R.id.task_edit_text)

        if (Broker.focused == 0) {
            editTask.setText(Broker.todo.taskAdapter.tasks.get(index))
        } else {
            editTask.setText(Broker.fin.taskAdapter.tasks.get(index))
        }

        System.out.println(Broker.focused)
        if(Broker.focused == 1){ //Tasks clicked on Finished tab
            finishButton.text = "To-Do"
        }

        removeButton.setOnClickListener{view->
            Broker.todo.taskAdapter.tasks.removeAt(index)
            val adapter = (Broker.todo.taskDisplay.adapter) as BaseAdapter
            adapter.notifyDataSetChanged()
            Broker.todo.changed_flag = true
            finish()
        }


        finishButton.setOnClickListener{view->
            if(Broker.focused == 0) { //To-do Tab: From To-Do to Finished
                val view = Broker.todo.taskAdapter.tasks.removeAt(index)
                Broker.fin.taskAdapter.add(view)
            }else{ //Finished Tab: From Finished to To-Do
                val view = Broker.fin.taskAdapter.tasks.removeAt(index)
                Broker.todo.taskAdapter.add(view)
            }

            val adapter1 = (Broker.todo.taskDisplay.adapter) as BaseAdapter
            val adapter2 = (Broker.fin.taskDisplay.adapter) as BaseAdapter
            adapter1.notifyDataSetChanged()
            adapter2.notifyDataSetChanged()

            Broker.todo.changed_flag = true
            Broker.fin.changed_flag = true
            finish()
        }

        saveButton.setOnClickListener{view->
            if(Broker.focused == 0) { //To-do Tab:
                Broker.todo.taskAdapter.tasks.set(index, editTask.text.toString())
            }else{ //Finished Tab:
                Broker.fin.taskAdapter.tasks.set(index, editTask.text.toString())
            }

            val adapter1 = (Broker.todo.taskDisplay.adapter) as BaseAdapter
            val adapter2 = (Broker.fin.taskDisplay.adapter) as BaseAdapter
            adapter1.notifyDataSetChanged()
            adapter2.notifyDataSetChanged()

            Broker.todo.changed_flag = true
            Broker.fin.changed_flag = true
            finish()
        }
    }
}