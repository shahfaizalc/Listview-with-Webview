package com.news.list.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.news.list.R
import com.news.list.databinding.ActivityWebviewBinding
import com.news.list.factory.WebViewFactory
import com.news.list.injection.Application
import com.news.list.injection.module.ConstantsModule
import com.news.list.utils.NetworkStateHandler
import com.news.list.viewmodel.WebViewModel
import javax.inject.Inject


/**
 * the fragment to show the article on web view based on the user selection.
 */

class WebViewFragment : Fragment(), NetworkStateHandler.NetworkStateListener {

    //TAG: Class name
    private val TAG = "WebViewFragment"

    //To handle the network state change
    @Inject
    lateinit var networkStateHandler: NetworkStateHandler

    @Inject
    lateinit var constantsModule: ConstantsModule

    //Binding
    @Transient
    lateinit var binding: ActivityWebviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Application.component.inject(this)
        val blogUrl = arguments!!.getString(constantsModule.BUNDLE_KEY_URL())
        binding = ActivityWebviewBinding.inflate(inflater, container, false);
        val webViewModel =    ViewModelProviders.of(this, WebViewFactory()).get(
            WebViewModel::class.java)
        binding.webViewData = webViewModel
        binding.webViewData!!.webViewUrl = blogUrl
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        registerListeners()
    }

    override fun onStop() {
        super.onStop()
        unRegisterListeners()
    }

    /**
     * Register network state handler
     */
    fun registerListeners() {
        networkStateHandler.registerNetWorkStateBroadCast(this.context!!)
        networkStateHandler.setNetworkStateListener(this)
    }

    /**
     * To Unregister network state handler
     */
    fun unRegisterListeners() {
        networkStateHandler.unRegisterNetWorkStateBroadCast(this.context!!)
    }

    /**
     * To handle on network state change received.
     * @param online: network state
     */
    override fun onNetworkStateReceived(online: Boolean) {
        Log.d(TAG, "onNetWorkStateReceived :$online")
        when (online) {
            true -> {
                binding.webViewData!!.msgView = View.GONE
            }
            false -> {
                binding.webViewData!!.msgView = View.VISIBLE
                binding.webViewData!!.msg =
                    this.context!!.resources.getString(R.string.network_ErrorMsg)
            }
        }
    }
}

