package ru.minkyyq.lobbymanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import ru.minkyyq.lobbymanager.config.ConfigManager;
import ru.minkyyq.lobbymanager.utils.HexUtil;

public class LobbyListener implements Listener {
    private final ConfigManager config;

    public LobbyListener(ConfigManager config) {
        this.config = config;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        if (!config.isUseChat()) {
            player.sendMessage(HexUtil.translate(config.getNotchat()));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (config.isJoinmsg()) {
            Bukkit.broadcastMessage(HexUtil.translate(config.getLeaveMsgChat()).replace("{player}", player.getName()));
        }
    }

    @EventHandler
    public void onVoid(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (config.isTptovoid()) {
            if (player.getLocation().getY() <= 30) {
                World world = Bukkit.getWorld(config.getSpawnWorld());
                int x = config.getSpawnX();
                int y = config.getSpawnY();
                int z = config.getSpawnZ();
                float yaw = (float) config.getSpawnYaw();
                float pitch = (float) config.getSpawnPitch();
                Location spawn = new Location(world, x + 0.5, y, z + 0.5, yaw, pitch);
                player.teleport(spawn);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setMaxHealth(player.getMaxHealth());
        if (config.isJoinmsg()) {
            Bukkit.broadcastMessage(HexUtil.translate(config.getJoinMsg()).replace("{player}", player.getName()));
        }
        if (config.isJoinmsgchat()) {
            for (String message : config.getJoinMsgChat()) {
                player.sendMessage(HexUtil.translate(message));
            }
        }
        if (config.isJointpcord()) {
            World world = Bukkit.getWorld(config.getSpawnWorld());
            int x = config.getSpawnX();
            int y = config.getSpawnY();
            int z = config.getSpawnZ();
            float yaw = (float) config.getSpawnYaw();
            float pitch = (float) config.getSpawnPitch();
            Location spawn = new Location(world, x + 0.5, y, z + 0.5, yaw, pitch);
            player.teleport(spawn);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();

        if (config.isDrop()) {
            player.sendMessage(HexUtil.translate(config.getNotDrop()));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();

        if (config.isCmduse()) {
            player.sendMessage(HexUtil.translate(config.getNotCmd()));
            e.setCancelled(true);
        }
    }
}
