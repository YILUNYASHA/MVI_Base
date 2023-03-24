package com.example.myapplication.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView

abstract class BaseFragment(@LayoutRes containerLayoutId: Int = 0) : Fragment(containerLayoutId),
    MavericksView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
