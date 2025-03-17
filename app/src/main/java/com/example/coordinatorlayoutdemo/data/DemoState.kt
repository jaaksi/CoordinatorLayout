package com.example.coordinatorlayoutdemo.data

import com.example.coordinatorlayoutdemo.widget.ITab

data class DemoState(
    // 更新tab的时候，要赋值是否是来自tab点击z
    val selectedTab: Int = 0,
    val fromTabClick: Boolean = false,
    val list: List<String> = listOf("FullBody", "Face", "Arm", "Chest", "Butt", "Leg"),
    val tabList: List<ITab> = list.map {
        object : ITab {
            override fun getTabName() = it
        }
    }
)