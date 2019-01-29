package com.yoverload.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.yoverload.di.InjectorUtils
import com.yoverload.R

/**
 * Created by tom.egan on 03-Jan-2019.
 */
class MainActivity() : AppCompatActivity() {

    private var itemCount = 0

    private val TAG = "MainActivity"
    private val mAdapter = MainPageAdapter()
    private var mRecyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private val pgProgress: ProgressBar by lazy {
        findViewById<ProgressBar>(R.id.main_progress_bar)
    }
    private val mViewModel: MainPageViewModel by lazy {
        val factory = InjectorUtils.provideMainPageViewModelFactory(this)
        ViewModelProviders.of(this, factory).get(MainPageViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpRecyclerView()

        mViewModel.getTopStories().observe(this, Observer { items ->
            items?.let {
                mAdapter.setPageData(items)
                pgProgress.visibility = View.GONE
                mRecyclerView?.visibility = View.VISIBLE
            }
        })
    }

    private fun setUpRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)

        setContentView(R.layout.main_layout)
        mRecyclerView = findViewById(R.id.rv_main_items)
        mRecyclerView?.layoutManager = layoutManager
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_refresh) {
            pgProgress.visibility = View.VISIBLE
            mRecyclerView?.visibility = View.GONE
            mViewModel.getTopStories()
        }
        return true
    }

}