package com.group7.android.todolist

import android.app.AlertDialog
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.group7.android.todolist.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        // adding task to display
//        val tasks : ArrayList<String> = ArrayList<String>()
//        val taskAdapter : ArrayAdapter<String> = ArrayAdapter<String>(this,
//            android.R.layout.simple_list_item_1, tasks)
//        val taskDisplay : ListView = getView() findViewById(R.id.task_display)
//
//        taskDisplay.adapter = taskAdapter
//        tasks.add("task 1")
//        tasks.add("task 2")

        // pop up windows for creating tasks
        var dialogBuilder : AlertDialog.Builder
        var dialog : AlertDialog
        var newTaskTitle : EditText
        var popupCancel : Button
        var popupSave : Button

        fun createNewTask() {
            dialogBuilder = AlertDialog.Builder(this)
            val popupView : View = getLayoutInflater().inflate(R.layout.popup, null);

            newTaskTitle = popupView.findViewById(R.id.new_task_title)
            popupSave = popupView.findViewById(R.id.save_button)
            popupCancel = popupView.findViewById(R.id.cancel_button)

            dialogBuilder.setView(popupView)
            dialog = dialogBuilder.create()
            dialog.show()

            popupSave.setOnClickListener { view ->

            }
            popupCancel.setOnClickListener { view ->
                dialog.dismiss()
            }
        }

        fab.setOnClickListener { view ->
            createNewTask()
        }
    }
}