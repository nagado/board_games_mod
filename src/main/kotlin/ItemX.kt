import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

object ItemX : Item() {
    init {
        this.registryName = ResourceLocation(MODID, "tictactoex")
        this.unlocalizedName = "tactactoex"
        this.creativeTab = CreativeTabs.MISC
        this.canItemEditBlocks()
    }
}