package com.sundayndu.movieappentry.utils.extensions

import android.view.View

val View.makeVisible get() = run { visibility = View.VISIBLE }
val View.makeInVisible get() = run { visibility = View.GONE }