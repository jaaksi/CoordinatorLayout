package com.example.coordinatorlayoutdemo.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.coordinatorlayoutdemo.R

@Composable
fun TopCard(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.mipmap.img_1),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

    }
}