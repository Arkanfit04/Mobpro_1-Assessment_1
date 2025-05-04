package com.arkan0099.assessment1.navigation

import com.arkan0099.assessment1.ui.screen.KEY_ID_CATATAN

sealed class Screen(val route: String) {
    data object Home: Screen("mainscreen")
    data object About: Screen("aboutscreen")
    data object FormBaru: Screen("detailscreen")
    data object FormUbah: Screen("detailscreen/{$KEY_ID_CATATAN}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}