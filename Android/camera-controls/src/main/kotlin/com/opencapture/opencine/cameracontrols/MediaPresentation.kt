package com.opencapture.opencine.cameracontrols

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.opencapture.opencine.vocabulary.MediaClipFormatting
import com.opencapture.opencine.vocabulary.MediaFile
import com.opencapture.opencine.vocabulary.MediaKind
import com.opencapture.opencine.vocabulary.MediaLibraryTab

enum class MediaBrowserLayout {
    GRID,
    LIST,
}

enum class MediaThumbnailSize {
    SMALL,
    MEDIUM,
    LARGE,
    ;

    val gridMinimumDp: Int
        get() =
            when (this) {
                SMALL -> 148
                MEDIUM -> 210
                LARGE -> 280
            }

    val gridMaximumDp: Int
        get() =
            when (this) {
                SMALL -> 200
                MEDIUM -> 300
                LARGE -> 380
            }

    val gridIconSizeDp: Int
        get() =
            when (this) {
                SMALL -> 9
                MEDIUM -> 12
                LARGE -> 15
            }

    val accessibilityLabel: String
        get() =
            when (this) {
                SMALL -> "Small thumbnails"
                MEDIUM -> "Medium thumbnails"
                LARGE -> "Large thumbnails"
            }
}

object MediaClipPresentation {
    fun resolutionLabel(resolution: String?): String? {
        if (resolution.isNullOrEmpty()) return null
        val width = resolution.substringBefore('x').toIntOrNull()
        return when {
            width == null -> resolution
            width >= 3840 -> "4K"
            width >= 2560 -> "2.7K"
            width >= 1920 -> "1080p"
            width >= 1280 -> "720p"
            else -> resolution
        }
    }

    fun dateLabel(dateKey: String): String {
        if (dateKey.length != 8) return dateKey
        return "${dateKey.substring(0, 4)}-${dateKey.substring(4, 6)}-${dateKey.substring(6, 8)}"
    }

    fun metadataLine(file: MediaFile, durationOverride: String? = null): String {
        val parts = ArrayList<String>(3)
        resolutionLabel(file.resolution)?.let { parts.add(it) }
        val bytes = MediaClipFormatting.byteLabel(file.sizeBytes)
        if (bytes.isNotEmpty()) parts.add(bytes)
        if (file.kind == MediaKind.VIDEO) {
            parts.add(durationOverride ?: MediaClipFormatting.durationLabel(file.durationSeconds))
        }
        return parts.joinToString(" · ")
    }
}

/** Operator-facing media-browser copy. Never name a sister app. */
object MediaLibraryCopy {
    const val FILTER_EMPTY = "Nothing in this tab matches the filters."
    const val EMPTY_ALL = "Nothing on this camera yet. Record a clip, then pull to refresh."
    const val EMPTY_FAVORITES = "Nothing favorited yet. Star a clip to find it here."
    const val EMPTY_VIDEOS = "No videos on this camera yet. Record a clip, then pull to refresh."
    const val EMPTY_PHOTOS = "No photos on this camera yet. Capture a still, then pull to refresh."
    const val DISCONNECTED = "Connect the camera to list clips on the body."
    const val DISCONNECTED_EMPTY_CACHE =
        "No cached clips here. Connect the camera to list clips on the body."
    /**
     * Offline, the gallery shows only clips cached on this phone; the last card listing stays
     * hidden (the documented offline browse: downloaded clips only), and the gallery says so.
     */
    const val DISCONNECTED_CACHED_ONLY = "Camera disconnected. Showing clips cached on this phone."
    const val BROWSE_HINT = "Tap to open · Hold, then drag to select"
    const val SELECTING_HINT = "Swipe to scroll · Hold or drag sideways to select"
    const val READING_HINT = "Reading camera media…"
    const val LISTING_TITLE = "Listing clips on camera…"
    const val FILTER_EMPTY_TITLE = "No matching clips"
    const val DISCONNECTED_TITLE = "Camera disconnected"
    const val NO_CACHED_TITLE = "No cached clips"
    const val EMPTY_TITLE = "No clips yet"

    /** The gallery's hint line under its item count (Swift `MonitorMediaGalleryCopy.galleryHint`). */
    fun galleryHint(fetching: Boolean, selecting: Boolean, isLive: Boolean): String =
        when {
            fetching -> READING_HINT
            selecting -> SELECTING_HINT
            isLive -> BROWSE_HINT
            else -> DISCONNECTED_CACHED_ONLY
        }

    /**
     * The empty gallery's heading, in the same order as [emptySubtitle]: "No clips yet" only when a
     * connected camera listed nothing for this tab.
     */
    fun emptyTitle(listing: Boolean, isLive: Boolean, hasListedClips: Boolean, activeFilterCount: Int): String =
        when {
            listing -> LISTING_TITLE
            !isLive && !hasListedClips -> DISCONNECTED_TITLE
            activeFilterCount > 0 -> FILTER_EMPTY_TITLE
            !isLive -> NO_CACHED_TITLE
            else -> EMPTY_TITLE
        }
    const val PROXY_TAG = "Proxy"
    const val PROXY_HELP = "720p preview. Connect the camera to share the original."

    fun headerTitle(category: MediaLibraryTab): String =
        when (category) {
            MediaLibraryTab.ALL -> "All clips"
            MediaLibraryTab.VIDEOS -> "Videos"
            MediaLibraryTab.PHOTOS -> "Photos"
            MediaLibraryTab.FAVORITES -> "Favorites"
        }

    /**
     * The empty gallery's line: offline with nothing listed, filtered (before the cache is blamed,
     * ZIB-413), offline with listed clips, or the tab's own.
     */
    fun emptySubtitle(isLive: Boolean, hasListedClips: Boolean, activeFilterCount: Int, category: MediaLibraryTab): String =
        when {
            !isLive && !hasListedClips -> DISCONNECTED
            activeFilterCount > 0 -> FILTER_EMPTY
            !isLive -> DISCONNECTED_EMPTY_CACHE
            category == MediaLibraryTab.FAVORITES -> EMPTY_FAVORITES
            category == MediaLibraryTab.VIDEOS -> EMPTY_VIDEOS
            category == MediaLibraryTab.PHOTOS -> EMPTY_PHOTOS
            else -> EMPTY_ALL
        }
}

object MediaOperatorCopy {
    const val LISTING = "Listing camera clips…"
    const val NOT_CONNECTED = "Connect the camera to list clips."
    const val PLAYBACK_FAILED = "Camera did not enter playback."
    const val BROWSING_ON_CAMERA = "Playback is open on the camera. Close to return to live view."
    const val NO_CLIPS = "No clips on the camera."
    const val LIST_FAILED = "Could not list camera clips."
    const val NOT_DELETABLE = "That clip cannot be deleted from here."
    const val DELETE_FAILED = "Could not delete that clip."
    const val DOWNLOAD_FAILED = "Could not download that clip."
    const val THUMB_FAILED = "Could not load that thumbnail."
    const val CLIP_OPEN_FAILED = "Could not open that clip."
    const val CLIP_LOADING = "Loading clip from camera…"
    const val CLIP_NOT_CACHED = "This clip is not cached on the phone."
}

/**
 * The same media-browser copy, resolved from resources so it reads in the operator's language.
 * The constants above stay the verbatim English a JVM test can assert against.
 */
@Composable
public fun mediaLibraryGalleryHint(fetching: Boolean, selecting: Boolean, isLive: Boolean): String =
    when {
        fetching -> stringResource(R.string.media_library_reading_hint)
        selecting -> stringResource(R.string.media_library_selecting_hint)
        isLive -> stringResource(R.string.media_library_browse_hint)
        else -> stringResource(R.string.media_library_disconnected_cached_only)
    }

@Composable
public fun mediaLibraryEmptyTitle(listing: Boolean, isLive: Boolean, hasListedClips: Boolean,
    activeFilterCount: Int): String =
    when {
        listing -> stringResource(R.string.media_library_listing_title)
        !isLive && !hasListedClips -> stringResource(R.string.media_library_disconnected_title)
        activeFilterCount > 0 -> stringResource(R.string.media_library_filter_empty_title)
        !isLive -> stringResource(R.string.media_library_no_cached_title)
        else -> stringResource(R.string.media_library_empty_title)
    }

@Composable
public fun mediaLibraryEmptySubtitle(isLive: Boolean, hasListedClips: Boolean, activeFilterCount: Int,
    category: MediaLibraryTab): String =
    when {
        !isLive && !hasListedClips -> stringResource(R.string.media_library_disconnected)
        activeFilterCount > 0 -> stringResource(R.string.media_library_filter_empty)
        !isLive -> stringResource(R.string.media_library_disconnected_empty_cache)
        category == MediaLibraryTab.FAVORITES -> stringResource(R.string.media_library_empty_favorites)
        category == MediaLibraryTab.VIDEOS -> stringResource(R.string.media_library_empty_videos)
        category == MediaLibraryTab.PHOTOS -> stringResource(R.string.media_library_empty_photos)
        else -> stringResource(R.string.media_library_empty_all)
    }

@Composable
public fun mediaLibraryHeaderTitle(category: MediaLibraryTab): String =
    when (category) {
        MediaLibraryTab.ALL -> stringResource(R.string.media_library_header_all)
        MediaLibraryTab.VIDEOS -> stringResource(R.string.media_library_header_videos)
        MediaLibraryTab.PHOTOS -> stringResource(R.string.media_library_header_photos)
        MediaLibraryTab.FAVORITES -> stringResource(R.string.media_library_header_favorites)
    }
