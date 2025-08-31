package ru.minkyyq.lobbymanager.other;

import org.bukkit.event.Listener;
import ru.minkyyq.lobbymanager.config.ConfigManager;

public class ActionBar implements Listener {

    private final ConfigManager config;

    public ActionBar(ConfigManager config) {
        this.config = config;
    }
}
