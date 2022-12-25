package com.android.binapp.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.binapp.R
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : Fragment(), Listener {

    private val viewModel by viewModel<MainViewModel>()
    lateinit var adapter: AdapterHistory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.menu_del, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_delete -> {
                        deleteAlertDialog()

                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.allRecords.observe(viewLifecycleOwner) { list ->
            adapter = AdapterHistory(requireActivity(), list, this)
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            layoutManager.reverseLayout = true
            recycler.layoutManager = layoutManager
            recycler.adapter = adapter
        }
    }

    private fun deleteAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Подтверждение удаления")

        builder.setMessage("Вы действительно хотите удлаить все записи?")

        builder.setPositiveButton(
            "Да"
        ) { _, _ ->
            viewModel.clear()
        }


        builder.setNegativeButton(
            "Нет"
        ) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onItemClick(position: Int) {
        val item = adapter.getItem(position)

        var bundle = bundleOf("id" to item.id)
        requireView().findNavController()
            .navigate(R.id.action_historyFragment_to_infoFragment, bundle)
    }

}