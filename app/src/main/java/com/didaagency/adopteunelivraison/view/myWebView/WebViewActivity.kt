package com.didaagency.adopteunelivraison.view.myWebView

import android.annotation.TargetApi
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import androidx.activity.viewModels
import com.didaagency.adopteunelivraison.R
import com.didaagency.adopteunelivraison.databinding.ActivityWebViewBinding
import com.didaagency.adopteunelivraison.utils.*
import com.didaagency.adopteunelivraison.view.baseViews.BaseActivity

class WebViewActivity : BaseActivity() {

    private val viewModel : WebviewViewModel by viewModels()
    lateinit var binding : ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        getExtrasFromIntent()
    }

    private fun getExtrasFromIntent() {
        if (intent != null) {
//            missingChildItem = Gson().fromJson(intent.getStringExtra("missingChild"), MissingChildItem::class.java)
             var title: String? = intent.getStringExtra("title")
             var webLink: String? = intent.getStringExtra("link")
            binding.tvTitle.text = title
            webLink.let {
                loadWebView(it!!)
            }
        }
    }

    private fun initViews() {
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = viewModel
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.clickEvent.observe(this) {
            when (it) {
                ClickEvents.BACK -> {
                    finish()
                }
                ClickEvents.INTERNET_CONNECTION -> {
                    showToast(resources.getString(R.string.internet_connection_message))
                }
            }
        }
    }


    private fun loadWebView(url: String) {
        binding.webView.settings.javaScriptEnabled = true // enable javascript
        binding.webView.settings.pluginState = WebSettings.PluginState.ON
        showLoadingDialog()
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView, url: String) {
                super.onLoadResource(view, url)
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                hideLoadingDialog()
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                showToast(description)
                hideLoadingDialog()
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView,
                req: WebResourceRequest,
                rerr: WebResourceError
            ) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(
                    view,
                    rerr.errorCode,
                    rerr.description.toString(),
                    req.url.toString()
                )
                hideLoadingDialog()
            }

            override fun onRenderProcessGone(
                view: WebView,
                detail: RenderProcessGoneDetail
            ): Boolean {
                hideLoadingDialog()
                Log.e(TAG, "onRenderProcessGone: $detail")
                return super.onRenderProcessGone(view, detail)
            }

            override fun onReceivedHttpError(
                view: WebView,
                request: WebResourceRequest,
                errorResponse: WebResourceResponse
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                Log.e(TAG, "onReceivedHttpError: $errorResponse")
                hideLoadingDialog()
            }

            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                super.onReceivedSslError(view, handler, error)
                Log.e(TAG, "onReceivedSslError: $error")
                hideLoadingDialog()
            }

            override fun onSafeBrowsingHit(
                view: WebView,
                request: WebResourceRequest,
                threatType: Int,
                callback: SafeBrowsingResponse
            ) {
                super.onSafeBrowsingHit(view, request, threatType, callback)
                Log.e(TAG, "onSafeBrowsingHit: $threatType")
                hideLoadingDialog()
            }

            override fun onUnhandledKeyEvent(view: WebView, event: KeyEvent) {
                super.onUnhandledKeyEvent(view, event)
                hideLoadingDialog()
            }
        }
        //        mWebview .loadUrl("http://www.google.com");
        binding.webView.loadUrl(url.trim { it <= ' ' })
    }
}