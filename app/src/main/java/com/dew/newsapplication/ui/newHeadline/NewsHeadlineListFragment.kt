package com.dew.newsapplication.ui.newHeadline

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.MediaController
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dew.newsapplication.R
import com.dew.newsapplication.common.BaseFrag
import com.dew.newsapplication.common.ItemOffsetDecoration
import com.dew.newsapplication.common.OnRetryConnectionListener
import com.dew.newsapplication.databinding.FragmentNewsHeadlineListBinding
import com.dew.newsapplication.model.ArticleInfo
import com.dew.newsapplication.ui.HelpFragment
import com.dew.newsapplication.ui.newHeadline.adapter.NewsHeadlineAdapter
import com.dew.newsapplication.ui.webView.WebViewActivity
import com.dew.newsapplication.utility.pref.AppPref
import com.dew.newsapplication.viewModel.NewsViewModel
import com.msewa.healthism.util.Status
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * A NewsHeadlineListFragment  containing a  news list form different news sources.
 */
class NewsHeadlineListFragment : BaseFrag(), NewsHeadlineAdapter.NewsHeadlineAdapterCallback,
    OnRetryConnectionListener {

    // viewModel
    private val viewModel: NewsViewModel by viewModels()

    //binding
    private var _binding: FragmentNewsHeadlineListBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var myAdapter: NewsHeadlineAdapter
    private lateinit var list: ArrayList<ArticleInfo?>
    private var isScrolling: Boolean = false
    private var newsSource: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsSource = arguments?.getString(SOURCE, "bitcoin")
        list = arrayListOf()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsHeadlineListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initial status of views
        binding.loadingLayout.visibility = View.GONE
        binding.noDataTv.visibility = View.GONE

        setUpAdapter()
        setScrollListener()
        getSourceNews()
    }

    private fun setUpAdapter() {
        myAdapter = NewsHeadlineAdapter(requireContext(), list, this@NewsHeadlineListFragment)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(
            ItemOffsetDecoration(
                context?.resources!!.getDimensionPixelOffset(
                    R.dimen.space
                ), 1
            )
        )
        binding.recyclerView.adapter = myAdapter
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val SOURCE = "news_source_detail"

        /**
         * Returns a new instance of this fragment for the given list
         * number.
         */
        @JvmStatic
        fun newInstance(newsSource: String): NewsHeadlineListFragment {
            return NewsHeadlineListFragment().apply {
                arguments = Bundle().apply {
                    putString(SOURCE, newsSource)
                }
            }
        }
    }

    // this method is get data from source
    private fun getSourceNews() {
        viewModel.fetchNewsHeadlines(newsSource!!)
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer {
            when (it.code) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.SUCCESS -> {
                    dismissLoading()
                    if (!it.data?.articles.isNullOrEmpty()) {
                        this.list.addAll(it.data?.articles!!)
                        myAdapter.notifyDataSetChanged()
                    }
                    if (list.size > 0) binding.noDataTv.visibility =
                        View.GONE else binding.noDataTv.visibility = View.VISIBLE

                    showHelpVideo()
                }
                Status.ERROR -> {
                    showMsg(it.message!!)
                }
                Status.NO_INTERNET -> {
                    noInternet(this@NewsHeadlineListFragment, 0)
                }
            }
        })
    }


    // this is callback function form NewsHeadlineAdapter it handles click action on Item
    override fun onClickRow(url: String) {
        var bundle = bundleOf("param1" to url)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        // clear binding
        _binding = null
    }

    // this method called, if internet connection lost
    override fun onConnectionRetry(value: Int) {
        viewModel.fetchNewsHeadlines(newsSource!!)
    }

    // this method handle list scrolling to check if find  last item then call view model method[fetchNewsHeadlines()] to fetch data form network data
    private fun setScrollListener() {
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null) {
                    val findFirstVisibleItemPosition =
                        linearLayoutManager.findFirstVisibleItemPosition()
                    val visibleProductCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount

                    if (isScrolling && findFirstVisibleItemPosition.plus(visibleProductCount) == totalItemCount) {
                        isScrolling = false
                        viewModel.fetchNewsHeadlines(newsSource!!)
                    }
                }

            }
        }
        binding.recyclerView.addOnScrollListener(listener)
    }

    private fun showHelpVideo(){
        if (!AppPref.isHelpVideoChecked(requireContext())) {
            binding.helpContainer.visibility=View.VISIBLE
            binding.helpContainer.visibility=View.VISIBLE
            childFragmentManager.beginTransaction().add(R.id.help_container,HelpFragment.newInstance(
                "https://firebasestorage.googleapis.com/v0/b/videoplayer-bb7be.appspot.com/o/This%20is%20iPhone%2012%20Pro%20%E2%80%94%20Apple.mp4?alt=media&token=b923b705-f76d-43ec-9916-8c2e8e5212cd"
            ),"helpFrag").commit()        }else{
            binding.helpContainer.visibility=View.GONE
        }
    }
}