
import net.minecraftforge.fml.client.registry.ClientRegistry

class ClientProxy : CommonProxy() {
    override fun registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTicTacToe::class.java, TESRTicTacToe)
    }
}