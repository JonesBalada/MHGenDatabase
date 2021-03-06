package com.ghstudios.android.features.armorsetbuilder.armorselect

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ghstudios.android.GenericActivity
import com.ghstudios.android.MenuSection
import com.ghstudios.android.features.armorsetbuilder.detail.ASBPagerActivity
import com.ghstudios.android.mhgendatabase.R

class ArmorSelectActivity : GenericActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ArmorSelectViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.activity_asb_armor_picker)

        val asbPieceIndex = intent.getIntExtra(ASBPagerActivity.EXTRA_PIECE_INDEX, -1)
        val rank = intent.getIntExtra(ASBPagerActivity.EXTRA_SET_RANK, -1)
        val hunterType = intent.getIntExtra(ASBPagerActivity.EXTRA_SET_HUNTER_TYPE, -1)

        viewModel.initialize(asbPieceIndex, hunterType)
    }

    override fun createFragment(): Fragment {
        return ArmorSelectAllFragment()
    }

    override fun getSelectedSection() = MenuSection.ARMOR_SET_BUILDER

}