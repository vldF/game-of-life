import engine.LifeEngine
import graphics.LifeGUI
import java.io.File

fun main(args: Array<String>) {
    val engine = if (args[0] == "random") {
        val width = Integer.parseInt(args[2])
        val height = Integer.parseInt(args[3])

        val array = (0 until height).map { (0 until width).map { listOf(0, 1).random() }.toIntArray() }.toTypedArray()
        val engine = LifeEngine(width, height)
        engine.load(array)
        engine
    } else {
        val path = args[0]
        val fieldFile = File(path)
        val engine = LifeEngine(fieldFile.readLines().first().count { it == ',' } + 1, fieldFile.readLines().size)
        engine.load(fieldFile)
        engine
    }
    val frameTime = Integer.parseInt(args[1]).toLong()
    val gui = LifeGUI(engine, frameTime)
    while (true) {
        gui.repaint()
    }
}
