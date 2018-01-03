package net.silentchaos512.extendeddays.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.extendeddays.ExtendedDays;
import net.silentchaos512.extendeddays.event.TimeEvents;

public class ClockHud extends Gui {

  public static final ClockHud INSTANCE = new ClockHud();

  public static final ResourceLocation TEXTURE = new ResourceLocation(ExtendedDays.MOD_ID,
      "textures/gui/hud.png");

  @SubscribeEvent
  public void onRenderOverlay(RenderGameOverlayEvent.Post event) {

    if (event.getType() != ElementType.TEXT)
      return;

    Minecraft mc = Minecraft.getMinecraft();
    EntityPlayer player = mc.player;
    World world = player.world;

    int width = event.getResolution().getScaledWidth();
    int height = event.getResolution().getScaledHeight();

    // TODO: Conditions to show clock
    // Should check if player can see the sky, but not every render tick!
    // Maybe every few seconds? Also consider a pocketwatch item (with Baubles
    // compat) that allows player to see time always.
    if (player.posY > 56) {
      renderClock(mc, world, width, height);
    }
  }

  public void renderClock(Minecraft mc, World world, int screenWidth, int screenHeight) {

    GlStateManager.enableBlend();

    mc.renderEngine.bindTexture(TEXTURE);

    int posX = 5; // TODO: Config
    if (posX < 0)
      posX = posX + screenWidth - 80;
    int posY = 25; // TODO: Config
    if (posY < 0)
      posY = posY + screenHeight - 12;

    long worldTime = world.getWorldTime() % 24000;
    boolean isNight = worldTime > 12000;

    // Main bar
    int texX = 0;
    int texY = isNight ? 12 : 0;
    drawTexturedModalRect(posX, posY, texX, texY, 80, 12, 0xFFFFFF);

    // Extended period markers
    // TODO

    // Sun/Moon
    texX = 84;
    int dayLength = isNight ? TimeEvents.INSTANCE.getNighttimeLength()
        : TimeEvents.INSTANCE.getDaytimeLength();
    int currentTime = TimeEvents.INSTANCE.getCurrentTime(world);
    if (isNight)
      currentTime -= TimeEvents.INSTANCE.getDaytimeLength();
    int x = 2 + (int) (posX + 78 * ((float) currentTime) / dayLength) - 6;
    drawTexturedModalRect(x, posY, texX, texY, 12, 12, 0xFFFFFF);
  }

  protected void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width,
      int height, int color) {

    float r = ((color >> 16) & 255) / 255f;
    float g = ((color >> 8) & 255) / 255f;
    float b = (color & 255) / 255f;
    GlStateManager.color(r, g, b);
    drawTexturedModalRect(x, y, textureX, textureY, width, height);
    GlStateManager.color(1f, 1f, 1f);
  }
}
