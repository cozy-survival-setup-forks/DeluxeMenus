package com.extendedclip.deluxemenus.utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Text that mixes MiniMessage tags (&lt;gradient:red:blue&gt;, &lt;#ff8800&gt;, &lt;bold&gt;) with the old
 * &amp; codes, turned into the section-symbol text that item names, lore and inventory titles take.
 */
public final class ColorParser {

    // something that looks like a MiniMessage tag, so plain text with a stray < is left alone
    private static final Pattern TAG = Pattern.compile("<[/!?#a-zA-Z_][^<>]*>");
    private static final Pattern HEX = Pattern.compile("(?i)&#([0-9a-f]{6})");
    private static final Pattern CODE = Pattern.compile("(?i)&([0-9a-fk-or])");
    private static final String[] NAMES = new String[128];
    private static final MiniMessage MINI = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.builder()
            .character('§').hexColors().useUnusualXRepeatedCharacterHexFormat().build();

    static {
        String[] colors = {"black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "gray",
                "dark_gray", "blue", "green", "aqua", "red", "light_purple", "yellow", "white"};
        for (int i = 0; i < colors.length; i++) {
            // a colour code also clears bold, italic and the rest, as it does in the old system
            NAMES["0123456789abcdef".charAt(i)] = "<reset><" + colors[i] + ">";
        }
        NAMES['k'] = "<obfuscated>";
        NAMES['l'] = "<bold>";
        NAMES['m'] = "<strikethrough>";
        NAMES['n'] = "<underlined>";
        NAMES['o'] = "<italic>";
        NAMES['r'] = "<reset>";
    }

    private ColorParser() {
    }

    /**
     * @return true if the text has something that looks like a MiniMessage tag
     */
    public static boolean hasTags(final @NotNull String input) {
        return input.indexOf('<') >= 0 && TAG.matcher(input).find();
    }

    /**
     * @param input text with MiniMessage tags and/or &amp; codes
     * @return the same text with section-symbol codes, hex colours included
     */
    public static @NotNull String toLegacy(final @NotNull String input) {
        String text = input.indexOf('§') >= 0 ? input.replace('§', '&') : input;
        text = HEX.matcher(text).replaceAll("<reset><#$1>");

        final Matcher codes = CODE.matcher(text);
        final StringBuilder out = new StringBuilder(text.length() + 16);
        while (codes.find()) {
            codes.appendReplacement(out, Matcher.quoteReplacement(NAMES[Character.toLowerCase(codes.group(1).charAt(0))]));
        }
        codes.appendTail(out);

        return LEGACY.serialize(MINI.deserialize(out.toString()));
    }
}
