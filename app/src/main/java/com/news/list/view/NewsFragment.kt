package com.news.list.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.news.list.R
import com.news.list.databinding.ActivityNewsBinding
import com.news.list.factory.NewsFactory
import com.news.list.injection.Application
import com.news.list.injection.module.ConstantsModule
import com.news.list.utils.NetworkStateHandler
import com.news.list.viewmodel.NewsViewModel
import javax.inject.Inject


/**
 * News Fragment to show list of news
 */
class NewsFragment : Fragment(), NetworkStateHandler.NetworkStateListener {

    private val TAG = "NewsFragment"

    internal var  binding: ActivityNewsBinding? = null

    @Inject
    lateinit var networkStateHandler: NetworkStateHandler

    @Inject
    lateinit var constantsModule: ConstantsModule

    /**
     * Navigation controller
     */
    lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (binding === null) {
            Application.component.inject(this)
            val areaViewModel =    ViewModelProviders.of(this, NewsFactory()).get(NewsViewModel::class.java)
            binding = ActivityNewsBinding.inflate(inflater, container, false)
            binding!!.mainData = areaViewModel
            areaViewModel.getBlogArticleLink().observe(this, Observer { text -> loadWebViewFragment(text!!) })
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(this.activity!!, R.id.my_nav_host_fragment)
    }


    override fun onResume() {
        super.onResume()
        registerListeners()
    }

    override fun onStop() {
        super.onStop()
        unRegisterListeners()
    }

    fun registerListeners() {
        networkStateHandler.registerNetWorkStateBroadCast(this.context!!)
        networkStateHandler.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler.unRegisterNetWorkStateBroadCast(this.context!!)
    }

    override fun onNetworkStateReceived(online: Boolean) {
        Log.d(TAG, "onNetWorkStateReceivedM:$online")

        when (online) {
            true -> {
                binding!!.mainData!!.msgView = View.GONE
                if (binding!!.mainData!!.retryBtn == View.VISIBLE) binding!!.mainData!!.notifyChange()
            }
            false -> {
                binding!!.mainData!!.msgView = View.VISIBLE
                binding!!.mainData!!.msg =
                    this.context!!.resources.getString(R.string.network_ErrorMsg)
            }

        }
    }

    fun loadWebViewFragment(url: String) {
        try {
            val bundle = Bundle()
            bundle.putString(constantsModule.BUNDLE_KEY_URL(), url)
            navController.navigate(R.id.action_NewsFragment_to_WebViewFragment, bundle)
        } catch (e: Exception) {
            Log.d(TAG, "loadWebViewFragment: IllegalArgumentException$e")
        }


    }

}