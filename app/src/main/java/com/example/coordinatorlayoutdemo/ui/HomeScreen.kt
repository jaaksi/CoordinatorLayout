package com.example.coordinatorlayoutdemo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coordinatorlayoutdemo.data.MenuDataProvider
import com.example.coordinatorlayoutdemo.ui.theme.AppColors

@Composable
fun HomeScreen(onNavigateTo: (route: String) -> Unit) {
    DisposableEffect(Unit) {

        onDispose {
            println("HomeScreen onDispose")
        }
    }
    Column(Modifier.fillMaxSize()) {
        Column(Modifier.padding(40.dp)) {
            Text(
                text = "Compose CoordinatorLayout",
                color = AppColors.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Compose实现的CoordinatorLayout的Lite版本，目前支持折叠（仅支持AppBarLayout的scroll效果）、吸顶。支持在可折叠区域快速滑动，完全折叠后，底部内容响应滑动。还支持设置在吸顶时，设置不可折叠的高度，控制吸顶的位置。",
                color = AppColors.Color_9,
                fontSize = 14.sp
            )
        }

        Column(modifier = Modifier.padding(horizontal = 15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            MenuDataProvider.menuData.forEach {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxWidth()
                        .heightIn(min = 50.dp)
                        .shadow(8.dp)
                        .background(AppColors.White)
                        .clickable {
                            onNavigateTo(it.route)
                        }
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = it.label,
                        color = AppColors.Black,
                        fontSize = 18.sp,
                    )

                }
            }
        }

    }
}