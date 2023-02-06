
package net.mcreator.enhancing.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.Minecraft;

import net.mcreator.enhancing.world.inventory.SellMenuGUIMenu;
import net.mcreator.enhancing.network.SellMenuGUIButtonMessage;
import net.mcreator.enhancing.EnhancingMod;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;

public class SellMenuGUIScreen extends AbstractContainerScreen<SellMenuGUIMenu> {
	private final static HashMap<String, Object> guistate = SellMenuGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;

	public SellMenuGUIScreen(SellMenuGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("enhancing:textures/screens/sell_menu_gui.png");

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderTexture(0, texture);
		this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		RenderSystem.setShaderTexture(0, new ResourceLocation("enhancing:textures/screens/emerald.png"));
		this.blit(ms, this.leftPos + 97, this.topPos + 35, 0, 0, 16, 16, 16, 16);

		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
	}

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack, Component.literal("Sell Items")), 5, 6, -12829636);
		this.font.draw(poseStack, Component.literal("" + SellMenuGUIMenu.PRICE)), 114, 37, -12829636);
	}

	@Override
	public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override
	public void init() {
		super.init();
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		this.addRenderableWidget(new Button(this.leftPos + 63, this.topPos + 60, 46, 20, Component.translatable("gui.enhancing.sell_menu_gui.button_sell"), e -> {
			if (true) {
				EnhancingMod.PACKET_HANDLER.sendToServer(new SellMenuGUIButtonMessage(0, x, y, z));
				SellMenuGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}));
	}
}
