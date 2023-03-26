package rocks.katiekatiekatie.clientbuilderswand;

import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import rocks.katiekatiekatie.clientbuilderswand.SettingsMenu.ModConfig;

import java.util.ArrayList;

@Mod.EventBusSubscriber(
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public class PlaceBlockHandler {
    private ClientPlayerEntity player = null;
    private MinecraftClient client = MinecraftClient.getInstance();
    private boolean delay = false;
    private boolean keyPressed = false;
    private int wait = 0;
    private final ArrayList<BlockHitResult> targetPos = new ArrayList<>();

    public PlaceBlockHandler() {
    }

    // TODO: don't put this in here
    //       (will likely be moved to loader-specific subproject w/ Architectury transition anyway)
    @SubscribeEvent(
            priority = EventPriority.NORMAL,
            receiveCanceled = true
    )
    public void onEvent(InputEvent.Key event) {
        MutableText on = MutableText.of(new TranslatableTextContent("clientbuilderswand.on"));
        on.setStyle(Style.EMPTY.withFormatting(Formatting.DARK_GREEN));

        MutableText off = MutableText.of(new TranslatableTextContent("clientbuilderswand.off"));
        off.setStyle(Style.EMPTY.withFormatting(Formatting.DARK_RED));

        this.player = this.client.player;

        if (event.getKey() != GLFW.GLFW_KEY_ESCAPE) {
            if (ClientBuildersWand.openMenu.isPressed()) {
                this.client.setScreen(ClientBuildersWand.configScreen);
            }

            if (ClientBuildersWand.toggleWand.isPressed()) {
                this.keyPressed = !this.keyPressed;
                this.player.sendMessage(MutableText.of(new TranslatableTextContent("clientbuilderswand.wand")).append(this.keyPressed ? on : off), true);
            }
        }
    }

    @SubscribeEvent
    public void placeBlock(PlayerInteractEvent.RightClickBlock event) {
        if (this.keyPressed && event.getEntity() instanceof ClientPlayerEntity && !this.delay) {
            this.delay = true;
            this.player = (ClientPlayerEntity)event.getEntity();
            this.client = MinecraftClient.getInstance();

            Direction placeDir = event.getFace();

            MutableText message = MutableText.of(new LiteralTextContent(placeDir.getName()));
            message.setStyle(Style.EMPTY.withFormatting(Formatting.BOLD));

            ArrayList<Direction> directions = new ArrayList<>();

            for(Direction direction : Direction.values()) {
                if (direction.getId() != placeDir.getId()
                        && direction.getOpposite() != placeDir
                        && !directions.contains(direction)
                        && !directions.contains(direction.getOpposite())) {
                    directions.add(direction);
                }
            }

            Vec3d pos = event.getHitVec().getPos();

            BlockPos blockPos = event.getPos();
            BlockState blockState = this.client.world.getBlockState(blockPos);
            BlockHitResult blockHitResult = new BlockHitResult(pos, placeDir, blockPos, false);

            ActionResult interactionResult = blockState.onUse(
                    this.client.world,
                    this.player,
                    Hand.MAIN_HAND,
                    blockHitResult
            );

            Block block = blockState.getBlock();

            // TODO: rename this field to something that makes sense!!
            boolean flag = !(block instanceof BlockWithEntity)
                        && !(block instanceof DoorBlock)
                        && interactionResult == ActionResult.PASS;

            if (
               blockState.getMaterial()!= Material.REPLACEABLE_PLANT
            && blockState.getMaterial() != Material.NETHER_SHOOTS
            ) {
                blockPos = blockPos.add(placeDir.getVector());
            }

            if (flag) {
                int range = ModConfig.buildingRange;

                for(int lauf1 = -range; lauf1 <= range; ++lauf1) {
                    for(int lauf2 = -range; lauf2 <= range; ++lauf2) {
                        if (lauf1 != 0 || lauf2 != 0) {
                            flag = false;

                            BlockPos placePos = blockPos
                                    .add(directions.get(0).getVector().multiply(lauf1))
                                    .add(directions.get(1).getVector().multiply(lauf2));

                            BlockState placeBlock = this.client.world.getBlockState(placePos);

                            if (
                               placeBlock.getMaterial() != Material.AIR
                            && placeBlock.getMaterial() != Material.STRUCTURE_VOID
                            && placeBlock.getMaterial() != Material.FIRE
                            && placeBlock.getMaterial() != Material.SNOW_LAYER
                            ) {
                                if (
                                (
                                    placeBlock.getMaterial() == Material.REPLACEABLE_PLANT
                                    || placeBlock.getMaterial() == Material.NETHER_SHOOTS
                                )
                                && ModConfig.ignorePlants
                                ) {
                                    flag = true;
                                } else if (
                                (
                                    placeBlock.getMaterial() == Material.WATER
                                    || placeBlock.getMaterial() == Material.BUBBLE_COLUMN
                                    || placeBlock.getMaterial() == Material.LAVA
                                )
                                && ModConfig.ignoreFluid
                                ) {
                                    flag = true;
                                }
                            } else {
                                flag = true;
                            }

                            BlockState compareBlock = this.client.world.getBlockState(placePos.add(placeDir.getOpposite().getVector()));

                            if (compareBlock.getBlock() != block && !ModConfig.fuzzyMode) {
                                flag = false;
                            }

                            if (compareBlock.getMaterial().isReplaceable() && !ModConfig.placeinAir) {
                                flag = false;
                            }

                            if (flag && !event.getItemStack().isEmpty()) {
                                pos = event.getHitVec().getPos();
                                pos = pos.subtract(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
                                pos = new Vec3d(placePos.getX(), placePos.getY(), placePos.getZ()).add(pos);
                                this.targetPos.add(new BlockHitResult(pos, placeDir, placePos, false));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (this.wait > 0) {
            --this.wait;
        } else {
            int counter = 0;

            int max = ModConfig.blocksPerTick;

            while(!this.targetPos.isEmpty() && counter < max) {
                this.wait = 1;
                ++counter;

                BlockHitResult blockHitResult = this.targetPos.get(0);

                this.client.interactionManager.interactBlock(this.player, Hand.MAIN_HAND, blockHitResult);

                this.targetPos.remove(0);
            }

            if (this.targetPos.isEmpty()) {
                this.delay = false;
            }
        }
    }
}
