package com.project.gosdaq.data.enum

sealed class Region {
    data class KR(val countryName: String = "KR"): Region()
    data class US(val countryName: String = "US"): Region()
}