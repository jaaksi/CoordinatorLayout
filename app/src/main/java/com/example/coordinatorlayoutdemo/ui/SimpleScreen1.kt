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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coordinatorlayoutdemo.ui.theme.AppColors
import org.jaaksi.coordinatorlayout.CoordinatorLayout
import org.jaaksi.coordinatorlayout.rememberCoordinatorState

// collapsable + LazyColumn
@Composable
fun SimpleScreen1() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {
        val lazyListState = rememberLazyListState()
        val coordinatorState = rememberCoordinatorState()

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

        CoordinatorLayout(
            nestedScrollableState = { lazyListState },
            state = coordinatorState,
            modifier = Modifier.fillMaxSize(),
            collapsableContent = {
                com.example.coordinatorlayoutdemo.widget.TopCard()
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

@Composable
fun DemoTitle(modifier: Modifier = Modifier) {
    Text(
        text = "TitleBar",
        style = TextStyle.Default.copy(
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = modifier
    )
}