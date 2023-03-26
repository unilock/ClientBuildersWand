package rocks.katiekatiekatie.clientbuilderswand;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;

public class Util {
    public static MinecraftClient client = MinecraftClient.getInstance();

    public Util() {
    }

    public static MutableText booleanTextComponent(boolean value) {
        MutableText textComponent = MutableText.of(new TranslatableTextContent("clientbuilderswand." + value));
        if (value) {
            textComponent.setStyle(Style.EMPTY.withFormatting(Formatting.DARK_GREEN));
        } else {
            textComponent.setStyle(Style.EMPTY.withFormatting(Formatting.DARK_RED));
        }

        return textComponent;
    }
}
