package com.k5.omsetku.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.k5.omsetku.R
import com.k5.omsetku.fragment.loadfragment.LoadFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBacktoProduct: LinearLayout = view.findViewById(R.id.btn_back_to_product)
        btnBacktoProduct.setOnClickListener {
            LoadFragment.loadChildFragment(parentFragmentManager, R.id.host_fragment,
                ProductFragment())
        }

        val items = listOf("Item 1", "Item 2", "Item 3")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
        val dropdownCategory: AutoCompleteTextView = view.findViewById(R.id.dropdown_category)
        dropdownCategory.setAdapter(adapter)

        val btnSave = view.findViewById<MaterialButton>(R.id.btn_save)
        btnSave.setOnClickListener {
            Toast.makeText(requireContext(), "Anda memilih: ${dropdownCategory.text}", Toast.LENGTH_SHORT).show()
        }

//        dropdownCategory.setOnItemClickListener { parent, view, position, id ->
//            val selectedItem = parent.getItemAtPosition(position).toString()
//            Toast.makeText(requireContext(), "Anda memilih: $selectedItem", Toast.LENGTH_SHORT).show()
//
//        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}