package com.sakethh.arara

object Constants {
    const val BASE_URL = "https://arara-server-side.herokuapp.com"
    const val UNRELEASED="/unreleased"
    const val SUBREDDIT="/subReddit"
    private const val ANDROID_CLIENT="/androidClient"
    const val UNRELEASED_HEADER_IMG_URL= "$BASE_URL$ANDROID_CLIENT/unreleasedHeaderURL"
    const val UNRELEASED_FOOTER_IMG_URL="$BASE_URL$ANDROID_CLIENT/footerGIFURL"
    const val MUSIC_LOADING_GIF="$BASE_URL$ANDROID_CLIENT/loadingGIFForBottomMusicPlayer"
    const val MUSIC_PLAYING_GIF="$BASE_URL$ANDROID_CLIENT/playingGIFForBottomMusicPlayer"
    const val MUSIC_ERROR_GIF="https://ia801509.us.archive.org/6/items/arara-android/red%20color%20playing%20cropped.gif"
}
object FanartsURL{
    const val hot="${Constants.SUBREDDIT}/fanarts/hot"
    const val relevance="${Constants.SUBREDDIT}/fanarts/relevance"
    const val topAllTime="${Constants.SUBREDDIT}/fanarts/topAllTime"
    const val topPast24Hours="${Constants.SUBREDDIT}/fanarts/topPast24Hours"
    const val topPastHour="${Constants.SUBREDDIT}/fanarts/topPastHour"
    const val topPastMonth="${Constants.SUBREDDIT}/fanarts/topPastMonth"
    const val topPastWeek="${Constants.SUBREDDIT}/fanarts/topPastWeek"
    const val topPastYear="${Constants.SUBREDDIT}/fanarts/topPastYear"

}
object ImagesURL{
    const val hot="${Constants.SUBREDDIT}/image/hot"
    const val relevance="${Constants.SUBREDDIT}/image/relevance"
    const val topAllTime="${Constants.SUBREDDIT}/image/topAllTime"
    const val topPast24Hours="${Constants.SUBREDDIT}/image/topPast24Hours"
    const val topPastHour="${Constants.SUBREDDIT}/image/topPastHour"
    const val topPastMonth="${Constants.SUBREDDIT}/image/topPastMonth"
    const val topPastWeek="${Constants.SUBREDDIT}/image/topPastWeek"
    const val topPastYear="${Constants.SUBREDDIT}/image/topPastYear"
}
object NewsURL{
    const val hot="${Constants.SUBREDDIT}/news/hot"
    const val relevance="${Constants.SUBREDDIT}/news/relevance"
    const val topAllTime="${Constants.SUBREDDIT}/news/topAllTime"
    const val topPast24Hours="${Constants.SUBREDDIT}/news/topPast24Hours"
    const val topPastHour="${Constants.SUBREDDIT}/news/topPastHour"
    const val topPastMonth="${Constants.SUBREDDIT}/news/topPastMonth"
    const val topPastWeek="${Constants.SUBREDDIT}/news/topPastWeek"
    const val topPastYear="${Constants.SUBREDDIT}/news/topPastYear"
}

//retrofit2.HttpException: HTTP 500 Internal Server Error