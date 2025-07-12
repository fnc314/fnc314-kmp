@file:OptIn(ExperimentalTime::class)

package com.fnc314.kmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fnc314.kmp.composeapp.generated.resources.Res
import com.fnc314.kmp.composeapp.generated.resources.compose_multiplatform
import com.fnc314.kmp.features.posts.list.PostsListScreen
import com.fnc314.kmp.features.posts.list.PostsListViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(true) }
        val postsListViewModel = koinViewModel<PostsListViewModel>()
        Column(
            modifier = Modifier
                .safeContentPadding()
                .statusBarsPadding()
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { showContent = !showContent }
            ) {
                Text("Click me!")
            }
            AnimatedVisibility(
                visible = showContent
            ) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
            AnimatedVisibility(
                visible = !showContent,
                modifier = Modifier.fillMaxHeight()
            ) {
                PostsListScreen(
                    modifier = Modifier
                        .fillMaxHeight(),
                    postsListViewModel = postsListViewModel
                )
            }
        }
    }
}