package com.gino.myredditclient.model

import com.gino.myredditclient.util.Converter
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element

/**
 * @author gino.ghiotto
 */
data class Thumbnail (
    @Id
    var id: Long = 0,

    @field: Attribute
    var url: String? = null
) {
    class ThumbnailConverter : PropertyConverter<Thumbnail, ByteArray> {
        override fun convertToDatabaseValue(entityProperty: Thumbnail) = Converter.serialize(entityProperty)

        override fun convertToEntityProperty(databaseValue: ByteArray) = Converter.deserialize<Thumbnail>(databaseValue)
    }
}