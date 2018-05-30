package net.runelite.client.plugins.custommenuswapper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(
        keyName = "custommenuentryswapper",
        name = "Supa's Custom Menu Swapper",
        description = "Some custom options to swap entries"
)
public interface CustomMenuSwapperConfig extends Config  {
    @ConfigItem(
            position = 1,
            keyName = "swapTar",
            name = "Swap tar",
            description = "Swap tar option wield -> use and salamander wield -> release for Hunter."
    )
    default boolean swapTar() {
        return false;
    }

    @ConfigItem(
            position = 2,
            keyName = "woodDrop",
            name = "Drop wood",
            description = "Left click dropping logs when woodcutting."
    )
    default boolean dropLogs() {
        return false;
    }

    @ConfigItem(
            position = 3,
            keyName = "fishDrop",
            name = "Drop fish",
            description = "Left click dropping fish when fishing."
    )
    default boolean dropFish() {
        return false;
    }
}
