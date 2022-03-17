package com.example.exam6.adapter

import android.support.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.exam6.fragment.PhysicalFragment

class CardViewPagerAdapter (
    @NonNull fragmentManager: FragmentManager?,
    @NonNull lifecycle: Lifecycle?
    ):
    FragmentStateAdapter(fragmentManager!!, lifecycle!!) {
        @NonNull
        override fun createFragment(position: Int): Fragment {
            return if (position == 1) {
                PhysicalFragment()
            } else PhysicalFragment()
        }

        override fun getItemCount(): Int {
            return 2
        }
}