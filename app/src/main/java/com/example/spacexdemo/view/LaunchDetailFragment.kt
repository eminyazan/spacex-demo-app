package com.example.spacexdemo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.spacexdemo.R
import com.example.spacexdemo.constans.LAUNCH_ID_KEY
import com.example.spacexdemo.constans.WEB_VIEW_DEFAULT_URL
import com.example.spacexdemo.databinding.FragmentLaunchDetailBinding
import com.example.spacexdemo.repo.BaseRepo
import com.example.spacexdemo.repo.BaseViewModelFactory
import com.example.spacexdemo.service.BaseHTTPService
import com.example.spacexdemo.viewmodel.LaunchDetailViewModel
import java.lang.NullPointerException

class LaunchDetailFragment : Fragment() {

    private var launchId: String? = null
    private lateinit var viewModel: LaunchDetailViewModel
    private lateinit var binding: FragmentLaunchDetailBinding
    private lateinit var loader: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loader = LoadingDialog(requireContext())

        registerViewModel()

        arguments?.let {
            launchId = it.get(LAUNCH_ID_KEY) as String?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_launch_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val res = registerViewModel()

        if (res) {
            arguments?.let {
                viewModel.getLaunch(it.get(LAUNCH_ID_KEY).toString())
                observeData()
            }

        } else {
            Toast.makeText(view.context, "Error occurred try again later", Toast.LENGTH_LONG).show()
        }


        binding.webViewButton.setOnClickListener {
            val launch = viewModel.launch.value
            launch?.let { launchNullable ->
                val launchUrl = launchNullable.links.reddit.launch
                if (launchUrl == null) {
                    goToWebView(view, WEB_VIEW_DEFAULT_URL)
                } else {
                    goToWebView(view, launchUrl.toString())
                }
            }

        }
    }

    private fun observeData() {
        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                if (it){
                    loader.show()
                }
            }
        }

        viewModel.launch.observe(viewLifecycleOwner) {
            it?.let {
                loader.cancel()
                binding.launch = it
            }
        }
    }

    private fun goToWebView(view: View, url: String) {

        val action = LaunchDetailFragmentDirections.goToWebView(url)
        Navigation.findNavController(view).navigate(action)

    }

    private fun registerViewModel(): Boolean {
        return try {
            val retrofitService = BaseHTTPService.getInstance()
            val mainRepository = BaseRepo(retrofitService)

            viewModel = ViewModelProvider(
                requireActivity(),
                BaseViewModelFactory(mainRepository)
            )[LaunchDetailViewModel::class.java]

            true
        } catch (e: NullPointerException) {
            println(e)
            false
        }

    }


}