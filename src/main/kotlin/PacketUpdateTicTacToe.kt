import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext


class PacketUpdateTicTacToe : IMessage {
    private lateinit var pos: BlockPos
    private lateinit var stack: ItemStack

    constructor(pos:BlockPos, stack:ItemStack) {
        this.pos = pos
        this.stack = stack
    }
    constructor(te:TileEntityTicTacToe) : this(te.getPos(), te.inventory.getStackInSlot(0)) {}
    constructor() {}

    override fun toBytes(buf: ByteBuf?) {
        if (buf != null) {
            buf.writeLong(pos.toLong())
            ByteBufUtils.writeItemStack(buf, stack)
        }
    }

    override fun fromBytes(buf: ByteBuf?) {
        if (buf != null) {
            this.pos = BlockPos.fromLong(buf.readLong())
            this.stack = ByteBufUtils.readItemStack((buf))
        }
    }

    class Handler: IMessageHandler<PacketUpdateTicTacToe, IMessage> {
        override fun onMessage(message: PacketUpdateTicTacToe, ctx: MessageContext): IMessage? {
            Minecraft.getMinecraft().addScheduledTask {
                val te = Minecraft.getMinecraft().world.getTileEntity(message.pos) as TileEntityTicTacToe?
                te?.inventory?.setStackInSlot(0, message.stack)
            }
            return null
        }
    }
}