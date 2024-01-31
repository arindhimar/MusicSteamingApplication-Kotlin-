package com.example.musicplayer


import HomeFrag
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class navAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragsList = listOf(HomeFrag(), YTFrag(), PlaylistFrag())

    override fun getItemCount(): Int = fragsList.size

    override fun createFragment(position: Int): Fragment = fragsList[position]
}
