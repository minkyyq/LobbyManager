package ru.minkyyq.lobbymanager;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.minkyyq.lobbymanager.commands.LobbyManagerCommand;
import ru.minkyyq.lobbymanager.config.ConfigManager;
import ru.minkyyq.lobbymanager.listeners.*;
import ru.minkyyq.lobbymanager.other.ActionBar;
import ru.minkyyq.lobbymanager.other.BossBar;
import ru.minkyyq.lobbymanager.utils.HexUtil;

public final class LobbyManager extends JavaPlugin {
    @Getter
    private static LobbyManager instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigManager config = new ConfigManager(this);
        getLogger().info(HexUtil.translate("&aLobbyManager включен"));
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new ActionBar(config), this);
        manager.registerEvents(new LobbyListener(config), this);

        this.getCommand("lobbymanager").setExecutor(new LobbyManagerCommand(config));
    }

    @Override
    public void onDisable() {
        getLogger().info(HexUtil.translate("&cLobbyManager выключен"));
    }

}
