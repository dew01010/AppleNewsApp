package com.dew.newsapplication.ui

import android.media.MediaPlayer
import android.media.MediaSync
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.dew.newsapplication.R
import com.dew.newsapplication.databinding.FragmentHelpBinding
import com.dew.newsapplication.databinding.FragmentNewsHeadlineListBinding
import com.dew.newsapplication.utility.pref.AppPref
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [HelpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HelpFragment : Fragment() {
    private lateinit var url: String

    //binding
    private var _binding: FragmentHelpBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private var currentPosition: Int = 0
    private var isVideoEnded: Boolean = false
    private lateinit var scheduledExecutorService: ScheduledExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_PARAM1) ?: ""
        }
        currentPosition = AppPref.getHelpVideoPosition(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playHelpVideo(url)
        binding.closeBt.setOnClickListener {
            AppPref.setHelpVideoChecked(requireContext(), true)
            parentFragment?.childFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

    override fun onPause() {
        super.onPause()
        AppPref.setHelpVideoPosition(requireContext(), currentPosition)
        binding.videoView.pause()
    }

    private fun playHelpVideo(url: String) {
        binding.pb.visibility = View.VISIBLE
        val mediaController = MediaController(requireContext())
        binding.videoView.setMediaController(mediaController)
        binding.videoView.setVideoPath(url)
        binding.videoView.seekTo(currentPosition)
        binding.videoView.setOnPreparedListener {
            binding.pb.visibility = View.GONE
            it.isLooping = true
            scheduledExecutorService = ScheduledThreadPoolExecutor(1)
            scheduledExecutorService.scheduleWithFixedDelay(Runnable {
                currentPosition = binding.videoView.currentPosition
            }, 1000, 1000, TimeUnit.MICROSECONDS)
            binding.videoView.start()
        }

        // handle error pop inCase  not able to play video
        binding.videoView.setOnErrorListener { p0, p1, p2 -> true }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment HelpFragment.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            HelpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}