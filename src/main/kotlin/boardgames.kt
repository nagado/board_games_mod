import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.*
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
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
    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        event.registry.register(ItemTable)
    }

    @JvmStatic
    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
        ModelLoader.setCustomModelResourceLocation(ItemTable, 0, ModelResourceLocation(ItemTable.registryName ?: return, "inventory"))
    }
}

object ItemTable : Item() {
    init {
        this.registryName = ResourceLocation(MODID, "table")
        this.unlocalizedName = "table"
        this.creativeTab = CreativeTabs.MISC
        this.canItemEditBlocks()
    }
}