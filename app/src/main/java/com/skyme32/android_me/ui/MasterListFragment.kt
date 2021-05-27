package com.skyme32.android_me.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.skyme32.android_me.R
import com.skyme32.android_me.data.AndroidImageAssets


class MasterListFragment: Fragment() {

    lateinit var mCallback: OnImageClickListener


    override fun onAttach(context: Context) {
        super.onAttach(context)

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        mCallback = try {
            context as OnImageClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OnImageClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_master_list, container, false)

        // Get a reference to the GridView in the fragment_master_list xml layout file
        val gridView = rootView.findViewById<View>(R.id.images_grid_view) as GridView

        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the image resources to display
        val mAdapter = MasterListAdapter(context, AndroidImageAssets().getAll())

        // Set the adapter on the GridView
        gridView.adapter = mAdapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            mCallback.onImageSelected(position)
        }

        // Return the root view
        return rootView
    }

    interface OnImageClickListener {
        fun onImageSelected(position: Int)
    }
}