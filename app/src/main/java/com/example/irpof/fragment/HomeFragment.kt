package com.example.irpof.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.irpof.adapter.ImageViewAdapter
import com.example.irpof.adapter.PageAdapter
import com.example.irpof.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), PageAdapter.OnItemClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var imageViewAdapter: ImageViewAdapter? = null
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    private val delay: Long = 3000 // 3 seconds delay
    private lateinit var pageAdapter: PageAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val urlList = listOf(
            "https://irpof.vercel.app/homeimage1.jpeg",
            "https://irpof.vercel.app/homeimage2.jpeg",
            "https://irpof.vercel.app/homeimage3.jpeg"
        )

        imageViewAdapter = ImageViewAdapter(requireActivity(), urlList)
        val viewPager = binding.imgViewPager
        viewPager.adapter = imageViewAdapter
        binding.markMissionDotsIndicator.setViewPager(viewPager)
        binding.headline.isSelected = true

        startAutoScroll()
        val stringList = mutableListOf("Who we are: IRPOF", "Mission & Vision", "Recent Events", "Images", "Videos")
        pageAdapter.setData(stringList)
    }

    private fun setupRecyclerView() {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        pageAdapter = PageAdapter(this)
        binding.rvPages.apply {
            layoutManager = linearLayoutManager
            adapter = pageAdapter
            itemAnimator = null
            setHasFixedSize(true)
        }
    }

    override fun onItemClick(item: String) {
        if (item.equals("Who we are: IRPOF")){
            Log.d( "tanay: ","tanay1")
            binding.rl1.visibility= View.VISIBLE
            binding.rl2.visibility= View.GONE
            binding.rl3.visibility= View.GONE
            binding.rl4.visibility= View.GONE
            binding.rl5.visibility= View.GONE
        }
        else if (item.equals("Mission & Vision")){
            binding.rl1.visibility= View.GONE
            binding.rl2.visibility= View.VISIBLE
            binding.rl3.visibility= View.GONE
            binding.rl4.visibility= View.GONE
            binding.rl5.visibility= View.GONE

        } else if (item.equals("Recent Events")){
            binding.rl1.visibility= View.GONE
            binding.rl2.visibility= View.GONE
            binding.rl3.visibility= View.VISIBLE
            binding.rl4.visibility= View.GONE
            binding.rl5.visibility= View.GONE
        } else if (item.equals("Images")){
            binding.rl1.visibility= View.GONE
            binding.rl2.visibility= View.GONE
            binding.rl3.visibility= View.GONE
            binding.rl4.visibility= View.VISIBLE
            binding.rl5.visibility= View.GONE

        } else if (item.equals("Videos")){
            binding.rl1.visibility= View.GONE
            binding.rl2.visibility= View.GONE
            binding.rl3.visibility= View.GONE
            binding.rl4.visibility= View.GONE
            binding.rl5.visibility= View.VISIBLE
        }
    }

    private val runnable = object : Runnable {
        override fun run() {
            if (imageViewAdapter?.count == 0) return

            currentPage = (currentPage + 1) % (imageViewAdapter?.count ?: 0)
            binding.imgViewPager.setCurrentItem(currentPage, true)
            handler.postDelayed(this, delay)
        }
    }

    private fun startAutoScroll() {
        handler.postDelayed(runnable, delay)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAutoScroll()
    }
}