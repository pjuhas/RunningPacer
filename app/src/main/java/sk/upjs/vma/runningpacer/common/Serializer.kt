@file:Suppress("BlockingMethodInNonBlockingContext")

package sk.upjs.vma.runningpacer.common

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTableData
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TrainingTableData
import java.io.InputStream
import java.io.OutputStream

object RaceSerializer : Serializer<RaceTableData> {
    override val defaultValue: RaceTableData
        get() = RaceTableData()

    override suspend fun readFrom(input: InputStream): RaceTableData {
        return try {
            Json.decodeFromString(
                deserializer = RaceTableData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: RaceTableData, output: OutputStream) {
        output.write(
            Json.encodeToString(serializer = RaceTableData.serializer(), value = t).encodeToByteArray()
        )
    }
}

object TrainingSerializer : Serializer<TrainingTableData> {
    override val defaultValue: TrainingTableData
        get() = TrainingTableData()

    override suspend fun readFrom(input: InputStream): TrainingTableData {
        return try {
            Json.decodeFromString(
                deserializer = TrainingTableData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: TrainingTableData, output: OutputStream) {
        output.write(
            Json.encodeToString(serializer = TrainingTableData.serializer(), value = t).encodeToByteArray()
        )
    }
}