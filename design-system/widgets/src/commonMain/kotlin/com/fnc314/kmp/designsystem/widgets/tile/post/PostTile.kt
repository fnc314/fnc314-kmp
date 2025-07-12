@file:OptIn(ExperimentalTime::class)

package com.fnc314.kmp.designsystem.widgets.tile.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.time.ExperimentalTime

@Composable
fun PostTile(
    modifier: Modifier = Modifier,
    post: PostUIModel,
    onPostReaction: (reaction: PostUIModel.PostReaction) -> Unit = { },
) {
    Card(
        modifier = modifier,
        shape = CardDefaults.elevatedShape,
        colors = CardDefaults.elevatedCardColors(),
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Column(
            modifier = modifier
                .padding(all = 8.dp)
                .wrapContentHeight(unbounded = true),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    text = post.author,
                )
                Text(
                    textAlign = TextAlign.End,
                    text = post.postDate.toString()
                )
            }
            Row(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = post.postBody
                )
            }
            Row(
                modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                post.reactions.onEach { reaction ->
                    if (reaction.key != PostUIModel.PostReaction.None) {
                        Button(
                            onClick = { onPostReaction(reaction.key) },
                            modifier = Modifier,
                            colors = ButtonDefaults.filledTonalButtonColors(),
                        ) {
                            Icon(
                                modifier = Modifier.padding(all = 10.dp),
                                imageVector = reaction.key.imageVector!!,
                                contentDescription = reaction::class.simpleName
                            )
                            Text(text = reaction.value.toString())
                        }
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(top = 2.dp, bottom = 2.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                post.comments.onEachIndexed { index, comment ->
                    Text(text = comment.commentBody)
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = comment.author
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = comment.commentDate.toString(),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}