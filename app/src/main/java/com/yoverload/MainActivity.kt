package com.yoverload

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.yoverload.network.Item

/**
 * Created by tom.egan on 03-Jan-2019.
 */
class MainActivity() : AppCompatActivity(), ItemReceiver {

    private var itemCount = 0

    private val TAG = "MainActivity"
    private var mViewModel : MainPageViewModel = ViewModelProviders.of(this).get(MainPageViewModel::class.java)
    private val mAdapter = MainPageAdapter()
    private  lateinit var mRecyclerView : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUprecyclerView()

        mViewModel.items?.observe(this, Observer { items ->
            items?.let {
                mAdapter.setPageData(items)
            }
        })
    }

    private fun setUprecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        setContentView(R.layout.main_layout)
        mRecyclerView = findViewById(R.id.rv_main_items)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_refresh) {
            // mController.getTopStories(this)
        }
        return true
    }

    override fun receiveItem(item: Item) {
        Log.i(TAG, "Received item " + item.id)

        mAdapter.setPageData(item)
        mAdapter.notifyDataSetChanged()

        if (mAdapter.itemCount > 5) {
            val pgProgress = findViewById<ProgressBar>(R.id.main_progress_bar)
            pgProgress.visibility = View.GONE
            mRecyclerView.visibility = View.VISIBLE
        }
    }
}