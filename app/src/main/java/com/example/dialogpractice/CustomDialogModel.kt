package com.example.dialogpractice

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *  DialogModel.kt
 *
 *  Created by Hayeong Lee on 2022/04/12
 *  Copyright © 2022 Shinhan Bank. All rights reserved.
 */

@Parcelize
data class CustomDialogModel(
    var message: String = "",

    var hasPositiveBtn: Boolean = false,
    var positiveBtnText: String = "",
    var positiveBtnClick: CustomDialog.ClickListener? = null,

    var hasNegativeBtn: Boolean = false,
    var negativeBtnText: String = "",
    var negativeBtnClick: CustomDialog.ClickListener? = null,

) : Parcelable
