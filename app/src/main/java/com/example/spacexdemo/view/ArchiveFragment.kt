package com.example.spacexdemo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexdemo.R
import com.example.spacexdemo.adapter.LocalLaunchAdapter
import com.example.spacexdemo.viewmodel.ArchiveViewModel
import kotlinx.android.synthetic.main.fragment_archived.*


class ArchiveFragment : Fragment() {

    private lateinit var adapter: LocalLaunchAdapter
    private lateinit var viewModel: ArchiveViewModel
    private lateinit var loader: LoadingDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loader = LoadingDialog(requireContext())

        registerViewModel()
        adapter = LocalLaunchAdapter(arrayListOf(), viewModel)

        viewModel.getAllLaunchesFromLocal()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_archived, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        observeData()

    }


    private fun observeData() {
//        viewModel.loading.observe(viewLifecycleOwner) {
//            it?.let {
//                if (it) {
//                    loader.show()
//                }
//            }
//        }
//        viewModel.error.observe(viewLifecycleOwner) {
//            it?.let {
//                if (!it) {
//                    loader.cancel()
//                }
//            }
//        }

        // TODO: when go back, list page or archive page is not visible when update changes it looks fine
        viewModel.localLaunchesList.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    println("Archive fragment local is not empty")
                    loader.cancel()
                    localRecyclerView.visibility = View.VISIBLE
                    emptyArchiveText.visibility = View.GONE
                    adapter.updateLocalLaunchList(it)
                } else {
                    println("Archive fragment local is empty")
                    localRecyclerView.visibility = View.GONE
                    emptyArchiveText.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun setupRecycler() {
        adapter = LocalLaunchAdapter(arrayListOf(), viewModel = viewModel)
        localRecyclerView.adapter = adapter
        localRecyclerView.layoutManager = LinearLayoutManager(context)

    }

    private fun registerViewModel() {
        viewModel = ViewModelProvider(this)[ArchiveViewModel::class.java]
        viewModel.getAllLaunchesFromLocal()
    }


}