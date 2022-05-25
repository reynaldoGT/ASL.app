package com.neo.signLanguage.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neo.signLanguage.ui.view.fragments.GiphyFragment
import com.neo.signLanguage.ui.view.fragments.BottomNavigationFragment

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return BottomNavigationFragment()
            1 -> return GiphyFragment()
            /*2 -> return HistoryFragment()*/

        }
        return BottomNavigationFragment()
    }

}