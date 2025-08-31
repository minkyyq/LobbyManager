package ru.minkyyq.lobbymanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.minkyyq.lobbymanager.LobbyManager;
import ru.minkyyq.lobbymanager.config.ConfigManager;
import ru.minkyyq.lobbymanager.utils.HexUtil;

public class LobbyManagerCommand implements CommandExecutor {
    private final ConfigManager config;

    public LobbyManagerCommand(ConfigManager config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("lobbymanager.admin")) {
        } else {
            sender.sendMessage(HexUtil.translate(config.getNotCmd()));
        }

        if (args.length == 0) {
            sender.sendMessage("/lobbymanager reload");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                LobbyManager.getInstance().reloadConfig();
                sender.sendMessage(HexUtil.translate(config.getCfgreload()));
                break;
            default:
                break;
        }

        return true;
    }
}
