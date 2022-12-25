package com.android.binapp.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.android.binapp.R
import com.android.binapp.domain.models.BinInfoEntity
import kotlinx.android.synthetic.main.fragment_info.*

import kotlinx.android.synthetic.main.fragment_info.view.*

import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : Fragment() {
    var idHistory = 0
    private val viewModel by viewModel<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        idHistory = arguments?.getInt("id") ?: 0

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.menu_del, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_delete -> {
                        viewModel.delete(idHistory)
                        requireView().findNavController().popBackStack()

                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.allRecords.observe(viewLifecycleOwner) { binInfoList ->
            binInfoList.single { it.id == idHistory }.let {
                init(it)
            }
        }

    }


    fun init(binInfoEntity: BinInfoEntity) {
        binInfoEntity?.let {

            cardViewNumber2.tv_number_len2.text = it.number_length.toString()
            cardViewNumber2.tv_number_luhn2.text = if (it.number_luhn) "да" else "нет"

            cardView_2.tv_scheme2.text = it.scheme
            cardView_2.tv_type2.text = it.type
            cardView_2.tv_brand2.text = it.brand
            cardView_2.tv_prepaid2.text = if (it.prepaid) "да" else "нет"


            cardViewCountry_2.tv_country_numeric2.text = it.country_numeric
            cardViewCountry_2.tv_country_alpha2.text = it.country_alpha2
            cardViewCountry_2.tv_country_name2.text = it.country_name
            cardViewCountry_2.tv_country_emoji2.text = it.country_emoji
            cardViewCountry_2.tv_country_currency2.text = it.country_currency
            cardViewCountry_2.tv_country_latitude2.text = it.country_latitude.toString()
            cardViewCountry_2.tv_country_longitude2.text = it.country_longitude.toString()


            cardViewBank2.tv_bank_city2.text = it.bank_city
            cardViewBank2.tv_bank_name2.text = it.bank_name
            cardViewBank2.tv_bank_phone2.text = it.bank_phone
            cardViewBank2.tv_bank_url2.text = it.bank_url

            cardViewBank2.isVisible = true
        }
    }


}