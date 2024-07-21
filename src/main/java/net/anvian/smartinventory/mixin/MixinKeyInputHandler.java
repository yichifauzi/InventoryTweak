package net.anvian.smartinventory.mixin;

import net.anvian.smartinventory.handler.ModKeyBinding;
import net.anvian.smartinventory.sort.SortInventory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public class MixinKeyInputHandler {
    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode == ModKeyBinding.keyBinding.getDefaultKey().getCode()) {
            ScreenHandler screenHandler = MinecraftClient.getInstance().player.currentScreenHandler;
            Screen screen = MinecraftClient.getInstance().currentScreen;

            if (screen instanceof InventoryScreen || screen instanceof CreativeInventoryScreen) {
                SortInventory.sortPlayerInventory(screenHandler);
            } else {
                SortInventory.sortContainerInventory(screenHandler);
            }
        }
    }
}
