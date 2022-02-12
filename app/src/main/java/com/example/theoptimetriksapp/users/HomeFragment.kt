package com.example.theoptimetriksapp.users

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theoptimetriksapp.R
import com.example.theoptimetriksapp.adapter.UserListAdapter


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.user_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)

        homeViewModel.userList.observe(viewLifecycleOwner, Observer {
            userListAdapter = UserListAdapter(it)
            recyclerView.adapter = userListAdapter
        })

        setHasOptionsMenu(true)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val menuInflater =  inflater.inflate(R.menu.top_bar_menu, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search_query_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                userListAdapter.filter.filter(query)
                return true
            }
        })
        return menuInflater
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                homeViewModel.refreshUsers()
            }
            else -> return false
        }
        return true
    }
}