package ru.minkyyq.lobbymanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
        if (e.getMessage() != null) {
            if (config.isUseChat()) {
                player.sendMessage(HexUtil.translate(config.getNotchat()));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (config.isJoinmsgB()) {
            Player player = e.getPlayer();
            Bukkit.broadcastMessage(HexUtil.translate(config.getLeaveMsgBC()).replace("%player%", player.getName()));
        }
    }

    @EventHandler
    public void onVoid(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (config.isTptovoid()) {
            if (player.getLocation().getY() <= -10) {
                World world = Bukkit.getWorld(config.getSpawnWorld());
                int x = config.getSpawnX();
                int y = config.getSpawnY();
                int z = config.getSpawnZ();
                float yaw = (float) config.getSpawnYaw();
                float pitch = (float) config.getSpawnPitch();
                Location spawn = new Location(world, x, y, z, yaw, pitch);
                player.teleport(spawn);
                player.setBedSpawnLocation(spawn);
                player.sendMessage(HexUtil.translate(config.getNotVoid()));
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity().getPlayer();
        World world = Bukkit.getWorld(config.getSpawnWorld());
        int x = config.getSpawnX();
        int y = config.getSpawnY();
        int z = config.getSpawnZ();
        float yaw = (float) config.getSpawnYaw();
        float pitch = (float) config.getSpawnPitch();
        Location spawn = new Location(world, x, y, z, yaw, pitch);
        player.teleport(spawn);
        player.setBedSpawnLocation(spawn);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setMaxHealth(config.getMaxHP());
        player.setHealth(config.getMaxHP());
        player.setGameMode(GameMode.valueOf(config.getGamemode()));
        if (config.isJointpcord()) {
            World world = Bukkit.getWorld(config.getSpawnWorld());
            int x = config.getSpawnX();
            int y = config.getSpawnY();
            int z = config.getSpawnZ();
            float yaw = (float) config.getSpawnYaw();
            float pitch = (float) config.getSpawnPitch();
            Location spawn = new Location(world, x, y, z, yaw, pitch);
            player.teleport(spawn);
        }
        if (config.isJoinmsgB()) {
            Bukkit.broadcastMessage(HexUtil.translate(config.getJoinMsgBC()).replace("%player%", player.getName()));
        }
        if (config.isJoinmsgchatB()) {
            for (String message : config.getJoinMsgChat()) {
                player.sendMessage(HexUtil.translate(message));
            }
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

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (config.isBlockBreak()) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (config.isBlockPlace()) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPvp(EntityDamageByEntityEvent e) {
        if (config.isPvp()) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void  onDamage(EntityDamageEvent e) {
        if (config.isPvp()) {
            e.setCancelled(true);
        }
    }

}
