package com.example.coordinatorlayoutdemo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coordinatorlayoutdemo.data.DemoState
import com.example.coordinatorlayoutdemo.ui.theme.AppColors
import com.example.coordinatorlayoutdemo.widget.TabBar
import com.example.coordinatorlayoutdemo.widget.TopCard
import kotlinx.coroutines.launch
import org.jaaksi.coordinatorlayout.CoordinatorLayout
import org.jaaksi.coordinatorlayout.rememberCoordinatorState


val titleStyle = TextStyle.Default.copy(
    color = AppColors.Black,
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold
)

@Composable
fun SimpleScreen4() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {

        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Home",
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
        }

        HorizontalDivider(color = Color(0XFFE5E5E5))

        var uiState by remember { mutableStateOf(DemoState()) }
        val coroutineScope = rememberCoroutineScope()

        val lazyListState = rememberLazyListState()
        val coordinatorState = rememberCoordinatorState()
        CoordinatorLayout(
            nestedScrollableState = { lazyListState },
            state = coordinatorState,
            modifier = Modifier.fillMaxSize(),
            collapsableContent = {
                TopCard()
            },
            pinContent = {
                val titleHeight = with(LocalDensity.current) { 40.dp.roundToPx() }
                HomeTabBar(uiState) {
                    uiState = uiState.copy(selectedTab = it, fromTabClick = true)
                    coroutineScope.launch {
                        coordinatorState.animateToCollapsed()
                        lazyListState.animateScrollToItem(
                            index = it,
                            scrollOffset = if (it == 0) 0 else titleHeight
                        )
                    }
                }
            },
        ) {
            BottomList(
                uiStateGetter = { uiState },
                lazyListState = lazyListState,
            ) {
                uiState = uiState.copy(selectedTab = it, fromTabClick = false)

            }
        }
    }

}

@Composable
private fun BottomList(
    uiStateGetter: () -> DemoState,
    lazyListState: LazyListState,
    onChangeTab: (Int) -> Unit
) {
    // 滑动过程中持续联动tab
    LaunchedEffect(key1 = lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect {
                if (!uiStateGetter().fromTabClick) {
                    onChangeTab(it)
                }

            }
    }
    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.isScrollInProgress }
            .collect { isScrollInProgress ->
                if (!isScrollInProgress && uiStateGetter().fromTabClick) {
                    onChangeTab(uiStateGetter().selectedTab)

                }

            }
    }

    val list = uiStateGetter().list
    LazyColumn(
        Modifier
            .fillMaxSize(),
        state = lazyListState
    ) {
        itemsIndexed(list, contentType = { index, item -> item }) { index, item ->
            Column(Modifier.padding(horizontal = 20.dp)) {
                if (index != 0) {
                    Column(
                        modifier = Modifier.height(40.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = list[index],
                            style = titleStyle,
                        )
                    }
                }
                TopCard()
            }
        }

    }
}

@Composable
private fun HomeTabBar(
    uiState: DemoState,
    modifier: Modifier = Modifier,
    onTabClick: (Int) -> Unit
) {
    Column {
        TabBar(
            tabList = uiState.tabList,
            selectedTabIndex = uiState.selectedTab,
            modifier = modifier
        ) {
            onTabClick(it)

        }
    }

}