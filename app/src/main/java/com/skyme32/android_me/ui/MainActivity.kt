package com.skyme32.android_me.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.skyme32.android_me.R
import com.skyme32.android_me.data.AndroidImageAssets


class MainActivity : AppCompatActivity(), MasterListFragment.OnImageClickListener {

    private var headIndex: Int = 0
    private var bodyIndex: Int = 0
    private var legIndex: Int = 0
    private var mTwoPane: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Determine if you're creating a two-pane or single-pane display
        if (findViewById<View?>(R.id.android_me_linear_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true

            // Change the GridView to space out the images more on tablet
            val gridView = findViewById<View>(R.id.images_grid_view) as GridView
            gridView.numColumns = 2

            // Getting rid of the "Next" button that appears on phones for launching a separate activity
            val nextButton = findViewById<View>(R.id.next_button) as Button
            nextButton.visibility = View.GONE
            if (savedInstanceState == null) {
                // In two-pane mode, add initial BodyPartFragments to the screen
                val fragmentManager: FragmentManager = supportFragmentManager

                // Creating a new head fragment
                val headFragment = BodyPartFragment()
                headFragment.nImageIds = AndroidImageAssets().getHeads()
                // Add the fragment to its container using a transaction
                fragmentManager.beginTransaction()
                    .add(R.id.head_container, headFragment)
                    .commit()

                // New body fragment
                val bodyFragment = BodyPartFragment()
                bodyFragment.nImageIds = AndroidImageAssets().getBodies()
                fragmentManager.beginTransaction()
                    .add(R.id.body_container, bodyFragment)
                    .commit()

                // New leg fragment
                val legFragment = BodyPartFragment()
                legFragment.nImageIds = AndroidImageAssets().getLegs()
                fragmentManager.beginTransaction()
                    .add(R.id.leg_container, legFragment)
                    .commit()
            }
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false
        }

    }

    override fun onImageSelected(position: Int) {
        val bodyPartNumber = position/12
        val listIndex = position - 12*bodyPartNumber

        if (mTwoPane) {
            // Create two=pane interaction
            val newFragment = BodyPartFragment()

            when (bodyPartNumber) {
                0 -> {
                    newFragment.nImageIds = AndroidImageAssets().getHeads()
                    newFragment.setListIndex(listIndex)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.head_container, newFragment)
                        .commit();
                }
                1 -> {
                    newFragment.nImageIds = AndroidImageAssets().getBodies()
                    newFragment.setListIndex(listIndex)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.body_container, newFragment)
                        .commit();
                }
                2 -> {
                    newFragment.nImageIds = AndroidImageAssets().getLegs()
                    newFragment.setListIndex(listIndex)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.leg_container, newFragment)
                        .commit();
                }
            }
        } else {
            when (bodyPartNumber) {
                0 -> headIndex = listIndex
                1 -> bodyIndex = listIndex
                2 -> legIndex = listIndex
            }

            val bundle = Bundle()
            bundle.putInt("headIndex", headIndex)
            bundle.putInt("bodyIndex", bodyIndex)
            bundle.putInt("legIndex", legIndex)

            val intent: Intent = Intent(this, AndroidMeActivity::class.java)
            intent.putExtras(bundle)

            // The "Next" button launches a new AndroidMeActivity
            val nextButton = findViewById<View>(R.id.next_button) as Button
            nextButton.setOnClickListener { startActivity(intent) }
        }
    }

}