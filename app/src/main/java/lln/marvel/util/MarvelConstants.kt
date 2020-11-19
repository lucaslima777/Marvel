package lln.marvel.util

class MarvelConstants private constructor() {

    object BUNDLE {
        const val DESCRIPTION = "description"
        const val ID = "id"
        const val NAME = "name"
        const val THUMBNAIL = "thumbnail"
    }

    object KEYS {
        const val PRIVATE = "0aff4289b8c8262dfd7e8a673522ed1892edd395"
        const val PUBLIC = "06baead4ed90594ecf5b655a25cdcf24"
    }

    object PARAMS {
        const val API_KEY = "apikey"
        const val HASH = "hash"
        const val LIMIT = "limit"
        const val OFFSET = "offset"
        const val TS = "ts"
    }

    object API {
        val API_BASE_URL = "http://gateway.marvel.com/v1/public/"
    }

}