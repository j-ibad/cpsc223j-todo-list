package com.group7.android.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class TaskDetails : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        val titleView = findViewById<TextView>(R.id.task_details_title)
        titleView.text = intent.getStringExtra("TASK_TITLE");

        val index = intent.getIntExtra("INDEX", 0);

        val removeButton = findViewById<Button>(R.id.task_remove_button)
        val finishButton = findViewById<Button>(R.id.task_finish_button)
        System.out.println(Broker.focused)
        if(Broker.focused == 1){ //Tasks clicked on Finished tab
            finishButton.text = "To-Do"
        }

        removeButton.setOnClickListener{view->
            Broker.todo.tasks.removeAt(index)
            val adapter = (Broker.todo.taskDisplay.adapter) as BaseAdapter
            adapter.notifyDataSetChanged()
            Broker.todo.changed_flag = true
            finish()
        }


        finishButton.setOnClickListener{view->
            if(Broker.focused == 0) { //To-do Tab: From To-Do to Finished
                val view = Broker.todo.tasks.removeAt(index)
                Broker.fin.tasks.add(view)
            }else{ //Finished Tab: From Finished to To-Do
                val view = Broker.fin.tasks.removeAt(index)
                Broker.todo.tasks.add(view)
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