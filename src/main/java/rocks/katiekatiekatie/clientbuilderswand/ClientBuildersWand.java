package rocks.katiekatiekatie.clientbuilderswand;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rocks.katiekatiekatie.clientbuilderswand.SettingsMenu.Config;

@Mod("clientbuilderswand")
public class ClientBuildersWand {
    public static final Logger LOGGER = LogManager.getLogger();

    public ClientBuildersWand() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.spec);
    }
}
