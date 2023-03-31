package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "CrimeListFragment"



class CrimeListFragment : Fragment() {
    // View models are destroyed when fragments are destroyed (not when rotating tho)
    private val crimeListViewModel : CrimeListViewModel by viewModels()
    private var job: Job? = null
    private var _binding: FragmentCrimeListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        /*recycler views only create as many views needed to display on the screen and then just
        reuses the same view objects as you scroll down the screen. OnBindViewHolder happens VERY often*/
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    //ONLY UPDATE UI WHEN IT IS VISIBLE ON SCREEN [started and higher!!!]
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*repeatOnLifecycle is a suspending function that starts your code upon entering a lifecycle
        * and permanently suspends it when you go below it in the lifecycle chain (onStop).*/
        viewLifecycleOwner.lifecycleScope.launch {
            //note how this code will continue to run into resumed if it must
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val crimes = crimeListViewModel.loadCrimes()
                binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        /* when fragments are no longer in view, we should destroy the view. But
        * if often a fragment is still in memory but it's no longer in view. GC can't
        * collect view if the fragment still has a reference to it. */
        _binding = null
    }
}