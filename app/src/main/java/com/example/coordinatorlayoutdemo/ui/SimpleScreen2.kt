package com.example.coordinatorlayoutdemo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.coordinatorlayoutdemo.data.DemoState
import com.example.coordinatorlayoutdemo.ui.theme.AppColors
import com.example.coordinatorlayoutdemo.widget.TabBar
import com.example.coordinatorlayoutdemo.widget.TopCard
import kotlinx.coroutines.launch
import org.jaaksi.coordinatorlayout.CoordinatorLayout
import org.jaaksi.coordinatorlayout.rememberCoordinatorState

// collapsable + pin + LazyColumn
@Composable
fun SimpleScreen2() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {
        val coroutineScope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()
        val coordinatorState = rememberCoordinatorState()
        var uiState by remember { mutableStateOf(DemoState()) }
        var hasRecent by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            DemoTitle()

            Button(onClick = { hasRecent = !hasRecent }, modifier = Modifier.align(Alignment.CenterEnd)) { }
        }

        HorizontalDivider(color = AppColors.Divider)

        CoordinatorLayout(
            nestedScrollableState = { lazyListState },
            state = coordinatorState,
            modifier = Modifier.fillMaxSize(),
            collapsableContent = {
                Column {
                    if(hasRecent){
                        Box(modifier = Modifier.fillMaxWidth().height(300.dp).background(AppColors.Theme))
                    }
                    TopCard()
                }

            },
            pinContent = {
                TabBar(
                    tabList = uiState.tabList,
                    selectedTabIndex = uiState.selectedTab,
                ) {
                    // 吸顶
                    coroutineScope.launch {
                        uiState = uiState.copy(selectedTab = it)
                        coordinatorState.animateToCollapsed()
                    }

                }
            },
        ) {
            LazyColumn(Modifier.fillMaxSize(), state = lazyListState) {
                items(30) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(horizontal = 15.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Item $it",
                            textAlign = TextAlign.Center,

                            )
                        HorizontalDivider(
                            thickness = 0.7.dp,
                            color = AppColors.Divider,
                            modifier = Modifier.align(Alignment.BottomStart)
                        )

                    }
                }

            }
        }
    }

}