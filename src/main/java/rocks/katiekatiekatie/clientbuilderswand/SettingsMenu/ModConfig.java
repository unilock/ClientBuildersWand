package rocks.katiekatiekatie.clientbuilderswand.SettingsMenu;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
    @Comment public static Comment buildingRangeComment;
    @Entry(isSlider = true, min = 0, max = 8) public static int buildingRange = 0;

    @Comment public static Comment blocksPerTickComment;
    @Entry(isSlider = true, min = 1, max = 100) public static int blocksPerTick = 4;

    @Comment public static Comment fuzzyModeComment;
    @Entry public static boolean fuzzyMode = false;

    @Comment public static Comment ignoreFluidComment;
    @Entry public static boolean ignoreFluid = false;

    @Comment public static Comment ignorePlantsComment;
    @Entry public static boolean ignorePlants = false;

    @Comment public static Comment placeinAirComment;
    @Entry public static boolean placeinAir = false;
}
