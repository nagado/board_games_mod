import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemCoal
import net.minecraft.item.ItemRedstone
import net.minecraft.item.ItemEgg
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.world.IBlockAccess
import net.minecraftforge.items.CapabilityItemHandler


object BlockTicTacToe : BlockTileEntity<TileEntityTicTacToe>(Material.WOOD) {
    private val BOUNDING_BOX = AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.0625, 1.0)
    var game_grid: Array<Array<XO?>> = arrayOf(
            arrayOfNulls(3),
            arrayOfNulls(3),
            arrayOfNulls(3)
    )
    var next_sign = XO.CROSS

    init {
        this.registryName = ResourceLocation(MODID, "tictactoe")
        this.unlocalizedName = "tictactoe"
        this.setCreativeTab(CreativeTabs.DECORATIONS)
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer() = BlockRenderLayer.TRANSLUCENT
    
    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos) = BOUNDING_BOX
    override fun getTileEntityClass() = TileEntityTicTacToe::class.java
    override fun createTileEntity(world: World, state: IBlockState) = TileEntityTicTacToe()
    override fun isOpaqueCube(state: IBlockState) = false
    override fun isFullCube(state: IBlockState) = false
    private fun canBlockStay(world: World, pos: BlockPos) = !world.isAirBlock(pos.down())

    override fun canPlaceBlockAt(world: World, pos: BlockPos): Boolean {
        return super.canPlaceBlockAt(world, pos) && this.canBlockStay(world, pos)
    }

    override fun neighborChanged(state: IBlockState, world: World, pos: BlockPos, block: Block, from_pos: BlockPos) {
        if (!this.canBlockStay(world, pos)) {
            this.dropBlockAsItem(world, pos, state, 0)
            world.setBlockToAir(pos)
        }
    }

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        org.lwjgl.input.Mouse.setGrabbed(false) // TODO: Remove once done debugging

        if (world.isRemote) {
            val tile = getTileEntity(world, pos)
            var itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing) ?: return false

            val cell_x = (hitX * 2.9999).toInt()
            val cell_z = (hitZ * 2.9999).toInt()
            val slot = cell_x + 3 * cell_z  // Translate to the location in the itemStack.
            logger.info("Cell: $cell_x, $cell_z, slot: $slot")

            if (itemHandler.getStackInSlot(slot).isEmpty) {
                itemHandler.insertItem(slot, ItemStack(next_sign.item, 1), false)
                next_sign = !next_sign
                tile.markDirty()
            }

            for (slot in 0..8) {logger.info("Slot $slot: ${itemHandler.getStackInSlot(slot).unlocalizedName}")}
        }

        return true
    }
}