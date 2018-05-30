package net.runelite.client.plugins.custommenuswapper;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import lombok.Getter;
import net.runelite.api.*;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

import javax.inject.Inject;

@SuppressWarnings("Duplicates")
@PluginDescriptor(
        name = "Supa's Custom Menu Swapper",
        enabledByDefault = false
)
public class CustomMenuSwapperPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private CustomMenuSwapperConfig config;

    @Getter
    private boolean configuringShiftClick = false;

    @Provides
    CustomMenuSwapperConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(CustomMenuSwapperConfig.class);
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event) {
        if (client.getGameState() != GameState.LOGGED_IN) return;

        // int itemId = event.getIdentifier();
        String option = Text.removeTags(event.getOption()).toLowerCase();
        String target = Text.removeTags(event.getTarget()).toLowerCase();

        if (option.equals("wield")) {
            if (config.swapTar()) {
                if (target.contains("tar")) {
                    swap("use", option, target, true);
                } else if (target.contains("salamander")) {
                    swap("release", option, target, true);
                }
            }
        } else if (option.equals("use")) {
            if (config.dropLogs() && target.contains("logs")) {
                swap("drop", option, target, true);
            } else if (config.dropFish() && (target.contains("raw") || target.contains("leaping"))) {
                swap("drop", option, target, true);
            }
        }
    }

    // Method copied from Adam's menuentryswapper plugin.
    // Might have some changes because I removed all shift click customization and changed some other stuff.
    private int searchIndex(MenuEntry[] entries, String option, String target, boolean strict) {
        for (int i = entries.length - 1; i >= 0; i--) {
            MenuEntry entry = entries[i];
            String entryOption = Text.removeTags(entry.getOption()).toLowerCase();
            String entryTarget = Text.removeTags(entry.getTarget()).toLowerCase();

            if (strict) {
                if (entryOption.equals(option) && entryTarget.equals(target)) {
                    return i;
                }
            } else {
                if (entryOption.contains(option.toLowerCase()) && entryTarget.equals(target)) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Method copied from Adam's menuentryswapper plugin.
    // Might have some changes because I removed all shift click customization and changed some other stuff.
    private void swap(String optionA, String optionB, String target, boolean strict) {
        MenuEntry[] entries = client.getMenuEntries();

        int idxA = searchIndex(entries, optionA, target, strict);
        int idxB = searchIndex(entries, optionB, target, strict);

        if (idxA >= 0 && idxB >= 0) {
            MenuEntry entry = entries[idxA];
            entries[idxA] = entries[idxB];
            entries[idxB] = entry;

            client.setMenuEntries(entries);
        }
    }
}
