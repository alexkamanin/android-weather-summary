package ru.shurick.enterprise.storage.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import ru.shurick.enterprise.storage.SettingsStore
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object SettingsSerializer : Serializer<SettingsStore> {

    override val defaultValue: SettingsStore
        get() = SettingsStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SettingsStore {
        try {
            return SettingsStore.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto: ", exception)
        }
    }

    override suspend fun writeTo(t: SettingsStore, output: OutputStream) {
        t.writeTo(output)
    }
}