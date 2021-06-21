package com.example.conversion.convertir

import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.conversion.R
import com.example.conversion.database.MonedaDataBase
import com.example.conversion.databinding.FragmentCurrencyBinding


class CurrencyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCurrencyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container,false)

        val application= requireNotNull(this.activity).application
        val dataSource= MonedaDataBase.getInstance(application).monedaDatabaseDao

        val viewModelFactory: CurrencyViewModelFactory= CurrencyViewModelFactory(dataSource, application)
        val viewModel: CurrencyViewModel= ViewModelProvider(this, viewModelFactory).get(CurrencyViewModel::class.java)

        binding.viewModel=viewModel
        binding.lifecycleOwner=this

        //menu agregar Moneda
        setHasOptionsMenu(true)


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }
}