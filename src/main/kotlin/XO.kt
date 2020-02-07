import net.minecraft.item.Item
import net.minecraft.item.ItemCoal
import net.minecraft.item.ItemRedstone

enum class XO (val item: Item) {
    CROSS(ItemCoal()),
    CIRCLE(ItemRedstone());

    operator fun not() = if (this == XO.CROSS) XO.CIRCLE else XO.CROSS
}