package com.picpay.contact.presentation.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.picpay.core.di.factory.ViewModelFactory
import com.picpay.contact.R
import com.picpay.contact.databinding.FragmentContactListBinding
import com.picpay.contact.presentation.ui.adapter.ContactListAdapter
import com.picpay.contact.presentation.viewmodel.ContactListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContactListFragment @Inject constructor(): Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ContactListViewModel by viewModels { viewModelFactory }

    private val adapter = ContactListAdapter()

    lateinit var binding: FragmentContactListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_contact_list,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()

        viewModel.fetchContacts(false)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.list.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

}