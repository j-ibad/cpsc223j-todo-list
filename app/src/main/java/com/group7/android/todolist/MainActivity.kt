package com.group7.android.todolist

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.group7.android.todolist.ui.main.SectionsPagerAdapter


class MainActivity : AppCompatActivity() {
    val fm : FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, fm)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

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
                val td : ToDo = sectionsPagerAdapter.getItem(0) as ToDo
                td.addTasks(newTaskTitle.text.toString())
                dialog.dismiss()
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