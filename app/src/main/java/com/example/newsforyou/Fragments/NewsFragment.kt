package com.example.newsforyou.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsforyou.Adapters.NewsAdapter
import com.example.newsforyou.Model.Article
import com.example.newsforyou.Model.NewsHeadlines
import com.example.newsforyou.Model.NewsModel
import com.example.newsforyou.Network.apiInterface
import com.example.newsforyou.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsFragment : Fragment() {

    lateinit var newsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        newsRecyclerView = view.findViewById(R.id.newsRecyclerView)
        newsRecyclerView.layoutManager = LinearLayoutManager(view.context)


        var newList = mutableListOf<NewsHeadlines>()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                var response: Response<NewsModel> = apiInterface().getNews()
                for (i in response.body()?.articles!!)
                    if (!i.urlToImage.isNullOrEmpty())
                    {
                            newList.add(
                                NewsHeadlines(
                                    i.author,
                                    i.source.id,
                                    i.source.name,
                                    i.title,
                                    i.description,
                                    i.url,
                                    i.urlToImage,
                                    i.publishedAt,
                                    i.content
                                )
                            )
                    }
                newsRecyclerView.adapter = NewsAdapter(view.context, newList)
            } catch (e: Exception) {
                Log.d("NewsError", e.message!!)
            }
        }
        return view
    }

}