package rocks.katiekatiekatie.clientbuilderswand;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import rocks.katiekatiekatie.clientbuilderswand.SettingsMenu.ModConfig;

@Mod("clientbuilderswand")
public class ClientBuildersWand {
    public static final String MOD_ID = "clientbuilderswand";
    public static final Logger LOGGER = LogManager.getLogger();

    public static Screen configScreen;

    public static KeyBinding toggleWand;
    public static KeyBinding openMenu;

    public ClientBuildersWand() {
        IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();

        MOD_BUS.addListener(this::onClientSetup);
        MOD_BUS.addListener(this::onRegisterKeys);

        MinecraftForge.EVENT_BUS.register(new PlaceBlockHandler());
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        MidnightConfig.init(MOD_ID, ModConfig.class);
        configScreen = MidnightConfig.getScreen(null, MOD_ID);
    }

    public void onRegisterKeys(RegisterKeyMappingsEvent event) {
        String catagoryString = "key.clientbuilderswand.category";
        String nameString = "key.clientbuilderswand.";

        toggleWand = new KeyBinding(nameString + "toggleWand", GLFW.GLFW_KEY_V, catagoryString);
        openMenu = new KeyBinding(nameString + "settings", GLFW.GLFW_KEY_I, catagoryString);

        event.register(toggleWand);
        event.register(openMenu);
    }
}
