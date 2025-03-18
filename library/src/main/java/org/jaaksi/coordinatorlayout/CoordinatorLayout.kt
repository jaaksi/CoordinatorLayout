package org.jaaksi.coordinatorlayout

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import kotlin.math.roundToInt

/**
 * @param collapsableContent 可折叠的Content
 * @param pinContent 要吸顶的Content，默认为空的
 * @param content 底部的Content
 * @param nonCollapsableHeight 不允许折叠的高度，至少为0
 * @param nestedScrollableState 用于collapsableContent和pinContent快速滑动，完全折叠后，剩余Fling交给content来响应。如果不设置，完全折叠后，content不能响应剩余Fling
 *
 */
@Composable
fun CoordinatorLayout(
    nestedScrollableState: () -> ScrollableState?,
    collapsableContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    pinContent: @Composable () -> Unit = {},
    state: CoordinatorState = rememberCoordinatorState(),
    nonCollapsableHeight: Int = 0,
    content: @Composable () -> Unit
) {
    check(nonCollapsableHeight >= 0) {
        "nonCollapsableHeight is at least 0!"
    }

    val flingBehavior = ScrollableDefaults.flingBehavior()
    Layout(
        content = {
            collapsableContent()
            pinContent()
            content()
        }, modifier = modifier
            .clipToBounds()
            .fillMaxSize()
            .scrollable(
                state = state.scrollableState,
                orientation = Orientation.Vertical,
                enabled = !state.isFullyCollapsed,
                flingBehavior = remember {
                    object : FlingBehavior {
                        override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                            val remain = with(flingBehavior) {
                                performFling(initialVelocity)
                            }
                            // 外层响应Fling后，剩余的交给nestedScrollableState来处理
                            if (remain < 0 && nestedScrollableState() != null) { // 向上滑动，剩余的Fling交给nestedScrollableState消费
                                nestedScrollableState()!!.scroll {
                                    with(flingBehavior){
                                        performFling(-remain)
                                    }
                                }
                                return 0f
                            }
                            return remain
                        }
                    }
                },
            )
            .nestedScroll(state.nestedScrollConnection)
    ) { measurables, constraints ->
        check(constraints.hasBoundedHeight)
        val height = constraints.maxHeight
        val collapsablePlaceable = measurables[0].measure(
            constraints.copy(minHeight = 0, maxHeight = Constraints.Infinity)
        )
        val collapsableContentHeight = collapsablePlaceable.height
        val pinPlaceable: Placeable? = if (measurables.size == 3) {
            measurables[1].measure(
                constraints.copy(minHeight = 0, maxHeight = Constraints.Infinity)
            )
        } else null
        val pinContentHeight = pinPlaceable?.height ?: 0
        val safeNonCollapsableHeight = nonCollapsableHeight.coerceAtMost(collapsableContentHeight)
        val nestedScrollPlaceable = measurables[measurables.lastIndex].measure(
            constraints.copy(
                minHeight = 0,
                maxHeight = (height - pinContentHeight - safeNonCollapsableHeight).coerceAtLeast(0)
            )
        )
        state.maxCollapsableHeight =
            (collapsablePlaceable.height - safeNonCollapsableHeight).toFloat().coerceAtLeast(0f)
        layout(constraints.maxWidth, height) {
            val collapsedHeight = state.collapsedHeight.roundToInt()
            nestedScrollPlaceable.placeRelative(0, collapsableContentHeight + pinContentHeight - collapsedHeight)
            collapsablePlaceable.placeRelative(0, -collapsedHeight)
            pinPlaceable?.placeRelative(0, collapsableContentHeight - collapsedHeight)
        }
    }
}