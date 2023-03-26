package rocks.katiekatiekatie.clientbuilderswand.SettingsMenu;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableTextContent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import rocks.katiekatiekatie.clientbuilderswand.Util;

@OnlyIn(Dist.CLIENT)
public class SettingsScreen extends Screen {
    private static final MiningOption[] OPTION_SCREEN_OPTIONS = new MiningOption[]{MiningOption.BUILDING_RANGE, MiningOption.BLOCKS_PER_TICK};
    private final MiningOptions options;

    public SettingsScreen(MiningOptions options) {
        super(MutableText.of(new TranslatableTextContent("clientbuilderswand.options.menu")));
        this.options = options;
    }

    protected void init() {
        int i = 0;
        int widgetwidth = 300;

        for(MiningOption option : OPTION_SCREEN_OPTIONS) {
            int j = this.width / 2 - widgetwidth / 2;
            int k = this.height / 6 + i * 20 * 6 / 5;
            this.addDrawableChild(option.createButton(this.options, j, k, widgetwidth));
            ++i;
        }

        ++i;
        this.addDrawableChild(
                new ButtonWidget(
                        (this.width - widgetwidth) / 2,
                        this.height / 6 + 24 * i,
                        widgetwidth,
                        20,
                        MutableText.of(new TranslatableTextContent("clientbuilderswand.options.fuzzy")).append(Util.booleanTextComponent(MiningOptions.fuzzyMode)),
                        widget -> {
                            MiningOptions.fuzzyMode = !MiningOptions.fuzzyMode;
                            widget.setMessage(
                                    MutableText.of(new TranslatableTextContent("clientbuilderswand.options.fuzzy")).append(Util.booleanTextComponent(MiningOptions.fuzzyMode))
                            );
                        }
                )
        );

        ++i;
        this.addDrawableChild(
                new ButtonWidget(
                        (this.width - widgetwidth) / 2,
                        this.height / 6 + 24 * i,
                        widgetwidth,
                        20,
                        MutableText.of(new TranslatableTextContent("clientbuilderswand.options.fluid")).append(Util.booleanTextComponent(MiningOptions.ignoreFluid)),
                        widget -> {
                            MiningOptions.ignoreFluid = !MiningOptions.ignoreFluid;
                            widget.setMessage(
                                    MutableText.of(new TranslatableTextContent("clientbuilderswand.options.fluid")).append(Util.booleanTextComponent(MiningOptions.ignoreFluid))
                            );
                        }
                )
        );

        ++i;
        this.addDrawableChild(
                new ButtonWidget(
                        (this.width - widgetwidth) / 2,
                        this.height / 6 + 24 * i,
                        widgetwidth,
                        20,
                        MutableText.of(new TranslatableTextContent("clientbuilderswand.options.plants")).append(Util.booleanTextComponent(MiningOptions.ignorePlants)),
                        widget -> {
                            MiningOptions.ignorePlants = !MiningOptions.ignorePlants;
                            widget.setMessage(
                                    MutableText.of(new TranslatableTextContent("clientbuilderswand.options.plants")).append(Util.booleanTextComponent(MiningOptions.fuzzyMode))
                            );
                        }
                )
        );

        ++i;
        this.addDrawableChild(
                new ButtonWidget(
                        (this.width - widgetwidth) / 2,
                        this.height / 6 + 24 * i,
                        widgetwidth,
                        20,
                        MutableText.of(new TranslatableTextContent("clientbuilderswand.options.air")).append(Util.booleanTextComponent(MiningOptions.placeInAir)),
                        widget -> {
                            MiningOptions.placeInAir = !MiningOptions.placeInAir;
                            widget.setMessage(
                                    MutableText.of(new TranslatableTextContent("clientbuilderswand.options.air")).append(Util.booleanTextComponent(MiningOptions.placeInAir))
                            );
                        }
                )
        );

        ++i;
        this.addDrawableChild(
                new ButtonWidget((this.width - widgetwidth) / 2, this.height / 6 + 168, widgetwidth, 20, ScreenTexts.DONE, widget -> this.close())
        );
    }

    public void removed() {
        this.options.save();
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void close() {
        this.options.save();
        this.client.setScreen(null);
    }
}
