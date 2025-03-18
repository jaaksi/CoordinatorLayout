package com.example.coordinatorlayoutdemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coordinatorlayoutdemo.R
import com.example.coordinatorlayoutdemo.ui.theme.AppColors
import org.jaaksi.coordinatorlayout.CoordinatorLayout
import org.jaaksi.coordinatorlayout.rememberCoordinatorState

@Preview
@Composable
private fun Preview() {
    SimpleScreen6()
}

// collapsableContent底部的内容吸顶
@Composable
fun SimpleScreen6() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
    ) {
        val lazyListState = rememberLazyListState()
        val coordinatorState = rememberCoordinatorState()
        val nonCollapsableHeight = with(LocalDensity.current) { 30.dp.roundToPx() }


        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            DemoTitle()
        }

        CoordinatorLayout(
            nestedScrollableState = { lazyListState },
            state = coordinatorState,
            modifier = Modifier.fillMaxSize(),
            collapsableContent = {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.img_1),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                this.alpha = coordinatorState.collapsedHeight / coordinatorState.maxCollapsableHeight
                            }
                            .background(Color.White))

                    Box(
                        Modifier
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 15.dp)
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(CircleShape)
                            .background(AppColors.bgColor)
                    ) {
                        Spacer(
                            Modifier
                                .fillMaxWidth(0.3f)
                                .fillMaxHeight()
                                .background(AppColors.Theme)
                        )
                    }
                }
            },
            nonCollapsableHeight = nonCollapsableHeight
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