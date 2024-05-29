package com.example.irpof.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.irpof.adapter.TabLayoutAdapter
import com.example.irpof.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(), TabLayoutMediator.TabConfigurationStrategy {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val titles: ArrayList<String> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabTitles()
        setupViewPager()
        TabLayoutMediator(binding.tabs, binding.imgViewPager, this).attach()
    }


    private fun setupTabTitles() {
        titles.add("Home")
        titles.add("Organization")
        titles.add("DITS/Panel")
        titles.add("Events")
        titles.add("Contact Us")
    }

    private fun setupViewPager() {
        try {
            val tabLayoutAdapter = TabLayoutAdapter(requireActivity())
            val fragmentList: MutableList<Fragment> = ArrayList()
            fragmentList.add(HomeFragment())
            fragmentList.add(OrganizationFragment())
            fragmentList.add(PanelFragment())
            fragmentList.add(EventFragment())
            fragmentList.add(contactFragment())
            tabLayoutAdapter.setData(fragmentList)
            binding.imgViewPager.isUserInputEnabled = false
            binding.imgViewPager.adapter = tabLayoutAdapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
        tab.text = titles[position]
    }


}