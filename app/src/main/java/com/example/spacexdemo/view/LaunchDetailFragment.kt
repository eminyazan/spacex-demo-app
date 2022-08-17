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


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = LoadingDialog(requireContext())
        registerViewModel()
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_launch_detail)
        appBar = (activity as AppCompatActivity).supportActionBar!!

        arguments?.let {
            launchId = LaunchDetailFragmentArgs.fromBundle(it).launchIdKey
            viewModel.getLaunch(launchId!!)
        }
        observeData()
        webViewButton()
    }

    private fun goToWebView(view: View, url: String) {
        // TODO: error fix it add parameter
        println("go to web view func")

        val action = LaunchDetailFragmentDirections.actionLaunchDetailFragmentToWebViewFragment()
        Navigation.findNavController(view).navigate(action)

    }

    private fun webViewButton() {
        binding.detailWebViewButton.setOnClickListener {
//            val launch = viewModel.launch.value
//            launch?.let { launchNullable ->
//                val launchUrl = launchNullable.links.reddit.launch
//                if (launchUrl == null) {
//                    goToWebView(it, WEB_VIEW_DEFAULT_URL)
//                } else {
//                    goToWebView(it, launchUrl.toString())
//                }
//            }

        }
    }

    private fun observeData() {
//        viewModel.loading.observe(viewLifecycleOwner) {
//            it?.let {
//                if (it) {
//                    loader.show()
//                }
//            }
//        }

        viewModel.launch.observe(viewLifecycleOwner) {
            it?.let {
                println("Fragment observe launch --> ${it.links.patch.large}")
                binding.launch = it
                appBar.title=it.name
                loader.cancel()
            }
        }

//        binding.launch=viewModel.getLaunch(launchId!!)
    }

    private fun registerViewModel() {
        viewModel = ViewModelProvider(this)[LaunchDetailViewModel::class.java]
    }


}