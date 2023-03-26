package rocks.katiekatiekatie.clientbuilderswand.SettingsMenu;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.*;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class SliderOption extends MiningOption {
    protected final float steps;
    protected final double minValue;
    protected double maxValue;
    private final Function<MiningOptions, Double> getter;
    private final BiConsumer<MiningOptions, Double> setter;
    private final BiFunction<MiningOptions, SliderOption, Text> toString;
    private final Function<MinecraftClient, List<OrderedText>> tooltipSupplier;

    public SliderOption(
            String name,
            double minValue,
            double maxValue,
            float steps,
            Function<MiningOptions, Double> getter,
            BiConsumer<MiningOptions, Double> setter,
            BiFunction<MiningOptions, SliderOption, Text> toString,
            Function<MinecraftClient, List<OrderedText>> tooltipSupplier
    ) {
        super(name);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.steps = steps;
        this.getter = getter;
        this.setter = setter;
        this.toString = toString;
        this.tooltipSupplier = tooltipSupplier;
    }

    public SliderOption(
            String translationKey,
            double minValue,
            double maxValue,
            float steps,
            Function<MiningOptions, Double> getter,
            BiConsumer<MiningOptions, Double> setter,
            BiFunction<MiningOptions, SliderOption, Text> option
    ) {
        this(translationKey, minValue, maxValue, steps, getter, setter, option, sliderOption -> ImmutableList.of());
    }

    public ClickableWidget createButton(MiningOptions option, int x, int y, int width) {
        List<OrderedText> list = this.tooltipSupplier.apply(MinecraftClient.getInstance());
        return new SliderButton(option, x, y, width, 20, this, list);
    }

    public double toPercentage(double value) {
        return MathHelper.clamp((this.clamp(value) - this.minValue) / (this.maxValue - this.minValue), 0.0, 1.0);
    }

    public double toValue(double value) {
        return this.clamp(MathHelper.lerp(MathHelper.clamp(value, 0.0, 1.0), this.minValue, this.maxValue));
    }

    private double clamp(double value) {
        if (this.steps > 0.0F) {
            value = this.steps * (float)Math.round(value / (double)this.steps);
        }

        return MathHelper.clamp(value, this.minValue, this.maxValue);
    }

    public void setValue(MiningOptions option, double value) {
        this.setter.accept(option, value);
    }

    public double getValue(MiningOptions option) {
        return this.getter.apply(option);
    }

    public Text getMessage(MiningOptions option) {
        return this.toString.apply(option, this);
    }

    protected Text genericValueLabel(Text label) {
        return MutableText.of(new TranslatableTextContent("options.generic_value", this.getCaption(), label));
    }

    protected Text genericValueLabel(int value) {
        return this.genericValueLabel(MutableText.of(new LiteralTextContent(Integer.toString(value))));
    }
}
