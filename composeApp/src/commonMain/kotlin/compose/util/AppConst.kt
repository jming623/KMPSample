package compose.util

object AppConst {
    /**
     * FIXME
     * 로컬에서 실행할 경우 [BASE_URL] 수정할 것
     * * EX) http://localhost:8080
     */
    const val BASE_URL = "http://10.10.11.44:50006"

    fun getUrl(path: String = ""): String = if (path.first() == '/') {
        "$BASE_URL$path"
    } else {
        "$BASE_URL/$path"
    }
}