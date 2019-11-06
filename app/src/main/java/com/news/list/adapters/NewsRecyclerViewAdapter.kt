package com.news.list.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.news.list.databinding.ListItemNewsBinding
import com.news.list.injection.Application
import com.news.list.listeners.NewsEventCallback
import com.news.list.viewmodel.NewsViewModel

/**
 * News recycler view adapter to view list of items
 */
class NewsRecyclerViewAdapter(private val newsViewModel: NewsViewModel) :
    RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>(), NewsEventCallback {

    /**
     * TAG
     */
    private val TAG = "ArtistRecyclerAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(ListItemNewsBinding.inflate(LayoutInflater.from(
        parent.context), parent, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Application.component.inject(this)
        viewHolder.binding.apply {
            articlesItem = newsViewModel.articlesList[position]
            itemPosition = position
            mainDataModel = newsViewModel
        }
        viewHolder.binding.simpleListAdapter = this
        viewHolder.binding.setItemClickListener(this)
    }

    override fun getItemCount() = newsViewModel.articlesList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position.let { position }

    inner class ViewHolder(var binding: ListItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClickNewsListItem(newsViewModel: NewsViewModel, position: Int) {
        Log.d(TAG, "Click: " + newsViewModel.articlesList[position].author)
        newsViewModel.doUpdateClickedItem(newsViewModel.articlesList[position])
    }
}
