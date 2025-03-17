package com.example.coordinatorlayoutdemo.data

object MenuDataProvider {
    val menuData = listOf(
        MenuItem("collapsable + LazyColumn", "route1"),
        MenuItem("collapsable + pin + LazyColumn", "route2"),
        MenuItem("控制吸顶位置", "route3"),
        MenuItem("collapsable + pin + 锚点联动", "route4"),
        MenuItem("collapsable + pin + horizontal pager", "route5"),
    )
}