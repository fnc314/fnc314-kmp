package com.fnc314.kmp.designsystem.widgets.tile.post

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.SportsMma
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class PostUIModel(
    val author: String,
    val postDate: Instant,
    val postBody: String,
    val reactions: Map<PostReaction, Int>,
    val comments: Set<PostComment>,
) {
    sealed interface PostReaction {
        val imageVector: ImageVector?
        data object None: PostReaction {
            override val imageVector: ImageVector? = null
        }
        data object Like: PostReaction {
            override val imageVector: ImageVector = Icons.Filled.ThumbUp
        }
        data object Dislike: PostReaction {
            override val imageVector: ImageVector = Icons.Filled.ThumbDown
        }
        sealed interface Warning: PostReaction {
            data object Violence: Warning {
                override val imageVector: ImageVector = Icons.Filled.SportsMma
            }
            data object Racism: Warning {
                override val imageVector: ImageVector = Icons.Filled.PersonOff
            }
            data class Other(val concerningIssue: String, override val imageVector: ImageVector): Warning
        }
    }
    data class PostComment(
        val author: String,
        val commentDate: Instant,
        val commentBody: String,
        val comments: Set<PostComment> = emptySet()
    )
}
