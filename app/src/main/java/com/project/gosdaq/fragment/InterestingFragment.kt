package com.project.gosdaq.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.presenter.InterestingPresenter
import com.project.gosdaq.adaptor.InterestingAdaptor
import com.project.gosdaq.dao.InterestingResponse.InterestingResponseInformation
import com.project.gosdaq.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InterestingFragment : Fragment(), InterestingContract.InterestingView {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var mainPresenter: InterestingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        mainPresenter = InterestingPresenter(this@InterestingFragment)

        lifecycleScope.launch(Dispatchers.IO) {
            mainPresenter.initInterestingStockList()
        }

        return binding.root
    }

    override fun setInterestingData(interestingResponseInformation: MutableList<InterestingResponseInformation>) {
        val adapter = InterestingAdaptor(interestingResponseInformation)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.favoriteShimmerLayout.visibility = View.GONE
        binding.favoriteShimmerLayout.stopShimmer()
    }
}