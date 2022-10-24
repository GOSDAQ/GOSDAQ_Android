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
import com.project.gosdaq.data.interesting.InterestingResponseList
import com.project.gosdaq.databinding.FragmentFavoriteBinding
import com.project.gosdaq.dialog.AddInterestingDialog
import com.project.gosdaq.repository.GosdaqRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InterestingFragment : Fragment(), InterestingContract.InterestingView {

    private val gosdaqRepository: GosdaqRepository by lazy {
        GosdaqRepository.getInstance(requireContext())
    }

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var mainPresenter: InterestingPresenter
    private lateinit var adapter: InterestingAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainPresenter = InterestingPresenter(this@InterestingFragment, gosdaqRepository)
        lifecycleScope.launch(Dispatchers.IO) {
            mainPresenter.setInterestingDataList()
        }
    }

    override fun setShimmerVisibility(visibility: Boolean) {
        when (visibility) {
            true -> {
                binding.favoriteShimmerLayout.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.favoriteShimmerLayout.startShimmer()
            }
            else -> {
                binding.favoriteShimmerLayout.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.favoriteShimmerLayout.stopShimmer()
            }
        }
    }

    override fun initInterestingRecyclerView(interestingResponseData: MutableList<InterestingResponseList>) {
        adapter = InterestingAdaptor(interestingResponseData)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun updateInterestingRecyclerView() {
        adapter.notifyDataSetChanged()
    }

    fun addInterestingItem(){
        AddInterestingDialog(mainPresenter).show(activity?.supportFragmentManager!!, "")
    }
}