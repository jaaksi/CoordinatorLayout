package com.example.coordinatorlayoutdemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coordinatorlayoutdemo.R
import com.example.coordinatorlayoutdemo.data.DemoState
import com.example.coordinatorlayoutdemo.ui.theme.AppColors
import com.example.coordinatorlayoutdemo.widget.TabBar
import com.example.coordinatorlayoutdemo.widget.TopCard
import kotlinx.coroutines.launch
import org.jaaksi.coordinatorlayout.CoordinatorLayout
import org.jaaksi.coordinatorlayout.rememberCoordinatorState


@Composable
fun SimpleScreen5() {
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
            DemoTitle()
        }

        HorizontalDivider(color = AppColors.Divider)

        val list = remember { DemoState().tabList }
        val coroutineScope = rememberCoroutineScope()

        val lazyListState = rememberLazyListState()
        val coordinatorState = rememberCoordinatorState()
        // var contentScrollableState by remember { mutableStateOf<ScrollableState?>(lazyListState) }
        var contentScrollableState: ScrollableState = remember { lazyListState }
        val pagerState = rememberPagerState { list.size }
        CoordinatorLayout(
            nestedScrollableState = { contentScrollableState },
            state = coordinatorState,
            modifier = Modifier.fillMaxSize(),
            collapsableContent = {
                TopCard()
            },
        ) {
            Column(Modifier.fillMaxSize()) {
                TabBar(
                    tabList = list,
                    selectedTabIndex = pagerState.currentPage,
                ) {
                    coroutineScope.launch {
                        launch {
                            pagerState.animateScrollToPage(it)
                        }
                        coordinatorState.animateToCollapsed()
                    }

                }
                val scrollState = rememberScrollState()
                val lazyGridState = rememberLazyGridState()
                LaunchedEffect(pagerState) {
                    snapshotFlow { pagerState.currentPage }
                        .collect {
                            contentScrollableState = when (it) {
                                0 -> lazyListState
                                1 -> lazyGridState
                                else -> scrollState
                            }
                        }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                ) { page ->
                    when (page) {
                        0 -> {
                            LazyColumn(state = lazyListState) {
                                items(20) {
                                    TopCard()
                                }
                            }
                        }

                        1 -> {
                            LazyVerticalGrid(GridCells.Fixed(2), state = lazyGridState) {
                                items(20) {
                                    TopCard()
                                }
                            }
                        }

                        2 -> {
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .verticalScroll(scrollState)
                            ) {
                                Text("Content with verticalScroll.")
                                Image(
                                    painter = painterResource(id = R.mipmap.img_1),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(700.dp),
                                    contentScale = ContentScale.Crop
                                )

                            }

                        }

                        else -> {
                            Box(Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState), contentAlignment = Alignment.Center) {
                                Text(
                                    text = list[page].getTabName(),
                                    style = TextStyle.Default.copy(
                                        color = AppColors.Black,
                                        fontSize = 40.sp
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }

                }
            }

        }
    }

}