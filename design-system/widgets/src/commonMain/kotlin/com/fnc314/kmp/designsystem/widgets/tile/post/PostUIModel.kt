package com.fnc314.kmp.designsystem.widgets.tile.post

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.SportsMma
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
data class PostUIModel(
    val author: String,
    @Serializable(with = InstantSerializer::class)
    val postDate: Instant,
    val postTitle: String,
    val postBody: String,
    val reactions: Map<PostReaction, Int>,
    val comments: Set<PostComment>,
) {
    fun key(index: Int): String = buildString {
        append(author)
        append("@")
        append(postDate)
        append("@")
        append(postTitle)
        append("#")
        append(index)
    }

    object InstantSerializer : KSerializer<Instant> {
        override val descriptor: SerialDescriptor = Instant.serializer().descriptor
        override fun serialize(encoder: Encoder, value: Instant) {
            Instant.serializer().serialize(encoder, value)
        }

        override fun deserialize(decoder: Decoder): Instant =
            Instant.serializer().deserialize(decoder)
    }

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

    @Serializable
    data class PostComment(
        val author: String,
        @Serializable(with = InstantSerializer::class)
        val commentDate: Instant,
        val commentBody: String,
        val comments: Set<PostComment> = emptySet()
    )
}
