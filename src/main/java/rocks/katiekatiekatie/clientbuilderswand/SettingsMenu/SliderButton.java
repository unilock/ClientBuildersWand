package rocks.katiekatiekatie.clientbuilderswand.SettingsMenu;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.OrderableTooltip;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SliderButton extends SliderWidget implements OrderableTooltip {
    private final SliderOption option;
    private final MiningOptions options;
    private final List<OrderedText> tooltip;

    public SliderButton(MiningOptions options, int x, int y, int width, int height, SliderOption option, List<OrderedText> tooltip) {
        super(x, y, width, height, MutableText.of(LiteralTextContent.EMPTY), (float)option.toPercentage(option.getValue(options)));
        this.option = option;
        this.options = options;
        this.tooltip = tooltip;
        this.updateMessage();
    }

    protected void applyValue() {
        this.option.setValue(this.options, this.option.toValue(this.value));
    }

    protected void updateMessage() {
        this.setMessage(this.option.getMessage(this.options));
    }

    public List<OrderedText> getOrderedTooltip() {
        return this.tooltip;
    }
}
