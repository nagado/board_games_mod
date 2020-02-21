import net.minecraft.item.Item
import net.minecraft.item.ItemCoal
import net.minecraft.item.ItemRedstone

enum class XO (val item: Item) {
    X(ItemX),
    O(ItemO);

    operator fun not() = if (this == XO.X) XO.O else XO.X
}