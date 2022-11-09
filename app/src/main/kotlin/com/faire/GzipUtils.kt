package com.faire

import java.io.InputStream
import java.io.PushbackInputStream
import java.util.zip.GZIPInputStream

object GzipUtils {
    fun maybeDecompress(input: InputStream): InputStream {
        val pushbackInputStream = PushbackInputStream(input, 2)

        var header: Int = pushbackInputStream.read()
        if (header == -1) {
            return pushbackInputStream
        }

        val b: Int = pushbackInputStream.read()
        if (b == -1) {
            pushbackInputStream.unread(header)
            return pushbackInputStream
        }

        pushbackInputStream.unread(byteArrayOf(header.toByte(), b.toByte()))

        header = b shl 8 or header

        return if (header == GZIPInputStream.GZIP_MAGIC) {
            GZIPInputStream(pushbackInputStream)
        } else {
            pushbackInputStream
        }
    }
}