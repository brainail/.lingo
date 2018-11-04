package org.brainail.everboxing.lingo.util

import android.content.Context
import org.brainail.everboxing.lingo.data.repository.file.FileStore
import java.io.InputStream
import javax.inject.Inject

class AndroidFileStore @Inject constructor(private val context: Context) : FileStore {
    override fun openAsset(path: String): InputStream {
        return context.assets.open(path)
    }
}