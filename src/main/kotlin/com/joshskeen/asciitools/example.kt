package com.joshskeen.asciitools

import java.net.URL

fun main(args: Array<String>) {
    val x = URL("https://avatars1.githubusercontent.com/u/230455?s=280&v=4")
            .toASCII(.3)
    println(x)
}
