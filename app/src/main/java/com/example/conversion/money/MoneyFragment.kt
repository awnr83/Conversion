package com.example.conversion.money

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.conversion.R
import com.example.conversion.database.MonedaDataBase
import com.example.conversion.databinding.FragmentMoneyBinding
import com.google.android.material.snackbar.Snackbar

class MoneyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMoneyBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_money, container, false)

        val application= requireNotNull(this.activity).application
        val dataSource= MonedaDataBase.getInstance(application).monedaDatabaseDao

        val viewModelFactory= MoneyViewModelFactory(dataSource)
        val viewModel: MoneyViewModel=  ViewModelProvider(this, viewModelFactory).get(MoneyViewModel::class.java)

        binding.viewModel=viewModel
        binding.lifecycleOwner=this

        viewModel.alertaDatos.observe(viewLifecycleOwner, Observer {
            if(it){
                Snackbar.make(activity!!.findViewById(android.R.id.content), resources.getString(R.string.msg_faltan_datos),Snackbar.LENGTH_SHORT).show()
                viewModel.doneAlerta()
            }
        })
        viewModel.alertaAgregado.observe(viewLifecycleOwner, Observer {
            if(it){
                Snackbar.make(activity!!.findViewById(android.R.id.content), resources.getString(R.string.msg_Agregado), Snackbar.LENGTH_SHORT).show()
                viewModel.doneAgregado()
            }
        })
        viewModel.alertaModificado.observe(viewLifecycleOwner, Observer {
            if(it){
                Snackbar.make(activity!!.findViewById(android.R.id.content), resources.getString(R.string.msg_modificado), Snackbar.LENGTH_SHORT).show()
                viewModel.doneModificado()
            }
        })
        viewModel.alertaEliminado.observe(viewLifecycleOwner, Observer {
            if(it){
                Snackbar.make(activity!!.findViewById(android.R.id.content), resources.getString(R.string.msg_eliminado), Snackbar.LENGTH_SHORT).show()
                viewModel.doneEliminado()
            }
        })

        return binding.root
    }

}