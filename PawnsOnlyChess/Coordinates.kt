class Coordinate(
    val firstLine: Char,
    val secondLine: Char,
    val firstPosition: Int,
    val secondPosition: Int,
    val required: String,
) {

    private constructor(builder: Builder) : this(
        firstLine = builder.firstLine,
        secondLine = builder.secondLine,
        firstPosition = builder.firstPosition,
        secondPosition = builder.secondPosition,
        required = builder.required
    )

    companion object {
        fun build(required: String) = Builder(required).build()
    }

    class Builder(
        val required: String,
    ) {
        val firstLine: Char = required[0]
        val secondLine: Char = required[2]
        val firstPosition: Int = required[1].toString().toInt()
        val secondPosition: Int = required[3].toString().toInt()

        fun build() = Coordinate(this)
    }
}
