import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class TileEntityTicTacToe: TileEntity() {
    val inventory = object:ItemStackHandler(9) {
        override fun onContentsChanged(slot: Int) {
            if (!world.isRemote) {
               BoardGamesMod.network.sendToAllAround(
                       PacketUpdateTicTacToe(this@TileEntityTicTacToe),
                       NetworkRegistry.TargetPoint(world.provider.dimension, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), 64.0)
                )
            }
        }
    }

    override fun getRenderBoundingBox() = AxisAlignedBB(getPos(), getPos().add(1, 1, 1))

    override fun onLoad() {
        if (world.isRemote) {
            BoardGamesMod.network.sendToServer(PacketRequestUpdateTicTacToe(this))
        }
        super.onLoad()
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setTag("inventory", inventory.serializeNBT())
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"))
        super.readFromNBT(compound)
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing)
    }

    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) inventory as T else super.getCapability(capability, facing)
    }
}