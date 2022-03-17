package com.example.exam6.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.exam6.R
import com.example.exam6.activity.AddCardActivity
import com.example.exam6.adapter.CardViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class CardsFragment : Fragment() {

    private var viewPagerAdapter: CardViewPagerAdapter? = null
    private var viewPager2: ViewPager2? = null
    private var tabLayout: TabLayout? = null
    lateinit var iv_add:ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_cards, container, false)

        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        iv_add = view.findViewById(R.id.iv_add)
        iv_add.setOnClickListener {
            callAddCardActivity()
        }

        viewPager2 = view.findViewById(R.id.viewpager2)
        tabLayout = view.findViewById(R.id.tabLayout)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Physical"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Virtual"));
        // Set the adapter
        val fragmentManager: androidx.fragment.app.FragmentManager = requireActivity().getSupportFragmentManager()
        viewPagerAdapter = CardViewPagerAdapter(fragmentManager, lifecycle)
        viewPager2!!.setAdapter(viewPagerAdapter)

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to  set the page viewer
        // we use the setupWithViewPager().
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2!!.setCurrentItem(tab.position)

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager2!!.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout!!.selectTab(tabLayout!!.getTabAt(position))
            }
        })
    }

    private fun callAddCardActivity() {
        var intent = Intent(requireContext(), AddCardActivity::class.java)
        startActivity(intent)
    }
}