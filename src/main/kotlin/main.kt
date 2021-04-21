fun main() {
    println("== 게시판 관리 프로그램 시작 ==")

    while ( true ) {
        print("명령어) ")
        val command = readLine()

        if ( command == "system exit" ) {
            println("프로그램을 종료합니다.")
            break
        }
    }

    println("== 게시판 관리 프로그램 끝 ==")
}