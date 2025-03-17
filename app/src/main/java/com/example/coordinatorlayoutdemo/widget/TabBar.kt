package com.example.coordinatorlayoutdemo.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coordinatorlayoutdemo.compose.NoRippleInteractionSource
import com.example.coordinatorlayoutdemo.ui.theme.AppColors
import com.example.coordinatorlayoutdemo.widget.MTabRowDefaults.tabIndicatorOffset2

@Stable
interface ITab {
    fun getTabName(): String
}

@Composable
fun TabBar(
    tabList: List<ITab>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    onTabClick: (Int) -> Unit
) {
    Box(modifier = Modifier.padding(vertical = 10.dp)) {
        MScrollableTabRow(
            selectedTabIndex,
            modifier = modifier
                .fillMaxWidth(),
            backgroundColor = Color.Transparent,
            contentColor = Color.Transparent,
            edgePadding = 5.dp,
            indicator = @Composable { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset2(tabPositions[selectedTabIndex])
                        // 居中对齐
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .width(36.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(AppColors.Theme),
                )
            },
            divider = {}
        ) {
            tabList.forEachIndexed { index, item ->
                val selected = index == selectedTabIndex
                Tab(
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            onTabClick(index)
                        }
                    },
                    interactionSource = remember {
                        NoRippleInteractionSource()
                    },
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .padding(bottom = 6.dp)
                ) {
                    Text(
                        text = item.getTabName(),
                        style = TextStyle.Default.copy(
                            color = if (selected) Color(0xFF212121) else Color(
                                0xFF808080
                            ),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }

}