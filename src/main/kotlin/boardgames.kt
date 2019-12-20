import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.ItemBlock
import net.minecraft.util.*
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.apache.logging.log4j.LogManager

const val MODID = "boardgames"
const val NAME = "Board Games"
const val VERSION = "0.1.0"

val logger = LogManager.getLogger(MODID)


@Mod(modid=MODID, name=NAME, version=VERSION, modLanguageAdapter="net.shadowfacts.forgelin.KotlinAdapter")
object BoardGamesMod{
    @Mod.EventHandler()
    fun preInit(event: FMLPreInitializationEvent) {
        logger.info("Pre-init fired for ${event.side}")

    }

    @Mod.EventHandler()
    fun init(event: FMLInitializationEvent) {
        logger.info("Init fired for ${event.side}")

    }

    @Mod.EventHandler()
    fun postInit(event: FMLPostInitializationEvent) {
        logger.info("Post-init fired for ${event.side}")
    }
}

@Mod.EventBusSubscriber(modid=MODID)
object EventHandler {
    private val tableItemBlock = ItemBlock(BlockTable)

    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        tableItemBlock.registryName = BlockTable.registryName;
        event.registry.register(tableItemBlock)
    }

    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        event.registry.register(BlockTable)
    }

    @JvmStatic
    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
        ModelLoader.setCustomModelResourceLocation(tableItemBlock, 0, ModelResourceLocation(BlockTable.registryName ?: return, "inventory"));
    }
}


object BlockTable : Block(Material.WOOD) {
    init {
        this.registryName = ResourceLocation(MODID, "table")
        this.unlocalizedName = "table"
        this.setCreativeTab(CreativeTabs.DECORATIONS)
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer() = BlockRenderLayer.SOLID
}