package com.project.gosdaq.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.gosdaq.dao.InterestingData
import com.project.gosdaq.contract.InterestingContract
import com.project.gosdaq.presenter.InterestingPresenter
import com.project.gosdaq.adaptor.InterestingAdaptor
import com.project.gosdaq.databinding.FragmentFavoriteBinding

class InterestingFragment : Fragment(), InterestingContract.InterestingView {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var mainPresenter: InterestingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        mainPresenter = InterestingPresenter(this@InterestingFragment)

        mainPresenter.loadInterestingData()
        return binding.root
    }

    override fun setInterestingData(interestingDataList: MutableList<InterestingData>) {
        val adapter = InterestingAdaptor(interestingDataList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}