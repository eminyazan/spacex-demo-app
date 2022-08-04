package com.example.spacexdemo.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.spacexdemo.R
import com.example.spacexdemo.constans.LAUNCH_ID_KEY
import com.example.spacexdemo.constans.WEB_VIEW_DEFAULT_URL
import com.example.spacexdemo.databinding.ActivityLaunchDetailBinding
import com.example.spacexdemo.repo.BaseRepo
import com.example.spacexdemo.repo.BaseViewModelFactory
import com.example.spacexdemo.service.BaseHTTPService
import com.example.spacexdemo.viewmodel.LaunchDetailViewModel
import java.lang.NullPointerException

class LaunchDetailActivity : AppCompatActivity() {

    private var launchId: String? = null
    private lateinit var viewModel: LaunchDetailViewModel
    private lateinit var binding: ActivityLaunchDetailBinding
    private lateinit var loader: LoadingDialog

    // TODO: Launch image not loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        registerViewModel()
        loader = LoadingDialog(this@LaunchDetailActivity)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch_detail)


        intent?.let {
            launchId = it.getStringExtra(LAUNCH_ID_KEY)
        }


        val res = registerViewModel()
        println("Res --> $res")
        showElements(res)

        webViewButton()
    }

    private fun goToWebView(view: View, url: String) {
        // TODO: error fix it
        val action = LaunchDetailActivityDirections.goToWebView(url)
        Navigation.findNavController(view).navigate(action)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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

    private fun showElements(res: Boolean) {
        if (res) {
            intent?.let {
                viewModel.getLaunch(it.getStringExtra(LAUNCH_ID_KEY)!!)
                observeData()
            }

        } else {
            Toast.makeText(this, "Error occurred try again later", Toast.LENGTH_LONG).show()
        }
    }

    private fun observeData() {
        viewModel.loading.observe(this) {
            it?.let {
                if (it) {
                    loader.show()
                }
            }
        }

        viewModel.launch.observe(this) {
            it?.let {
                loader.cancel()
                binding.launch = it
                title = it.name
            }
        }
    }

    private fun registerViewModel(): Boolean {
        return try {
            val retrofitService = BaseHTTPService.getInstance()
            val mainRepository = BaseRepo(retrofitService)

            viewModel = ViewModelProvider(
                this,
                BaseViewModelFactory(mainRepository)
            )[LaunchDetailViewModel::class.java]

            true
        } catch (e: NullPointerException) {
            println(e)
            false
        }

    }


}