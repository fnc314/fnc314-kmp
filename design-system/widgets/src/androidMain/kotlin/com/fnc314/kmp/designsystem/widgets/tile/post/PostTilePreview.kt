@file:OptIn(ExperimentalTime::class)

package com.fnc314.kmp.designsystem.widgets.tile.post

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@Preview
@Composable
fun PostTilePreview() {
    MaterialTheme {
        PostTile(
            modifier = Modifier.padding(all = 8.dp),
            post = PostUIModel(
                author = "Some Author",
                postDate = Clock.System.now(),
                postTitle = "POST-by-Author",
                postBody = "SOME CONTENT ".repeat(20),
                reactions = buildMap {
                    put(PostUIModel.PostReaction.Like, Random.Default.nextInt(from = 2, until = 10))
                    put(PostUIModel.PostReaction.Dislike, Random.Default.nextInt(from = 2, until = 10))
                    put(PostUIModel.PostReaction.Warning.Violence, Random.Default.nextInt(from = 2, until = 10))
                },
                comments = buildSet {
                    add(
                        PostUIModel.PostComment(
                            author = "Another Author",
                            commentDate = Clock.System.now().plus(duration = 20.minutes),
                            commentBody = "SOME COMMENT".repeat(4)
                        )
                    )
                }
            )
        ) {

        }
    }
}