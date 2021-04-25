package com.example.newsforyou.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsforyou.Activities.NewsActivity
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
        if (newsHeadlines[position].title != null)
        {
            holder.title.text = newsHeadlines[position].title
        }
        if (newsHeadlines[position].description != null)
        {
            holder.title.text = newsHeadlines[position].description
        }
        if (newsHeadlines[position].content != null)
        {
            holder.title.text = newsHeadlines[position].content
        }

        Glide.with(context).
        load(newsHeadlines[position].urlToImage)
                .placeholder(R.drawable.background2)
                .into(holder.image)

        holder.date.text = convertISOTime(context, newsHeadlines.get(position).publishedAt)


        holder.item.setOnClickListener {
            val intent = Intent(context, NewsActivity::class.java)
            intent.putExtra("title", newsHeadlines.get(position).title)
            intent.putExtra("desc", newsHeadlines.get(position).description)
            intent.putExtra("author", newsHeadlines.get(position).author)
            intent.putExtra("content", newsHeadlines.get(position).content)
            intent.putExtra("url", newsHeadlines.get(position).url)
            intent.putExtra("urlToImage", newsHeadlines.get(position).urlToImage)
            intent.putExtra("publishedAt", newsHeadlines.get(position).publishedAt)
            context.startActivity(intent)
        }
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