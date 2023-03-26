package rocks.katiekatiekatie.clientbuilderswand.SettingsMenu;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class Config {
    private static final Builder BUILDER = new Builder();
    public static final Config.General GENERAL = new Config.General(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    public Config() {
    }

    public static class General {
        public final ConfigValue<Double> rangeConfigValue;
        public final ConfigValue<Double> blocksPerTickConfigValue;
        public final ConfigValue<Boolean> fuzzyMode;
        public final ConfigValue<Boolean> ignoreFluid;
        public final ConfigValue<Boolean> ignorePlants;
        public final ConfigValue<Boolean> placeinAir;

        public General(Builder builder) {
            builder.push("General");
            this.rangeConfigValue = builder.comment("Range (in blocks) centered on player in which blocks can be placed").translation("config.buildrange").define("buildRange", 0.0);
            this.blocksPerTickConfigValue = builder.comment("Max number of blocks that can be placed per tick").translation("config.blockspertick").define("blockspertick", 4.0);
            this.fuzzyMode = builder.comment("Allow building off of any kind of block, instead of only that of the block that you right-clicked").translation("config.fuzzy").define("fuzzy", false);
            this.ignoreFluid = builder.comment("Allow replacing fluids when building (water and lava)").translation("config.fluid").define("fluid", false);
            this.ignorePlants = builder.comment("Allow replacing plants when building (grass, Nether roots, etc.)").translation("config.plant").define("plants", false);
            this.placeinAir = builder.comment("Allow building mid-air (servers may not like this!)").translation("config.air").define("air", false);
            builder.pop();
        }
    }
}
