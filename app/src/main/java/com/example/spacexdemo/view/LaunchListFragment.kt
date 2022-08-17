package com.example.spacexdemo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexdemo.R
import com.example.spacexdemo.adapter.LaunchClickListener
import com.example.spacexdemo.adapter.LaunchListAdapter
import com.example.spacexdemo.databinding.FragmentLaunchListBinding
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.model.LocalLaunch
import com.example.spacexdemo.viewmodel.LaunchListViewModel
import kotlinx.android.synthetic.main.fragment_launch_list.*


class LaunchListFragment : Fragment(), LaunchClickListener {

    private lateinit var launchListViewModel: LaunchListViewModel
    private lateinit var loader: LoadingDialog
    private lateinit var launchListAdapter: LaunchListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loader = LoadingDialog(requireContext())

        registerViewModel()

        launchListViewModel.getAllLaunches()

        launchListViewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                println("Loading --> $it")
                if (it) {
                    loader.show()
                }else{
                    loader.cancel()
                }
            }
        }

        launchListViewModel.launches.observe(viewLifecycleOwner) {
            println("Launch list alive")
            it?.let {

                if (it.isNotEmpty()) {
                    println("Launch list fragment observe data not empty")
                    loader.cancel()
                    launchListAdapter.updateLaunchList(it)
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }

        val binding = DataBindingUtil.inflate<FragmentLaunchListBinding>(
            inflater,
            R.layout.fragment_launch_list,
            container,
            false,
        )
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(view.context, "List fragment created", Toast.LENGTH_SHORT).show()

        launchListAdapter = LaunchListAdapter(arrayListOf(), this)

        setupRecyclerView()

        swipeRefresh.setOnRefreshListener {
            launchListViewModel.getAllLaunches()

            swipeRefresh.isRefreshing = false
            observeData()
        }

        observeData()

    }


    private fun registerViewModel() {
        launchListViewModel = ViewModelProvider(this)[LaunchListViewModel::class.java]
    }

    private fun setupRecyclerView() {

        recyclerView.adapter = launchListAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)


    }

    private fun observeData() {

//        launchListViewModel.error.observe(viewLifecycleOwner) {
//            it?.let {
//                if (!it) {
//                    errorText.visibility = View.GONE
//                    loader.cancel()
//                }
//            }
//        }

//        if (launchListViewModel.launchesList.isNotEmpty()){
//            println("fragment list is not empty")
//            recyclerView.visibility=View.VISIBLE
//            launchListAdapter.updateLaunchList(launchListViewModel.launchesList)
//        }else{
//            println("fragment list is empty")
//            recyclerView.visibility=View.GONE
//        }

//

    }

    override fun launchTapped(view: View, launchId: String) {
        val action = LaunchListFragmentDirections.goDetailFromList(launchIdKey = launchId)
        Navigation.findNavController(view).navigate(action)
    }

    override fun launchLongTapped(view: View, launch: Launch): Boolean {

        val localLaunch = LocalLaunch(
            name = launch.name,
            detail = launch.details,
            largeImage = launch.links.patch.large,
            smallImage = launch.links.patch.small,
            date = launch.dateUnix,
            id = launch.id,
        )
        println("launch ---> $launch")

        val res = launchListViewModel.insertLaunch(localLaunch)
        if (res) Toast.makeText(view.context, "${localLaunch.name} archived", Toast.LENGTH_SHORT)
            .show()



        return true
    }


}