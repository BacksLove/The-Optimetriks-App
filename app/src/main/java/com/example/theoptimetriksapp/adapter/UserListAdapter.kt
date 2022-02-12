package com.example.theoptimetriksapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theoptimetriksapp.MyApp
import com.example.theoptimetriksapp.R
import com.example.theoptimetriksapp.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class UserListAdapter (val userList: List<User>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>(), Filterable {

    private lateinit var context: Context
    private var userListFiltered : List<User> = userList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentUser = userListFiltered[position]
        Glide.with(context).asBitmap().load(currentUser.avatar).into(holder.userImageView)
        holder.userFirstnameTextView.text = currentUser.firstname
        holder.userLastnameTextView.text = currentUser.lastname
        holder.userEmailTextView.text = currentUser.email
        holder.userFavoriteImageView.setOnClickListener {
            setBootmarks(currentUser.isFavorite, holder.userFavoriteImageView)
            currentUser.isFavorite = !currentUser.isFavorite

            // Pas idéal mais synchro de l'utilisateur dans la bdd locale pour l'affichage en temps reel des bookmarks
            GlobalScope.launch {
                MyApp.db.userDao().addUserToFavorite(currentUser)
            }
        }
        setBootmarks(currentUser.isFavorite, holder.userFavoriteImageView)
    }

    override fun getItemCount() : Int {
        return userListFiltered.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        val userImageView: ImageView
        val userFirstnameTextView: TextView
        val userLastnameTextView: TextView
        val userEmailTextView: TextView
        val userFavoriteImageView: ImageView

        init {
            userImageView = view.findViewById(R.id.user_imageview)
            userFirstnameTextView = view.findViewById(R.id.user_firstname)
            userLastnameTextView = view.findViewById(R.id.user_lastname)
            userEmailTextView = view.findViewById(R.id.user_email)
            userFavoriteImageView = view.findViewById(R.id.user_favorite_imageview)
        }
    }

    // Fonction pour le changement d'etat de l'icone bookmark
    private fun setBootmarks(isFavorite: Boolean, imageView: ImageView) {
        if (isFavorite)
            Glide.with(context).asBitmap().load(R.drawable.favorite_fill).into(imageView)
        else
            Glide.with(context).asBitmap().load(R.drawable.favorite_empty).into(imageView)
    }

    // Fonction de filtrage pour la barre de recherche , sur les champs "lastname", "firstname" et "email"
    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    userListFiltered = userList
                } else {
                    val tempUserList = ArrayList<User>()
                    for (row in userList) {
                        if (row.lastname.toLowerCase(Locale.getDefault()).contains(charSearch.toLowerCase(Locale.getDefault())) ||
                                row.firstname.toLowerCase(Locale.getDefault()).contains(charSearch.toLowerCase(Locale.getDefault())) ||
                                row.email.toLowerCase(Locale.getDefault()).contains(charSearch.toLowerCase(Locale.getDefault()))) {
                                    tempUserList.add(row)
                        }
                    }
                    userListFiltered = tempUserList
                }
                val filterResults = FilterResults()
                filterResults.values = userListFiltered
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userListFiltered = results?.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }

}