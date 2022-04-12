package com.example.dialogpractice

import android.view.View
import androidx.databinding.BindingAdapter

/**
 *  CommonViewBindingAdapters.kt
 *
 *  Created by Hayeong Lee on 2022/04/12
 *  Copyright © 2022 Shinhan Bank. All rights reserved.
 */

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(flag: Boolean) {
    if (flag) setVisible()
    else setGone()
}
