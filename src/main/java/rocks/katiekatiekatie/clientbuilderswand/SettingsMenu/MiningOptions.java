package rocks.katiekatiekatie.clientbuilderswand.SettingsMenu;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MiningOptions {
    public static Double buildingRange; // init: 0.0
    public static Double blocksPerTick; //init: 4.0
    public static boolean fuzzyMode; // init: false
    public static boolean ignoreFluid; // init: false
    public static boolean ignorePlants; // init: false
    public static boolean placeInAir; // init: false

    public MiningOptions() {
    }

    public boolean isplaceInAir() {
        return placeInAir;
    }

    public boolean isIgnoreFluid() {
        return ignoreFluid;
    }

    public boolean isIgnorePlants() {
        return ignorePlants;
    }

    public Double getBuildingRange() {
        return buildingRange;
    }

    public Double getBlocksPerTick() {
        return blocksPerTick;
    }

    public boolean isfuzzy() {
        return fuzzyMode;
    }

    public void save() {
        Config.GENERAL.rangeConfigValue.set(buildingRange);
        Config.GENERAL.blocksPerTickConfigValue.set(blocksPerTick);
        Config.GENERAL.fuzzyMode.set(fuzzyMode);
        Config.GENERAL.ignoreFluid.set(ignoreFluid);
        Config.GENERAL.ignorePlants.set(ignorePlants);
        Config.GENERAL.placeinAir.set(placeInAir);
    }

    public void load() {
        buildingRange = Config.GENERAL.rangeConfigValue.get();
        blocksPerTick = Config.GENERAL.blocksPerTickConfigValue.get();
        fuzzyMode = Config.GENERAL.fuzzyMode.get();
        ignoreFluid = Config.GENERAL.ignoreFluid.get();
        ignorePlants = Config.GENERAL.ignorePlants.get();
        placeInAir = Config.GENERAL.placeinAir.get();
    }
}
