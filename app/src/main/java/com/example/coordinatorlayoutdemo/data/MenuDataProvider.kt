package com.example.coordinatorlayoutdemo.data

object MenuDataProvider {
    val menuData = listOf(
        MenuItem("collapsable + LazyColumn", "route1"),
        MenuItem("collapsable + pin + LazyColumn", "route2"),
        MenuItem("PinContent吸附在沉浸式TitleBar下方", "route3"),
        MenuItem("collapsableContent底部的内容吸顶", "route6"),
        MenuItem("collapsable + pin + 锚点联动", "route4"),
        MenuItem("collapsable + pin + horizontal pager", "route5"),
    )
}