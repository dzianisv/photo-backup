package dev.dzianisv.snapdisk

import dev.dzianisv.snapdisk.backup.PhotoQuery
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for the From picker's media-name filter (WL-247): the folder
 * walk in PhotoQuery keeps only the extensions in IMAGE_EXTENSIONS, so a
 * format missing from that list is silently skipped by the picker.
 */
class PhotoQueryTest {

    @Test
    fun avifNameIsMedia() {
        assertTrue(PhotoQuery.isMediaName("photo.avif"))
    }

    @Test
    fun avifNameMatchesCaseInsensitively() {
        assertTrue(PhotoQuery.isMediaName("PHOTO.AVIF"))
    }

    @Test
    fun existingImageNamesStillMedia() {
        assertTrue(PhotoQuery.isMediaName("photo.jpg"))
        assertTrue(PhotoQuery.isMediaName("photo.webp"))
        assertTrue(PhotoQuery.isMediaName("clip.mp4"))
    }

    @Test
    fun nonMediaNameIsNotMedia() {
        assertFalse(PhotoQuery.isMediaName("notes.txt"))
        assertFalse(PhotoQuery.isMediaName("photo.avif.bak"))
    }
}
