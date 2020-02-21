import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly


object BlockTable : Block(Material.WOOD) {
    var game_grid: Array<Array<XO?>> = arrayOf(
            arrayOfNulls(3),
            arrayOfNulls(3),
            arrayOfNulls(3)
    )
    var next_sign = XO.X

    init {
        this.registryName = ResourceLocation(MODID, "table")
        this.unlocalizedName = "table"
        this.setCreativeTab(CreativeTabs.DECORATIONS)
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer() = BlockRenderLayer.SOLID
}