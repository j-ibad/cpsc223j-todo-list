package com.group7.android.todolist

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.GestureDetector
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.group7.android.todolist.ui.main.SectionsPagerAdapter
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Broker.focused = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
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
                var cal = Calendar.getInstance()
                DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        val td : ToDo = sectionsPagerAdapter.getItem(0) as ToDo
                        td.addTasks( TaskItem(newTaskTitle.text.toString(), cal.time) )
                        dialog.dismiss()
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
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