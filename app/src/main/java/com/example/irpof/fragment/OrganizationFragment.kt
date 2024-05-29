package com.example.irpof.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.irpof.DataClass.PdfDetail
import com.example.irpof.API.RetrofitInstance
import com.example.irpof.adapter.PageAdapter
import com.example.irpof.adapter.PdfAdapter
import com.example.irpof.databinding.FragmentOrganizationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrganizationFragment : Fragment(), PageAdapter.OnItemClickListener {
    private var _binding: FragmentOrganizationBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageAdapter: PageAdapter
    private lateinit var pdfAdapter: PdfAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrganizationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getdata();
        pageAdapter.setData(
            mutableListOf(
                "Constitution",
                "Executive Body",
                "Constituent Units",
                "Incumbency Board"
            )
        )
    }

    private fun setupRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        pageAdapter = PageAdapter(this)
        binding.rvOrganization.apply {
            layoutManager = linearLayoutManager
            adapter = pageAdapter
            itemAnimator = null
            setHasFixedSize(true)
        }
    }

    override fun onItemClick(item: String) {
        if (item.equals("Constitution")){
            binding.rcItem.setText(item)
            binding.rlOrg1.visibility= View.VISIBLE
            binding.rlOrg2.visibility= View.GONE
            binding.rlOrg3.visibility= View.GONE
            binding.rlOrg4.visibility= View.GONE
        }
        else if (item.equals("Executive Body")){
            binding.rcItem.setText(item)
            binding.rlOrg1.visibility= View.GONE
            binding.rlOrg2.visibility= View.VISIBLE
            binding.rlOrg3.visibility= View.GONE
            binding.rlOrg4.visibility= View.GONE

        } else if (item.equals("Constituent Units")){
            binding.rcItem.setText(item)
            binding.rlOrg1.visibility= View.GONE
            binding.rlOrg2.visibility= View.GONE
            binding.rlOrg3.visibility= View.VISIBLE
            binding.rlOrg4.visibility= View.GONE
        } else if (item.equals("Incumbency Board")){
            binding.rcItem.setText(item)
            binding.rlOrg1.visibility= View.GONE
            binding.rlOrg2.visibility= View.GONE
            binding.rlOrg3.visibility= View.GONE
            binding.rlOrg4.visibility= View.VISIBLE

        }
    }
    private val folderpath  = "irpof/ORGANIZATION/CONSTITUTION"
    private fun getdata(){
        RetrofitInstance.apiInterface.getPdf(folderpath).enqueue(object : Callback<PdfDetail?> {
            override fun onResponse(call: Call<PdfDetail?>, response: Response<PdfDetail?>) {
                Log.d("API_RESPONSE", "Success: ${response.body()}")
                if(response.isSuccessful){
                   setAdapter()
                    response.body()?.let { pdfAdapter.setData(it.pdfs) }
                }


            }

            override fun onFailure(call: Call<PdfDetail?>, t: Throwable) {
                Log.d("API_RESPONSE", "Failure: ${t.message}")
            }
        })
    }

    fun setAdapter(){
        val linearLayoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        pdfAdapter = PdfAdapter(requireActivity())
        binding.rcPdf.apply {
            layoutManager = linearLayoutManager
            adapter = pdfAdapter
            itemAnimator = null
            setHasFixedSize(true)
        }
    }
}