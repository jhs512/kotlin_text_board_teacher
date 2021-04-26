import java.text.SimpleDateFormat

fun main() {
    println("== 게시물 관리 프로그램 시작 ==")

    makeTestArticles()

    loop@ while (true) {
        print("명령어) ")
        val command = readLineTrim()
        val commandBits = command.split(" ")

        when {
            command == "system exit" -> {
                println("프로그램 종료")
                break@loop
            }
            command == "article write" -> {
                print("제목 : ")
                val title = readLineTrim()
                print("내용 : ")
                val body = readLineTrim()

                val id = addArticle(title, body)

                println("${id}번 게시물이 작성되었습니다.")
            }
            command.startsWith("article list") -> {
                var page = 1
                var searchKeyword = ""

                if (commandBits.size == 3) {
                    page = commandBits[2].toInt()
                }

                if (commandBits.size == 4) {
                    searchKeyword = commandBits[2]
                    page = commandBits[3].toInt()
                }

                println("번호 / 작성날짜 / 갱신날짜 / 제목")

                val takeCount = 10
                val offsetCount = (page - 1) * takeCount

                val filteredArticles = getFilteredArticles(searchKeyword, offsetCount, takeCount)

                for (article in filteredArticles) {
                    println("${article.id} / ${article.regDate} / ${article.updateDate} / ${article.title}")
                }
            }
            command.startsWith("article detail ") -> {
                val id = commandBits[2].toInt()

                val article = getArticleById(id)

                if (article == null) {
                    println("${id}번 게시물은 존재하지 않습니다.")
                    continue@loop
                }

                println("번호 : ${article.id}")
                println("작성날짜 : ${article.regDate}")
                println("갱신날짜 : ${article.updateDate}")
                println("제목 : ${article.title}")
                println("내용 : ${article.body}")
            }
            command.startsWith("article delete ") -> {
                val id = commandBits[2].toInt()

                val article = getArticleById(id)

                if (article == null) {
                    println("${id}번 게시물은 존재하지 않습니다.")
                    continue@loop
                }

                articles.remove(article)

                println("${id}번 게시물을 삭제하였습니다.")
            }
            command.startsWith("article modify ") -> {
                val id = commandBits[2].toInt()

                val article = getArticleById(id)

                if (article == null) {
                    println("${id}번 게시물은 존재하지 않습니다.")
                    continue@loop
                }

                print("${id}번 게시물 새 제목 : ")
                val title = readLineTrim()
                print("${id}번 게시물 새 내용 : ")
                val body = readLineTrim()

                article.title = title
                article.body = body
                article.updateDate = Util.getNowDateStr()

                println("${id}번 게시물을 수정하였습니다.")
            }
            else -> {
                println("`$command`(은)는 존재하지 않는 명령어 입니다.")
            }
        }
    }

    println("== 게시물 관리 프로그램 끝 ==")
}

// 게시물 관련 시작
data class Article(
    val id: Int,
    val regDate: String,
    var updateDate: String,
    var title: String,
    var body: String
)

fun getArticleById(id: Int): Article? {
    for (article in articles) {
        if (article.id == id) {
            return article
        }
    }

    return null
}

fun addArticle(title: String, body: String): Int {
    val id = articlesLastId + 1
    val regDate = Util.getNowDateStr()
    val updateDate = Util.getNowDateStr()
    articles.add(Article(id, regDate, updateDate, title, body))
    articlesLastId = id

    return id
}

fun makeTestArticles() {
    for (id in 1..100) {
        addArticle("제목_$id", "내용_$id")
    }
}

fun getFilteredArticles(searchKeyword: String, offsetCount: Int, takeCount: Int): List<Article> {
    var filtered1Articles = articles

    if (searchKeyword.isNotEmpty()) {
        filtered1Articles = mutableListOf()

        for (article in articles) {
            if (article.title.contains(searchKeyword)) {
                filtered1Articles.add(article)
            }
        }
    }

    val filtered2Articles = mutableListOf<Article>()

    val startIndex = filtered1Articles.lastIndex - offsetCount
    var endIndex = startIndex - (takeCount - 1)

    if (endIndex < 0) {
        endIndex = 0
    }

    for (i in startIndex downTo endIndex) {
        filtered2Articles.add(filtered1Articles[i])
    }

    return filtered2Articles
}

val articles = mutableListOf<Article>()
var articlesLastId = 0
// 게시물 관련 끝

// 유틸관련 시작
fun readLineTrim() = readLine()!!.trim()

object Util {
    fun getNowDateStr(): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return format.format(System.currentTimeMillis())
    }
}
// 유틸관련 끝