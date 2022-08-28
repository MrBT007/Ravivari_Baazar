package com.example.ravivaribaazar.activities.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.activities.ui.activities.DashboardActivity
import com.example.ravivaribaazar.activities.ui.adapters.DashboardItemsListAdapter
import com.example.ravivaribaazar.activities.ui.adapters.MyProductsListAdapter
import com.example.ravivaribaazar.databinding.FragmentDashboardBinding
import com.example.ravivaribaazar.firestore.FirestoreClass
import com.example.ravivaribaazar.models.Product
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_products.*


class DashboardFragment : BaseFragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.noDashboardItemsFound
        textView.text = "No Items Found Yet!!"
        return root
    }

    override fun onResume() {
        super.onResume()
        getDashboardItemsList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun successDashboardItemsList(dashboardItemsList:ArrayList<Product>)
    {
        hideProgressDialog()
        if(dashboardItemsList.size>0)
        {
            rv_dashboard_items.visibility = View.VISIBLE
            no_dashboard_items_found.visibility = View.GONE

            rv_dashboard_items.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
            rv_dashboard_items.setHasFixedSize(true)
            val dashboardAdapter = DashboardItemsListAdapter(requireActivity(),dashboardItemsList)
            rv_dashboard_items.adapter = dashboardAdapter
        }
        else
        {
            rv_my_products_items.visibility = View.GONE
            no_products_found.visibility = View.VISIBLE
        }
    }

    fun getDashboardItemsList()
    {
        showProgressDialog(resources.getString((R.string.please_wait)))
        FirestoreClass().getDashboardItemsList(this)
    }
}