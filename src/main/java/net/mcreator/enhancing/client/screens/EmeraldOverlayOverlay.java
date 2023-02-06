package net.mcreator.enhancing.client.screens;
import net.mcreator.enhancing.PlayerStatusManager;

import org.checkerframework.checker.units.qual.h;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class EmeraldOverlayOverlay
{
	static int EMERALD_ICON_PADDING_LEFT = 16;
	static int EMERALD_ICON_PADDING_TOP = 16;

	static int WIDTH;
	static int HEIGHT;
	static int POSX;
	static int POSY;
	static Level WORLD;
	static Player PLAYER;
	static double X;
	static double Y;
	static double Z;

	static RenderGuiEvent.Pre event;
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre _event)
	{
		event = _event;
		
		init();
		startRender();
	}

	private static void init()
	{
		WIDTH = event.getWindow().getGuiScaledWidth();
		HEIGHT = event.getWindow().getGuiScaledHeight();
		
		POSX = WIDTH / 2;
		POSY = HEIGHT / 2;
		
		PLAYER = Minecraft.getInstance().player;
		
		if (PLAYER != null)
		{
			WORLD = PLAYER.level;
			X = PLAYER.getX();
			Y = PLAYER.getY();
			Z = PLAYER.getZ();
		}
	}

	private static void startRender()
	{
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		
		renderEmeraldIcon();
		renderEmeraldCountLabel();
		
		RenderSystem.depthMask(true);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	private static void renderEmeraldIcon()
	{
		RenderSystem.setShaderTexture(0, new ResourceLocation("enhancing:textures/screens/emerald.png"));
		Minecraft.getInstance().gui.blit(
			event.getPoseStack(),
			EMERALD_ICON_PADDING_TOP,
			EMERALD_ICON_PADDING_LEFT,
			0,
			0,
			16,
			16,
			16,
			16
		);
	}

	private static void renderEmeraldCountLabel()
	{
		Minecraft.getInstance().font.draw(
			event.getPoseStack(),
			Component.literal("" + PlayerStatusManager.getEmeraldCount()),
			EMERALD_ICON_PADDING_LEFT + 20,
			EMERALD_ICON_PADDING_TOP + 4,
			-1
		);
	}
}
