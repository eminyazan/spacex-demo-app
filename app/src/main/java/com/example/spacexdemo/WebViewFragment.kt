package com.example.spacexdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.spacexdemo.constans.WEB_VIEW_DEFAULT_URL
import com.example.spacexdemo.constans.WEB_VIEW_KEY
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment() {
    private var url: String = WEB_VIEW_DEFAULT_URL


    private fun setupWebView(url: String) {
        customWebView.webViewClient = WebViewClient()
        customWebView.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            val res = bundle.getString(WEB_VIEW_KEY)
            res?.let {
                url = it
                setupWebView(url)
                println("URL --> $url")
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }


}