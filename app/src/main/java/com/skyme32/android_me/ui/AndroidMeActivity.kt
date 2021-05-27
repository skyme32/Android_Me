package com.skyme32.android_me.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.skyme32.android_me.R
import com.skyme32.android_me.data.AndroidImageAssets

class AndroidMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_me)

        // Only create new fragments when there is no previously saved state
        if(savedInstanceState == null) {
            val fragmentManager: FragmentManager = supportFragmentManager

            val headFragment: BodyPartFragment = BodyPartFragment()
            headFragment.nImageIds = AndroidImageAssets().getHeads()
            headFragment.nListIndex = intent.getIntExtra("headIndex", 0)
            fragmentManager.beginTransaction()
                .add(R.id.head_container, headFragment)
                .commit()

            val bodyFragment: BodyPartFragment = BodyPartFragment()
            bodyFragment.nImageIds = AndroidImageAssets().getBodies()
            bodyFragment.nListIndex = intent.getIntExtra("bodyIndex", 0)
            fragmentManager.beginTransaction()
                .add(R.id.body_container, bodyFragment)
                .commit()

            val legFragment: BodyPartFragment = BodyPartFragment()
            legFragment.nImageIds = AndroidImageAssets().getLegs()
            legFragment.nListIndex = intent.getIntExtra("legIndex", 0)
            fragmentManager.beginTransaction()
                .add(R.id.leg_container, legFragment)
                .commit()
        }
    }
}