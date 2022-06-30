package com.example.ravivaribaazar.activities.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.activities.ui.activities.AddProductActivity
import com.example.ravivaribaazar.activities.ui.activities.DashboardActivity
import com.example.ravivaribaazar.databinding.FragmentProductsBinding
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.view.*
import kotlinx.android.synthetic.main.activity_profile_preview.*
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textProducts
        textView.text = "This is products Fragment"

        binding.fabProductsFragment.setOnClickListener{
            startActivity(Intent(requireContext(),AddProductActivity::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // TODO: Set Floating Action Button

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id)
        {
            R.id.action_add_product ->{
                startActivity(Intent(activity, AddProductActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}