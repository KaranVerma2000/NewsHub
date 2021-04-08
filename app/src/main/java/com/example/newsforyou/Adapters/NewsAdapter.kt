package com.example.newsforyou.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsforyou.Model.NewsHeadlines
import com.example.newsforyou.R
import com.example.newsforyou.Utils.UtilMethods.convertISOTime

class NewsAdapter(var context : Context , var newsHeadlines : List<NewsHeadlines>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        if (newsHeadlines.get(position).title != null)
        {
            holder.title.setText(newsHeadlines.get(position).title)
        }
        if (newsHeadlines.get(position).description != null)
        {
            holder.title.setText(newsHeadlines.get(position).description)
        }
        if (newsHeadlines.get(position).content != null)
        {
            holder.title.setText(newsHeadlines.get(position).content)
        }

        Glide.with(context).
        load(newsHeadlines.get(position).urlToImage)
                .placeholder(R.drawable.background2)
                .into(holder.image)

        holder.date.setText(convertISOTime(context, newsHeadlines.get(position).publishedAt))
    }

    override fun getItemCount(): Int {
        return newsHeadlines.size
    }

    class NewsViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        val image : ImageView = itemview.findViewById(R.id.news_image)
        val title : TextView = itemview.findViewById(R.id.newsTitle)
        val date : TextView = itemview.findViewById(R.id.news_date)
        val item : View = itemView
    }
}