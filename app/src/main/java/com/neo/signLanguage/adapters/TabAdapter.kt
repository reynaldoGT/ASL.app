package com.neo.signLanguage.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neo.signLanguage.ui.view.fragments.GiphyFragment
import com.neo.signLanguage.ui.view.fragments.BottomNavigationFragment
import com.neo.signLanguage.ui.view.fragments.LetterAndNumbersFragment
import com.neo.signLanguage.ui.view.fragments.SendMessageFragment

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SendMessageFragment()
            1 -> return LetterAndNumbersFragment()
            2 -> return GiphyFragment()
            /*2 -> return HistoryFragment()*/

        }
        return SendMessageFragment()
    }

}