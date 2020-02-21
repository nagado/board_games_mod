import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

object ItemO : Item() {
    init {
        this.registryName = ResourceLocation(MODID, "tictactoeo")
        this.unlocalizedName = "tactactoeo"
        this.creativeTab = CreativeTabs.MISC
        this.canItemEditBlocks()
    }
}