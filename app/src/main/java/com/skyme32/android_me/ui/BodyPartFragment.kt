package com.skyme32.android_me.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.skyme32.android_me.R


class BodyPartFragment : Fragment() {

    lateinit var nImageIds: ArrayList<Int?>
    var nListIndex: Int = 0

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Load the saved state (the list of images and list index) if there is one
        if(savedInstanceState != null) {
            nImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST) as ArrayList<Int?>
            nListIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        val rootView: View = inflater.inflate(R.layout.fragment_body_part, container, false)
        val imageView : ImageView = rootView.findViewById(R.id.body_part_image_view)

        imageView.setImageResource(nImageIds[nListIndex]!!)
        imageView.setOnClickListener { // Increment position as long as the index remains <= the size of the image ids list
            if (nListIndex < nImageIds.size - 1) {
                nListIndex++
            } else {
                // The end of list has been reached, so return to beginning index
                nListIndex = 0
            }
            // Set the image resource to the new list item
            imageView.setImageResource(nImageIds[nListIndex]!!)
        }


        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntegerArrayList(IMAGE_ID_LIST, nImageIds)
        outState.putInt(LIST_INDEX, nListIndex);
    }

    // Setter methods for keeping track of the list images this fragment can display and which image
    // in the list is currently being displayed
    fun setImageIds(imageIds: ArrayList<Int?>) {
        nImageIds = imageIds
    }

    fun setListIndex(index: Int) {
        nListIndex = index
    }
}

// Final Strings to store state information about the list of images and list index
const val IMAGE_ID_LIST = "image_ids"
const val LIST_INDEX = "list_index"