package cc.unilock.clientbuilderswand;

import cc.unilock.clientbuilderswand.config.ClientBuildersWandConfig;
import cc.unilock.clientbuilderswand.mixin.ClientPlayerInteractionManagerMixin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientBuildersWand implements ClientModInitializer {
	public static final String MOD_ID = "clientbuilderswand";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final KeyBinding keybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.clientbuilderswand.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "key.clientbuilderswand.category"));
	private boolean enabled = false;

	@Override
	public void onInitializeClient() {
		ClientTickEvents.START_WORLD_TICK.register(world -> {
			if (keybind.wasPressed()) {
				enabled = !enabled;
				MinecraftClient.getInstance().player.sendMessage(Text.translatable("clientbuilderswand.toggle", enabled ? Text.translatable("clientbuilderswand.on").formatted(Formatting.GREEN) : Text.translatable("clientbuilderswand.off").formatted(Formatting.RED)), true);
			}
		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (enabled && ClientBuildersWandConfig.INSTANCE.construction.value() && world.isClient()) {
				BlockPos pos = hitResult.getBlockPos();
				Direction direction = hitResult.getSide();
				Vec3i vecX;
				Vec3i vecZ;

				if (Direction.UP.equals(direction) || Direction.DOWN.equals(direction)) {
					vecX = new Vec3i(1, 0, 0);
					vecZ = new Vec3i(0, 0, 1);
				} else if (Direction.NORTH.equals(direction) || Direction.SOUTH.equals(direction)) {
					vecX = new Vec3i(0, 1, 0);
					vecZ = new Vec3i(1, 0, 0);
				} else if (Direction.EAST.equals(direction) || Direction.WEST.equals(direction)) {
					vecX = new Vec3i(0, 1, 0);
					vecZ = new Vec3i(0, 0, 1);
				} else {
					LOGGER.error("This should never happen!!");
					return ActionResult.PASS;
				}

				final int range = ClientBuildersWandConfig.INSTANCE.range.value();

				for (int x = -range; x <= range; x++) {
					for (int z = -range; z <= range; z++) {
						final Vec3i vecX2 = vecX.multiply(x);
						final Vec3i vecZ2 = vecZ.multiply(z);

						final BlockPos pos2 = pos.add(vecX2).add(vecZ2);

						if (world.getBlockState(pos2).isAir()) {
							continue;
						}

						((ClientPlayerInteractionManagerMixin) MinecraftClient.getInstance().interactionManager).callSendSequencedPacket(
							MinecraftClient.getInstance().world,
							sequence -> new PlayerInteractBlockC2SPacket(
								hand,
								new BlockHitResult(
									new Vec3d(
										pos2.getX(),
										pos2.getY(),
										pos2.getZ()
									),
									direction,
									pos2,
									world.getBlockState(pos2).isReplaceable()
								),
								sequence
							)
						);
					}
				}
			}

            return ActionResult.PASS;
        });
	}
}
