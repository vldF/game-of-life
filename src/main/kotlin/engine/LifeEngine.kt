package engine

import engine.LifeState.Companion.getNeighborsCount
import java.io.File
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/*
    THREAD #    ||    Time, ms
    1           ||    40
    2           ||    35
    4           ||    32
    8           ||    28
    16          ||    35
 */
class LifeEngine(
    private val width: Int,
    private val height: Int
) {
    val state: LifeState = LifeState(width, height)

    private val threads = 8
    private val executor = Executors.newFixedThreadPool(threads)
    private val tasks = mutableListOf<Callable<Unit>>()

    init {
        repeat(threads-1) {
            val runnable = Callable {
                doStepOnChunk(state.oldField, width / threads * it, width / threads * (it+1) - 1)
            }
            tasks.add(runnable)
        }
        val runnable = Callable { doStepOnChunk(state.oldField, width / threads * (threads-1), width) }
        tasks.add(runnable)
    }

    fun load(array: Array<IntArray>) {
        array.reversed().toTypedArray().copyInto(state.field)
    }

    fun load(file: File) {
        for ((x, line) in file.readLines().withIndex()) {
            for ((y, cell) in line.replace(" ", "").split(',').withIndex()) {
                state.field[x][y] = if (cell == "1") 1 else 0
            }
        }
    }

    fun doStep() {
        state.save()
        executor.invokeAll(tasks)
    }

    private fun doStepOnChunk(field: List<IntArray>, xStart: Int, xEnd: Int) {
        for (x in xStart..xEnd) {
            for (y in 0 until height) {
                val neighbors = field.getNeighborsCount(x, y, height, width)
                if (field[y][x] == 0) {
                    if (neighbors == 3) {
                        state.field[y][x] = 1
                    }
                } else {
                    if (neighbors != 2 && neighbors != 3) {
                        state.field[y][x] = 0
                    }
                }
            }
        }
    }
}