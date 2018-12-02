package org.brainail.everboxing.lingo.data.repository.file

import java.io.InputStream

interface FileStore {
    /**
     * Returns [InputStream] based on the file that comes with the application.
     * The location can differ due to app type. In Android it will be **assets** folder.
     *
     * @param path the relative path to file
     */
    fun openAsset(path: String): InputStream
}
