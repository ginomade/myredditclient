package com.gino.myredditclient.model

import android.net.Uri
import com.gino.myredditclient.util.findUrls
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementUnion
import org.simpleframework.xml.Namespace
import java.io.Serializable
import java.util.*

@Entity
data class Entry(
        @Id
        var id: Long = 0,

        @field: Element(name = "id")
        var postId: String? = null,

        @field: Element
        @field: Convert(converter = Author.AuthorConverter::class, dbType = ByteArray::class)
        var author: Author? = null,

        @field: Element
        var title: String? = null,

        @field: Element
        var content: String? = null,

        @field: Element
        var updated: Date? = null,

        //@Namespace(prefix = "media")
        @field: Element(name ="media:thumbnail", required = false)
        @field: Convert(converter = Thumbnail.ThumbnailConverter::class, dbType = ByteArray::class)
        var thumbnail: Thumbnail? = null,

        @field: Element
        @field: Convert(converter = Link.LinkConverter::class, dbType = ByteArray::class)
        var link: Link? = null
) : Serializable {

    val imgUri: Uri?
        get() = content
            ?.findUrls()
            ?.firstOrNull { uri ->
                val lastPathSegment = uri.lastPathSegment ?: return@firstOrNull false
                val authority = uri.authority ?: return@firstOrNull false
                !authority.contains("thumbs")
                        && (lastPathSegment.contains("jpg", true)
                        || lastPathSegment.contains("jpeg", true)
                        || lastPathSegment.contains("png", true)
                        || lastPathSegment.contains("webp", true)
                        || lastPathSegment.contains("bmp", true))
            }
}