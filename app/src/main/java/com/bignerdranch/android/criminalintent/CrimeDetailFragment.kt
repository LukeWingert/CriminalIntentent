package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeDetailBinding
import java.util.*

class CrimeDetailFragment : Fragment() {
    private lateinit var crime: Crime
    // "?" means that the property is nullable, the property below _binding just throws an exception if null
    private var _binding: FragmentCrimeDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        crime = Crime(
            id = UUID.randomUUID(),
            title = "",
            date = Date(),
            isSolved = false
        )
    }

    //You should only inflate views here
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //When you do apply, the context of "this" becomes what you called it on
        binding.apply {
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                //text is a char sequence so you need to use toString(). In lambda "_" means you don't need param
                //copy means "copy properties into a new instance and change these properies I've listed"
                crime = crime.copy(title = text.toString())
            }

            crimeDate.apply {
                text = crime.date.toString()
                //Button is WIP so keep it disables
                isEnabled = false
            }

            crimeSolved.setOnCheckedChangeListener{_, isChecked ->
                crime = crime.copy(isSolved = isChecked)
            }
        }
    }

    //when you navigate away from one fragment and go to another, the previous fragment's
    //variables are kept in memory. Normally the view is destroyed, but we have an instance of it in
    //memory. We reinitalize it in onCreateView so it's up to date. THere's no benefit to keeping the view
    //Make the binding null so GC can collect it.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}