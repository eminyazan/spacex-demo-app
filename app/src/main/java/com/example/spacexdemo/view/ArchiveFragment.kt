package com.example.spacexdemo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexdemo.R
import com.example.spacexdemo.adapter.LocalLaunchAdapter
import com.example.spacexdemo.constans.LOCAL_LAUNCH_KEY
import com.example.spacexdemo.model.LocalLaunch
import com.example.spacexdemo.viewmodel.ArchiveViewModel
import kotlinx.android.synthetic.main.fragment_archived.*


class ArchiveFragment : Fragment() {

    private val adapter = LocalLaunchAdapter(arrayListOf())
    private lateinit var viewModel: ArchiveViewModel
    private lateinit var loader: LoadingDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loader = LoadingDialog(requireContext())

        registerViewModel()

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
        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    loader.show()
                }
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                if (!it) {
                    loader.cancel()
                }
            }
        }
        viewModel.launchesList.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    loader.cancel()
                    localRecyclerView.visibility = View.VISIBLE
                    emptyArchiveText.visibility = View.GONE
                    adapter.updateCookList(it)
                } else {
                    emptyArchiveText.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun setupRecycler() {
        localRecyclerView.adapter = adapter
        localRecyclerView.layoutManager = LinearLayoutManager(context)

    }

    private fun registerViewModel() {
        viewModel = ViewModelProvider(this)[ArchiveViewModel::class.java]
        viewModel.getAllLaunchesFromLocal()
    }


}