package com.example.conversion.convertir


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
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

        val manager= GridLayoutManager(activity,3)
        binding.listMonedas.layoutManager=manager
        val adapter= CurrencyAdapter(MonedaListener {id->
            viewModel.onMonedaClicked(id)
        })
        binding.listMonedas.adapter= adapter
        viewModel.allMonedas.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        //aviso no cargo valor
        viewModel.dato.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(context, getString(R.string.fc_convertir), Toast.LENGTH_SHORT).show()
                viewModel.doneDato()
            }
        })
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