package com.yoverload.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import com.yoverload.di.InjectorUtils
import com.yoverload.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by tom.egan on 03-Jan-2019.
 */
class MainActivity : AppCompatActivity() {

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
    }

    private fun setUpRecyclerView() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, RecyclerView.VERTICAL, false)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                pgProgress.visibility = View.VISIBLE
                mRecyclerView?.visibility = View.GONE
                refreshList()
            }
        }
        return true
    }

    private fun refreshList() = GlobalScope.launch(Dispatchers.Main) {
        val topStories = mViewModel.topStories.await()
        topStories.observe(this@MainActivity, Observer { items ->
            mAdapter.setPageData(items)
            pgProgress.visibility = View.GONE
            mRecyclerView?.visibility = View.VISIBLE
        })
    }

}