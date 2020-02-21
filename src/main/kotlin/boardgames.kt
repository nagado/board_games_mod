import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import org.apache.logging.log4j.LogManager
import net.minecraftforge.fml.common.SidedProxy


const val MODID = "boardgames"
const val NAME = "Board Games"
const val VERSION = "0.1.0"

val logger = LogManager.getLogger(MODID)


@Mod(modid=MODID, name=NAME, version=VERSION, modLanguageAdapter="net.shadowfacts.forgelin.KotlinAdapter")
object BoardGamesMod{

    val network: SimpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MODID)

    @SidedProxy(serverSide = "CommonProxy", clientSide = "ClientProxy")
    var proxy: CommonProxy? = null

    @Mod.EventHandler()
    fun preInit(event: FMLPreInitializationEvent) {
        network.registerMessage(PacketUpdateTicTacToe.Handler(), PacketUpdateTicTacToe::class.java, 0, Side.CLIENT)
        network.registerMessage(PacketRequestUpdateTicTacToe.Handler(), PacketRequestUpdateTicTacToe::class.java, 1, Side.SERVER);
        proxy!!.registerRenderers()
    }

    @Mod.EventHandler()
    fun init(event: FMLInitializationEvent) {}

    @Mod.EventHandler()
    fun postInit(event: FMLPostInitializationEvent) {}
}

@Mod.EventBusSubscriber(modid=MODID)
object EventHandler {
    private val tableItemBlock = ItemBlock(BlockTable)
    private val tictactoeItemBlock = ItemBlock(BlockTicTacToe)

    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        tableItemBlock.registryName = BlockTable.registryName
        tictactoeItemBlock.registryName = BlockTicTacToe.registryName

        event.registry.register(tableItemBlock)
        event.registry.register(tictactoeItemBlock)
        event.registry.register(ItemX)
        event.registry.register(ItemO)
    }

    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        event.registry.register(BlockTable)
        event.registry.register(BlockTicTacToe)

        GameRegistry.registerTileEntity(TileEntityTicTacToe::class.java, "boardgames:tictactoe_entity")
    }

    @JvmStatic
    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
        ModelLoader.setCustomModelResourceLocation(tableItemBlock, 0, ModelResourceLocation(BlockTable.registryName ?: return, "inventory"))
        ModelLoader.setCustomModelResourceLocation(tictactoeItemBlock, 0, ModelResourceLocation(BlockTicTacToe.registryName ?: return, "inventory"))
        ModelLoader.setCustomModelResourceLocation(ItemX, 0, ModelResourceLocation(ItemX.registryName ?: return, "inventory"))
        ModelLoader.setCustomModelResourceLocation(ItemO, 0, ModelResourceLocation(ItemO.registryName ?: return, "inventory"))
    }
}