import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.GL11
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.util.ResourceLocation


object TESRTicTacToe : TileEntitySpecialRenderer<TileEntityTicTacToe>() {
    override fun render(te: TileEntityTicTacToe, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, alpha: Float) {
        for (slot in 0 until te.inventory.slots) {
            val stack = te.inventory.getStackInSlot(slot)
            if (!stack.isEmpty) {
                GlStateManager.enableRescaleNormal()
                GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f)
                GlStateManager.enableBlend()
                RenderHelper.enableStandardItemLighting()
                GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0)
                GlStateManager.pushMatrix()

                val cx = 0.5 + ((slot % 3 - 1) * 0.33)
                val cz = 0.5 + ((slot / 3 - 1) * 0.33)
                GlStateManager.translate(x + cx, y + 0.07, z + cz)
                GlStateManager.rotate(90f, 1f, 0f, 0f)
                GlStateManager.scale(0.2f, 0.2f, 0.5f)

                Minecraft.getMinecraft().renderItem.renderItem(stack, ItemCameraTransforms.TransformType.FIXED)

                GlStateManager.popMatrix()
                GlStateManager.disableRescaleNormal()
                GlStateManager.disableBlend()
            }
        }
    }
}