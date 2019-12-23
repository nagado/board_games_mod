enum class XO {
    CROSS, CIRCLE;

    operator fun not() = if (this == XO.CROSS) XO.CIRCLE else XO.CROSS
}