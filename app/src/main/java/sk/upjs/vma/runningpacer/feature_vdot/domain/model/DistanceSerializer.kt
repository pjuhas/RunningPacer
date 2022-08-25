package sk.upjs.vma.runningpacer.feature_vdot.domain.model

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object DistanceSerializer : Serializer<TableData> {
    override val defaultValue: TableData
        get() = TableData()

    override suspend fun readFrom(input: InputStream): TableData {
        return try {
            Json.decodeFromString(
                deserializer = TableData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: TableData, output: OutputStream) {
        output.write(
            Json.encodeToString(serializer = TableData.serializer(), value = t).encodeToByteArray()
        )
    }

}