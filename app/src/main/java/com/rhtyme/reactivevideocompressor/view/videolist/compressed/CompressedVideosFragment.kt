package com.rhtyme.reactivevideocompressor.view.videolist.compressed

import android.os.Bundle
import com.rhtyme.reactivevideocompressor.view.base.BaseFragment

class CompressedVideosFragment: BaseFragment() {

    companion object {

        fun getInstance(bundle: Bundle?): CompressedVideosFragment {
            val fragment =  CompressedVideosFragment()
            bundle?.let {
                fragment.arguments = it
            }
            return fragment
        }
    }

}