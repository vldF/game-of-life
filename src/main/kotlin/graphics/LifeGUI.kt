package graphics

import engine.LifeEngine
import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.abs

class LifeGUI(
    private val engine: LifeEngine,
    private val frameTime: Long
) : JPanel() {
    private val frame = JFrame("The life game")
    val field = engine.state.field

    init {
        frame.extendedState = JFrame.MAXIMIZED_BOTH;
        frame.isVisible = true
        frame.contentPane.add(this)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.background = Color.BLACK
        size = frame.size
    }

    override fun paintComponent(g: Graphics) {
        val timeStart = System.currentTimeMillis()
        val stepHorizontal = frame.bounds.size.width / (field[0].size)
        val stepVertical = frame.bounds.size.height / (field.size)
        val step = minOf(stepHorizontal, stepVertical)
        val centerX = frame.bounds.size.width / 2
        val centerY = frame.bounds.size.height / 2
        val xOffset = centerX - step * field.first().size / 2
        val yOffset = centerY - step * field.size / 2

        for ((y, row) in field.withIndex()) {
            for ((x, cell) in row.withIndex()) {
                if (cell == 0) {
                    g.color = Color.BLACK
                } else {
                    g.color = Color.WHITE
                }
                g.fillRect(x * step + xOffset + 1, y * step + yOffset + 1, step - 1, step - 1)
            }
        }

        engine.doStep()
        Thread.sleep(
            abs(System.currentTimeMillis() - timeStart - frameTime)
        )
        println("frame time: ${System.currentTimeMillis() - timeStart}")
    }
}