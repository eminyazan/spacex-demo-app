package com.example.spacexdemo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.spacexdemo.R
import com.example.spacexdemo.constans.LAUNCH_ID_KEY
import com.example.spacexdemo.constans.WEB_VIEW_DEFAULT_URL
import com.example.spacexdemo.databinding.FragmentLaunchDetailBinding
import com.example.spacexdemo.viewmodel.LaunchDetailViewModel

class LaunchDetailFragment : Fragment() {

    private var launchId: String? = null
    private lateinit var viewModel: LaunchDetailViewModel
    private lateinit var binding: FragmentLaunchDetailBinding
    private lateinit var loader: LoadingDialog
    private lateinit var appBar: ActionBar

    // TODO: Launch image not loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: find a solution for updating app bar from fragment and convert this class to fragment
//        val actionBar: ActionBar? = supportActionBar
//        actionBar?.setDisplayHomeAsUpEnabled(true)
        loader = LoadingDialog(requireContext())
        registerViewModel()
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_launch_detail)


        arguments?.let {
            launchId = LaunchDetailFragmentArgs.fromBundle(it).launchIdKey
            viewModel.getLaunch(launchId!!)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_launch_detail, container, false)
        appBar = (activity as AppCompatActivity).supportActionBar!!
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        webViewButton()
    }

    private fun goToWebView(view: View, url: String) {
        // TODO: error fix it add parameter
        val action = LaunchDetailFragmentDirections.actionLaunchDetailFragmentToWebViewFragment()
        Navigation.findNavController(view).navigate(action)

    }

    private fun webViewButton() {
        binding.webViewButton.setOnClickListener {
            val launch = viewModel.launch.value
            launch?.let { launchNullable ->
                val launchUrl = launchNullable.links.reddit.launch
                if (launchUrl == null) {
                    goToWebView(binding.detailWidgetConstrainLayout.rootView, WEB_VIEW_DEFAULT_URL)
                } else {
                    goToWebView(binding.detailWidgetConstrainLayout.rootView, launchUrl.toString())
                }
            }

        }
    }

    private fun observeData() {
        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    loader.show()
                }
            }
        }

        viewModel.launch.observe(viewLifecycleOwner) {
            it?.let {
                loader.cancel()
                binding.launch = it
                appBar.title=it.name
            }
        }
    }

    private fun registerViewModel() {
        viewModel = ViewModelProvider(this)[LaunchDetailViewModel::class.java]
    }


}