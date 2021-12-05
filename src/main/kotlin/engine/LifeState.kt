package engine

class LifeState(
    val width: Int,
    val height: Int
) {
    val field: Array<IntArray> = Array(height) { IntArray(width) { 0 } }
    lateinit var oldField: List<IntArray>

    fun save() {
        oldField = field.map { it.clone() }
    }

    companion object {
        fun List<IntArray>.getNeighborsCount(x: Int, y: Int, height: Int, width: Int): Int {
            return  this[y][(x - 1).mod(width)] +
                    this[y][(x + 1).mod(width)] +
                    this[(y - 1).mod(height)][x] +
                    this[(y + 1).mod(height)][x] +
                    this[(y - 1).mod(height)][(x + 1).mod(width)] +
                    this[(y - 1).mod(height)][(x - 1).mod(width)] +
                    this[(y + 1).mod(height)][(x + 1).mod(width)] +
                    this[(y + 1).mod(height)][(x - 1).mod(width)]
        }
    }
}