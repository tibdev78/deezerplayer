package com.example.deezer_play.track

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.deezer_play.R
import kotlinx.android.synthetic.main.track_fragment.*

class TrackFragment : Fragment() {

    companion object {
        fun newInstance() = TrackFragment()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.track_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadText()
    }

    fun openFragment(fragment: Fragment) {
        val transaction = fragmentManager?.beginTransaction()
        transaction!!.replace(R.id.header_album, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun loadText() {
        text_fragment.text = "dddd"
    }

}