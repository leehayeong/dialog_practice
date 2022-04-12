package com.example.dialogpractice

import android.view.View

/**
 *  ViewExtensions.kt
 *
 *  Created by Hayeong Lee on 2022/04/12
 *  Copyright © 2022 Shinhan Bank. All rights reserved.
 */

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}
