package com.android.binapp.presentation

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.android.binapp.R
import com.android.binapp.data.retrofit.BinResponse
import com.android.binapp.domain.models.BinInfoEntity
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.cardViewBank
import kotlinx.android.synthetic.main.fragment_main.cardViewCountry_
import kotlinx.android.synthetic.main.fragment_main.cardViewNumber
import kotlinx.android.synthetic.main.fragment_main.cardView_
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.fragment_main.view.tv_bank_city
import kotlinx.android.synthetic.main.fragment_main.view.tv_bank_name
import kotlinx.android.synthetic.main.fragment_main.view.tv_bank_phone
import kotlinx.android.synthetic.main.fragment_main.view.tv_bank_url
import kotlinx.android.synthetic.main.fragment_main.view.tv_brand
import kotlinx.android.synthetic.main.fragment_main.view.tv_country_alpha2
import kotlinx.android.synthetic.main.fragment_main.view.tv_country_currency
import kotlinx.android.synthetic.main.fragment_main.view.tv_country_emoji
import kotlinx.android.synthetic.main.fragment_main.view.tv_country_latitude
import kotlinx.android.synthetic.main.fragment_main.view.tv_country_longitude
import kotlinx.android.synthetic.main.fragment_main.view.tv_country_name
import kotlinx.android.synthetic.main.fragment_main.view.tv_country_numeric
import kotlinx.android.synthetic.main.fragment_main.view.tv_number_len
import kotlinx.android.synthetic.main.fragment_main.view.tv_number_luhn
import kotlinx.android.synthetic.main.fragment_main.view.tv_prepaid
import kotlinx.android.synthetic.main.fragment_main.view.tv_scheme
import kotlinx.android.synthetic.main.fragment_main.view.tv_type
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    var flag: Boolean = false
    private val viewModel by viewModel<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cardView.isVisible = false

        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {
                    R.id.menuItem_statistic -> {
                        requireView().findNavController()
                            .navigate(R.id.action_mainFragment_to_historyFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        button.setOnClickListener {
            val input = editTextBin.text.toString().toInt()
            if (input < 1000000) {
                Toast.makeText(requireContext(), "Длинна не меньше 6", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.loadInfoFromAPI(input)
            flag = true
        }


        viewModel.allRecords.observe(viewLifecycleOwner) {
            if (flag) {
                it?.let {
                    if (it.isNotEmpty()) {
                        init(it.last())
                    }
                }
            }


        }

        viewModel.noInfo.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    requireContext(),
                    "По данному номеру отсутствует информация",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        editTextBin.addTextChangedListener {
            if (it.toString().length in (6..8)) {
                button.isEnabled = true
            } else {
                button.isEnabled = false
                cardView.isVisible = false
            }
        }


    }

    fun init(binInfoEntity: BinInfoEntity?) {
        binInfoEntity?.let {

            cardViewNumber.tv_number_len.text = it.number_length.toString()
            cardViewNumber.tv_number_luhn.text = if (it.number_luhn) "да" else "нет"

            cardView_.tv_scheme.text = it.scheme
            cardView_.tv_type.text = it.type
            cardView_.tv_brand.text = it.brand
            cardView_.tv_prepaid.text = if (it.prepaid) "да" else "нет"


            cardViewCountry_.tv_country_numeric.text = it.country_numeric
            cardViewCountry_.tv_country_alpha2.text = it.country_alpha2
            cardViewCountry_.tv_country_name.text = it.country_name
            cardViewCountry_.tv_country_emoji.text = it.country_emoji
            cardViewCountry_.tv_country_currency.text = it.country_currency
            cardViewCountry_.tv_country_latitude.text = it.country_latitude.toString()
            cardViewCountry_.tv_country_longitude.text = it.country_longitude.toString()


            cardViewBank.tv_bank_city.text = it.bank_city
            cardViewBank.tv_bank_name.text = it.bank_name
            cardViewBank.tv_bank_phone.text = it.bank_phone
            cardViewBank.tv_bank_url.text = it.bank_url

            cardViewBank.isVisible = true


            cardView.isVisible = true
        }
    }


}