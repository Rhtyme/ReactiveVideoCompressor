package com.rhtyme.reactivevideocompressor.view.videolist.gallery

import android.os.Bundle
import com.rhtyme.reactivevideocompressor.view.base.BaseFragment

class GalleryFragment: BaseFragment() {

    companion object {

        fun getInstance(bundle: Bundle?): GalleryFragment {
            val fragment =  GalleryFragment()
            bundle?.let {
                fragment.arguments = it
            }
            return fragment
        }
    }
}