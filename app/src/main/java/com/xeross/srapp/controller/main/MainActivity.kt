package com.xeross.srapp.controller.main

import com.xeross.srapp.R
import com.xeross.srapp.adapter.GameAdapter
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.listener.ClickListener
import com.xeross.srapp.model.Game
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ClickListener<Game> {

    override fun getViewModelClass() = MainViewModel::class.java
    override fun getFragmentId() = R.layout.activity_main

    private var adapter: GameAdapter? = null
    private val games = ArrayList<Game>()

    override fun build() {
        getGames()
        adapter = GameAdapter(this, games, this).also {
            activity_main_recyclerview.setRecyclerViewAdapter(it, false)
        }
    }

    private fun getGames() {
        games.add(Game("Do stuff", getString(R.string.celeste), R.drawable.im_celeste, 113))
    }

    override fun onClick(o: Game) {
        // TODO("Do stuff..")
    }

    override fun onLongClick(o: Game) {}
}