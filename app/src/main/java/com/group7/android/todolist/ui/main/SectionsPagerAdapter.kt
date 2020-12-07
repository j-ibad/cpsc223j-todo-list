package com.group7.android.todolist.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.group7.android.todolist.Finish
import com.group7.android.todolist.R
import com.group7.android.todolist.ToDo

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, val fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    var td : ToDo = ToDo()
    var f : Finish = Finish()

    override fun getItem(position: Int): Fragment? {
        // return current tab
        when (position) {
            0 -> return td
            1 -> return f
            else -> return null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}