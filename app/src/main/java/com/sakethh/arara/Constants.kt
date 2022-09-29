package com.sakethh.arara

object Constants {
    const val BASE_URL = "https://arara-server-side.herokuapp.com"
    const val UNRELEASED="/unreleased"
    private const val ANDROID_CLIENT="/androidClient"
    const val UNRELEASED_HEADER_IMG_URL= "$BASE_URL$ANDROID_CLIENT/unreleasedHeaderURL"
    const val UNRELEASED_FOOTER_IMG_URL="$BASE_URL$ANDROID_CLIENT/footerGIFURL"
    const val MUSIC_LOADING_GIF="$BASE_URL$ANDROID_CLIENT/loadingGIFForBottomMusicPlayer"
    const val MUSIC_PLAYING_GIF="$BASE_URL$ANDROID_CLIENT/playingGIFForBottomMusicPlayer"
    const val MUSIC_ERROR_GIF="https://ia801509.us.archive.org/6/items/arara-android/red%20color%20playing%20cropped.gif"
}
//retrofit2.HttpException: HTTP 500 Internal Server Error