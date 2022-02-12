package com.example.theoptimetriksapp.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theoptimetriksapp.R
import com.example.theoptimetriksapp.adapter.UserListAdapter

class BookmarksFragment : Fragment() {

    private lateinit var bookmarksViewModel: BookmarksViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bookmarksViewModel =
                ViewModelProvider(this).get(BookmarksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bookmarks, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.bookmarks_recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(context)
        bookmarksViewModel.favoriteUserList.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = UserListAdapter(it)
        })

        return root
    }
}