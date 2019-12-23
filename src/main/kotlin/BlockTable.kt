import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly


object BlockTable : Block(Material.WOOD) {
    var game_grid: Array<Array<XO?>> = arrayOf(
            arrayOfNulls(3),
            arrayOfNulls(3),
            arrayOfNulls(3)
    )
    var next_sign = XO.CROSS

    init {
        this.registryName = ResourceLocation(MODID, "table")
        this.unlocalizedName = "table"
        this.setCreativeTab(CreativeTabs.DECORATIONS)
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer() = BlockRenderLayer.SOLID

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        org.lwjgl.input.Mouse.setGrabbed(false) // TODO: Remove once done debugging

        if (world.isRemote && hitY.toInt() == 1) {
            val cell_x = (hitX * 2.9999).toInt()
            val cell_z = (hitZ * 2.9999).toInt()
            logger.info("Coords: $hitX, $hitY, $hitZ")
            logger.info("Cell: $cell_x, $cell_z")

            if( game_grid[cell_z][cell_x] == null) {
                game_grid[cell_z][cell_x] = next_sign
                next_sign = !next_sign
            }

            for (row in game_grid) {logger.info("${row[0]}, ${row[1]}, ${row[2]}")}
        }

        return true
    }
}