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
import com.example.irpof.databinding.FragmentPanelBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PanelFragment : Fragment() , PageAdapter.OnItemClickListener{
    private var _binding: FragmentPanelBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageAdapter: PageAdapter
    private lateinit var pdfAdapter: PdfAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPanelBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getdata()
        pageAdapter.setData(
            mutableListOf(
                "DITS",
                "Panel > Group A",
                "Panel > JAG",
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

    private val folderpath  = "irpof/ORGANIZATION/CONSTITUTION"
    private fun getdata(){
        RetrofitInstance.apiInterface.getPdf(folderpath).enqueue(object : Callback<PdfDetail?> {
            override fun onResponse(call: Call<PdfDetail?>, response: Response<PdfDetail?>) {
                Log.d("API_RESPONSE", "Success: ${response.body()}")
                if(response.isSuccessful){
                    val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    pdfAdapter = PdfAdapter(requireContext())
                    binding.rcPdf.apply {
                        layoutManager = linearLayoutManager
                        adapter = pdfAdapter
                        itemAnimator = null
                        setHasFixedSize(true)
                    }
                    response.body()?.let { pdfAdapter.setData(it.pdfs) }
                }


            }

            override fun onFailure(call: Call<PdfDetail?>, t: Throwable) {
                Log.d("API_RESPONSE", "Failure: ${t.message}")
            }
        })
    }


    override fun onItemClick(item: String) {
        Log.d( "onItemClick: ","hi")
    }
}