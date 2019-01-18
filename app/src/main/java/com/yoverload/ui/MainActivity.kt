package com.yoverload.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
    private var mRecyclerView: RecyclerView? = null
    private val pgProgress: ProgressBar by lazy {
        findViewById<ProgressBar>(R.id.main_progress_bar)
    }
    private val mViewModel: MainPageViewModel by lazy {
        val factory = InjectorUtils.provideMainPageViewModelFactory()
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
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

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