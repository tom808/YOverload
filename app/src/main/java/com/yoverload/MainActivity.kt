package com.yoverload

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.yoverload.network.Controller

/**
 * Created by tom.egan on 03-Jan-2019.
 */
class MainActivity() : AppCompatActivity(), ItemReceiver {

    private lateinit var tvItems: TextView
    private val TAG = "MainActivity"
    private val controller = Controller()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        tvItems = findViewById(R.id.maxItem)
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
        tvItems.append(item.title)
    }
}