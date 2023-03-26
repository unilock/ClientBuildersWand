package rocks.katiekatiekatie.clientbuilderswand;

import net.minecraft.client.option.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import rocks.katiekatiekatie.clientbuilderswand.SettingsMenu.MiningOptions;

@Mod.EventBusSubscriber(
        modid = "clientbuilderswand",
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = {Dist.CLIENT}
)
public class ClientProxy {
    public static KeyBinding toggleWand;
    public static KeyBinding openMenu;
    public static MiningOptions miningOptions;

    public ClientProxy() {
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        String catagoryString = "key.clientbuilderswand.category";
        String nameString = "key.clientbuilderswand.";

        toggleWand = new KeyBinding(nameString + "toggleWand", GLFW.GLFW_KEY_V, catagoryString);
        openMenu = new KeyBinding(nameString + "settings", GLFW.GLFW_KEY_I, catagoryString);

        event.register(toggleWand);
        event.register(openMenu);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        miningOptions = new MiningOptions();
        miningOptions.load();

        MinecraftForge.EVENT_BUS.register(new PlaceBlockHandler());
    }
}
