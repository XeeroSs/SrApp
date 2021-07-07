package com.xeross.srapp.controller.main

import androidx.lifecycle.Observer
import com.xeross.srapp.R
import com.xeross.srapp.adapter.GameAdapter
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.model.Game
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ClickListener<Game> {

    override fun getViewModelClass() = MainViewModel::class.java
    override fun getFragmentId() = R.layout.activity_main

    private lateinit var adapter: GameAdapter
    private val games = ArrayList<Game>()

    override fun build() {
        (viewModel as? MainViewModel)?.build()
        adapter = GameAdapter(this, games, this).also {
            activity_main_recyclerview.setRecyclerViewAdapter(it, false)
        }
        getGames()
    }

    private fun getGames() {
        (viewModel as? MainViewModel)?.getCeleste()?.observe(this, {
            it?.let { game ->
                games.add(game)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onClick(o: Game) {
        // TODO("Do stuff..")
    }

    override fun onLongClick(o: Game) {}
}