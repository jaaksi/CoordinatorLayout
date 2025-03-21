# Compose CoordinatorLayout
Compose实现的CoordinatorLayout，实现了AppBarLayout、CollapsingToolbarLayout、NestedScrollView、RecyclerView等的简单联动、吸顶效果等。还支持指定位置的吸顶效果。

## Screenshot
![](https://github.com/jaaksi/CoordinatorLayout/blob/master/docs/demo1.gif)
![](https://github.com/jaaksi/CoordinatorLayout/blob/master/docs/demo6.gif)
![](https://github.com/jaaksi/CoordinatorLayout/blob/master/docs/demo2.gif)

## Structure
![](https://github.com/jaaksi/CoordinatorLayout/blob/master/docs/structure.png)

## 详细说明
[用Compose撸一个CoordinatorLayout 🔥🔥🔥](https://juejin.cn/post/7482670776481349673)

## Usage
```kotlin
// collapsable + pin + LazyColumn
@Composable
fun SimpleScreen() {
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
                    if (hasRecent) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(AppColors.Theme))
                    }
                    TopCard()
                }

            },
        ) {
            Column(Modifier.fillMaxSize()) {
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

}

```