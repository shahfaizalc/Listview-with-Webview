package com.news.list

import android.text.TextUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.list.adapters.NewsRecyclerViewAdapter
import com.news.list.handlers.RecyclerLoadNewsHandler
import com.news.list.viewmodel.NewsViewModel
import com.news.list.viewmodel.WebViewModel
import com.squareup.picasso.Picasso

/**
 * News articles Recyclerview
 *
 */
@BindingAdapter("app:newsRecycler")
fun adapter(recyclerView: RecyclerView, newsViewModel: NewsViewModel) {

    val linearLayoutManager = LinearLayoutManager(recyclerView.context)
    val listAdapter = NewsRecyclerViewAdapter(newsViewModel)
    val bindingAdapter = RecyclerLoadNewsHandler(newsViewModel, listAdapter,recyclerView)
    recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager
    recyclerView.adapter = listAdapter
    bindingAdapter.scrollListener( linearLayoutManager)
    bindingAdapter.initRequest(1)
}

/**
 * To add an image to ImageView
 */
@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    when (TextUtils.isEmpty(imageUrl)) {
        true -> view.setImageDrawable(view.context.getDrawable(R.drawable.app_icon_square));
        false -> {
            Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.app_icon_square)
                    .placeholder(R.drawable.app_icon_square)
                    .into(view)
        }
    }
}


/**
 * To set webview client
 * @param webView : Article web view
 * @param client : web view client
 */
@BindingAdapter("setWebViewClient")
fun setWebViewClient(webView: WebView, client: WebViewClient) {
    webView.webViewClient = client
    WebView.setWebContentsDebuggingEnabled(false)
    webView.settings.javaScriptEnabled
    webView.settings.loadsImagesAutomatically
}

/**
 * To load the webview
 * @param webView : Article web view
 * @param webViewModel: webview viewmodel class
 */
@BindingAdapter("loadUrl")
fun loadUrl(webView: WebView, webViewModel: WebViewModel) {
    with(webViewModel) {
        when (webViewUrl) {
            "" -> {
                msgView = android.view.View.VISIBLE
                msg = webView.context.resources.getString(R.string.webview_error)
                progressBarVisible = android.view.View.GONE
            }
            else -> webView.loadUrl(webViewUrl)
        }
    }
}
