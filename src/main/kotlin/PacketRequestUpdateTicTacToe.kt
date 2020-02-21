import io.netty.buffer.ByteBuf
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import java.awt.Dimension

class PacketRequestUpdateTicTacToe : IMessage {
    private lateinit var pos: BlockPos
    private var dimension: Int = 0

    constructor(pos: BlockPos, dimension: Int) {
        this.pos = pos
        this.dimension = dimension
    }

    constructor(te: TileEntityTicTacToe) : this(te.pos, te.world.provider.dimension)
    constructor() {}

    override fun toBytes(buf: ByteBuf) {
        buf.writeLong(pos.toLong())
        buf.writeInt(dimension)
    }

    override fun fromBytes(buf: ByteBuf) {
        this.pos = BlockPos.fromLong(buf.readLong())
        this.dimension = buf.readInt()
    }

    class Handler: IMessageHandler<PacketRequestUpdateTicTacToe, PacketUpdateTicTacToe> {
        override fun onMessage(message: PacketRequestUpdateTicTacToe, ctx: MessageContext) : PacketUpdateTicTacToe? {
            val world = FMLCommonHandler.instance().minecraftServerInstance.getWorld(message.dimension)
            val te = world.getTileEntity(message.pos) as TileEntityTicTacToe?
            if (te != null) {
                return PacketUpdateTicTacToe(te)
            }

            return null
        }
    }




}