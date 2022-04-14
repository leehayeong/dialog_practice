package com.example.dialogpractice

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *  DialogModel.kt
 *
 *  Created by Hayeong Lee on 2022/04/12
 *  Copyright © 2022 Shinhan Bank. All rights reserved.
 */

data class DialogModel(
    var title: String? = null,
    var message: String? = null,
    var positiveButton: CustomDialog.Listener? = null,
    var negativeButton: CustomDialog.Listener? = null,
)
