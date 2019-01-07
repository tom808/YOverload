package com.yoverload

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.yoverload.network.Controller

/**
 * Created by tom.egan on 03-Jan-2019.
 */
class MainActivity() : AppCompatActivity(), ItemReceiver {

    private var itemCount = 0

    private var tvItems: TextView = findViewById(R.id.maxItem)
    private val TAG = "MainActivity"
    private val controller = Controller()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_refresh) {
            controller.getTopStories(this)
        }
        return true
    }

    override fun receiveItem(item: Item) {
        Log.i(TAG, "Receieved item " + item.id)
        itemCount++
        tvItems.append(item.title)

        if (itemCount > 20) {
            val pgProgress = findViewById<ProgressBar>(R.id.main_progress_bar)
            pgProgress.visibility = View.GONE
            tvItems.visibility = View.VISIBLE
        }
    }
}