package com.k5.omsetku.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object LoadFragment {
    fun loadMainFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun loadChildFragment(childFragmentManager: FragmentManager, containerId: Int, childFragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction
            .replace(containerId, childFragment)
            .addToBackStack(null)
            .commit()
    }
}