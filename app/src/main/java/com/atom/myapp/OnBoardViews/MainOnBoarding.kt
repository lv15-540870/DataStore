package com.atom.myapp.onBoardViews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.atom.myapp.data.PageData
import com.atom.myapp.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainOnBoarding(onClick: () -> Unit) {
    val items = ArrayList<PageData>()

    items.add(
        PageData(
            R.raw.welcome,
            title = "Entrada",
            description = "Descripcion de la aplicacion en el momento de la entrada"
        )
    )

    items.add(
        PageData(
            R.raw.carwash,
            title = "Medio",
            description = "Descripcion de la aplicacion en el momento medio de la aplicacion"
        )
    )

    items.add(
        PageData(
            R.raw.redcar,
            title = "Final",
            description = "Descripcion de la aplicacion en el momento de la entrada"
        )
    )

    val pagerState = rememberPagerState(
        pageCount = items.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )

    OnBoardingPager(
        item = items,
        pagerState = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        onClick = onClick
    )
}