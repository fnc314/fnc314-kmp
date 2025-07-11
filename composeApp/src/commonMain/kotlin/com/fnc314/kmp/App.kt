@file:OptIn(ExperimentalTime::class)

package com.fnc314.kmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
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
import com.fnc314.kmp.designsystem.widgets.tile.post.PostTile
import com.fnc314.kmp.designsystem.widgets.tile.post.PostUIModel
import com.fnc314.kmp.features.posts.list.PostsListScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val posts = remember {
            mutableListOf(
                PostUIModel(
                    author = "AUTHOR",
                    postDate = Clock.System.now(),
                    postBody = "Post BODY 1 ".repeat(Random.Default.nextInt(from = 2, until = 10)),
                    reactions = buildMap {
                        put(PostUIModel.PostReaction.Like, Random.Default.nextInt(from = 5, until = 20))
                        put(PostUIModel.PostReaction.Dislike, 5)
                    },
                    comments = buildSet {
                        add(
                            PostUIModel.PostComment(
                                author = "Comment Author2",
                                commentDate = Clock.System.now().plus(1.days),
                                commentBody = "Comment ".repeat(Random.Default.nextInt(until = 10)),
                                comments = emptySet(),
                            )
                        )
                    }
                ),
                PostUIModel(
                    author = "AUTHOR2",
                    postDate = Clock.System.now(),
                    postBody = "Post BODY 2 ".repeat(Random.Default.nextInt(from = 2, until = 10)),
                    reactions = buildMap {
                        put(PostUIModel.PostReaction.Like, 5)
                        put(PostUIModel.PostReaction.Dislike, Random.Default.nextInt(from = 5, until = 20))
                    },
                    comments = buildSet {
                        add(
                            PostUIModel.PostComment(
                                author = "Comment Author",
                                commentDate = Clock.System.now().plus(2.minutes),
                                commentBody = "Comment ".repeat(Random.Default.nextInt(until = 10)),
                                comments = emptySet(),
                            )
                        )
                    }
                ),
            )
        }
        Column(
            modifier = Modifier
                .safeContentPadding(),
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
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    PostsListScreen()
                    posts.onEachIndexed { index, model ->
                        PostTile(
                            post = model
                        ) { reaction ->
                            val reactions = buildMap {
                                putAll(posts[index].reactions)
                                if (posts[index].reactions.containsKey(reaction)) {
                                    put(reaction, posts[index].reactions[reaction]!! + 1)
                                } else {
                                    put(reaction, 1)
                                }
                            }
                            posts[index] = posts[index].copy(
                                reactions = reactions
                            )
                        }
                    }
                }
            }
        }
    }
}