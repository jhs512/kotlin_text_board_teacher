fun readLineTrim() = readLine()!!.trim()

fun main() {
    println("== 게시판 관리 프로그램 시작 ==")

    // 가장 마지막에 입력된 게시물 번호
    var articlesLastId = 0

    while (true) {
        print("명령어) ")
        val command = readLineTrim()

        if (command == "system exit") {
            println("프로그램을 종료합니다.")
            break
        } else if (command == "article write") {
            val id = articlesLastId + 1
            print("제목 : ")
            val title = readLineTrim()
            print("내용 : ")
            val body = readLineTrim()
            val article = Article(id, title, body)

            println("${id}번 게시물이 작성되었습니다.")

            articlesLastId = id
        }
    }

    println("== 게시판 관리 프로그램 끝 ==")
}

data class Article(
    val id: Int,
    val title: String,
    val body: String
)