package com.example.ravivaribaazar.activities.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ravivaribaazar.R
import com.example.ravivaribaazar.activities.ui.activities.AddProductActivity
import com.example.ravivaribaazar.activities.ui.activities.DashboardActivity
import com.example.ravivaribaazar.activities.ui.adapters.MyProductsListAdapter
import com.example.ravivaribaazar.databinding.FragmentProductsBinding
import com.example.ravivaribaazar.firestore.FirestoreClass
import com.example.ravivaribaazar.models.Product
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.view.*
import kotlinx.android.synthetic.main.activity_profile_preview.*
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : BaseFragment() {

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

        binding.fabProductsFragment.setOnClickListener{
            startActivity(Intent(requireContext(),AddProductActivity::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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

    // get products from firestore
    private fun getProductListFromFirestore()
    {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getProductList(this)
    }

    override fun onResume() {
        super.onResume()
        getProductListFromFirestore()
    }

    // indicates we got our products from the firestore
    fun successProductsListFromFirestore(productsList:ArrayList<Product>)
    {
        hideProgressDialog()
        if(productsList.size>0)
        {
            rv_my_products_items.visibility = View.VISIBLE
            no_products_found.visibility = View.GONE

            rv_my_products_items.layoutManager = LinearLayoutManager(activity)
            rv_my_products_items.setHasFixedSize(true)
            val productsAdapter = MyProductsListAdapter(requireActivity(),productsList,this)
            rv_my_products_items.adapter = productsAdapter
        }
        else
        {
            rv_my_products_items.visibility = View.GONE
            no_products_found.visibility = View.VISIBLE
        }
    }

    private fun showAlertDialogToDeleteProduct(productID: String)
    {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Delete")
        builder.setMessage("Are you sure you want to delete the product?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().deleteProduct(this,productID)
            dialogInterface.dismiss()
        }

        builder.setNegativeButton("No"){dialogInterface,_ ->
            dialogInterface.dismiss()
        }

        val alertDialog:AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun deleteProduct(productID:String)
    {
//        Toast.makeText(requireContext(), "You can delete the product. $productID", Toast.LENGTH_SHORT).show()
        showAlertDialogToDeleteProduct(productID)
    }

    fun productDeleteSuccess()
    {
        hideProgressDialog()
        Toast.makeText(requireActivity(),"Your product was deleted successfully", Toast.LENGTH_SHORT).show()
        getProductListFromFirestore()
    }
}