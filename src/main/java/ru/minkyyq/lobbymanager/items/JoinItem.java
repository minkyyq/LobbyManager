package ru.minkyyq.lobbymanager.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.minkyyq.lobbymanager.LobbyManager;
import ru.minkyyq.lobbymanager.config.ConfigManager;
import ru.minkyyq.lobbymanager.utils.HexUtil;

import java.util.ArrayList;

public class JoinItem implements Listener {

    private final ConfigManager config;

    public JoinItem(ConfigManager config) {
        this.config = config;
    }

    private ItemStack itemjoin() {
        ItemStack item = new ItemStack(Material.valueOf(config.getItemMaterial()));
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(HexUtil.translate(HexUtil.translate(config.getItemName())));
        ArrayList<String> lore = new ArrayList<String>();
        for (String line : config.getItemLore()) {
            lore.add(HexUtil.translate(line));
        }
        NamespacedKey key = new NamespacedKey(LobbyManager.getInstance(), "LobbyManager");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "JoinItem");
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getItemInHand() != null) {
            if (player.getItemInHand().getItemMeta() != null) {
                if (config.isItemEnable()) {
                    NamespacedKey key = new NamespacedKey(LobbyManager.getInstance(), "LobbyManager");
                    if (player.getItemInHand().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                        // debug player.sendMessage(config.getItemCommand().replace("%player%", player.getName()));
                         Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getItemCommand().replace("%player%", player.getName()));
                    }
                }
            }
        }
    }
    @EventHandler
    public void join(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (config.isItemEnable()) {
            player.getInventory().clear();
            player.getInventory().addItem(itemjoin());
        }
    }
}
