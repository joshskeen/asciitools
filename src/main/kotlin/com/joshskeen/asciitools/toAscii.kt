package com.joshskeen.asciitools

import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

fun ByteArray.toASCII(scaleFactor: Double = 1.0) = this.toBufferedImage().toASCII(scaleFactor)
fun Image.toASCII(scaleFactor: Double = 1.0) = this.toBufferedImage().toASCII(scaleFactor)
fun BufferedImage.toASCII(scaleFactor: Double = 1.0) = this.scaled(scaleFactor).convert()
fun URL.toASCII(scaleFactor: Double = 1.0) = this.readBytes().toASCII(scaleFactor)


private fun ByteArray.toBufferedImage(): BufferedImage = ImageIO.read(this.inputStream())

private fun Image.toBufferedImage(): BufferedImage {
    val bufferedImage = BufferedImage(this.getWidth(null),
            this.getHeight(null), BufferedImage.TYPE_INT_ARGB)
    val graphics = bufferedImage.createGraphics()
    graphics.drawImage(this, 0, 0, null)
    graphics.dispose()
    return bufferedImage
}

private fun BufferedImage.scaled(scaleFactor: Double): BufferedImage {
    return if (scaleFactor != 1.0) {
        val newWidth = (this.width * scaleFactor).toInt()
        val newHeight = (this.height * scaleFactor).toInt()
        val scaled = this.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT)
        scaled.toBufferedImage()
    } else {
        this
    }
}

private fun BufferedImage.convert(): String {
    var out = ""
    for (y in 0 until this.height) {
        (0 until this.width)
                .map { Color(this.getRGB(it, y)) }
                .map { (((it.red * 0.30) + (it.blue * 0.59) + (it.green * 0.11))) }
                .forEach { out += colorToChar(it) }
        out += "\n"
    }
    return out
}

private fun colorToChar(g: Double) = when {
    g >= 240 -> ' '
    g >= 210 -> '.'
    g >= 190 -> '*'
    g >= 170 -> '+'
    g >= 120 -> '^'
    g >= 110 -> '&'
    g >= 80 -> '8'
    g >= 60 -> '#'
    else -> '@'
}

