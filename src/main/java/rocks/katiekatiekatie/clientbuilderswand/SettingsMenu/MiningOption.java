package rocks.katiekatiekatie.clientbuilderswand.SettingsMenu;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

public abstract class MiningOption {
    private final Text caption;

    public static final SliderOption BUILDING_RANGE = new SliderOption(
            "clientbuilderswand.options.range",
            0.0,
            8.0,
            1.0F,
            currentValue -> MiningOptions.buildingRange,
            (option, setValue) -> MiningOptions.buildingRange = setValue,
            (option, displayValue) -> {
                double d0 = displayValue.getValue(option);
                return displayValue.genericValueLabel((int)d0);
            }
    );

    public static final SliderOption BLOCKS_PER_TICK = new SliderOption(
            "clientbuilderswand.options.blockspertick",
            1.0,
            10.0,
            0.3F,
            currentValue -> Math.sqrt(MiningOptions.blocksPerTick),
            (option, setValue) -> MiningOptions.blocksPerTick = Math.ceil(setValue * setValue),
            (option, displayValue) -> {
                double d0 = displayValue.getValue(option);
                return displayValue.genericValueLabel((int)(d0 * d0));
            }
    );

    public MiningOption(String translationKey) {
        this.caption = MutableText.of(new TranslatableTextContent(translationKey));
    }

    public abstract ClickableWidget createButton(MiningOptions option, int x, int y, int width);

    protected Text getCaption() {
        return this.caption;
    }
}
