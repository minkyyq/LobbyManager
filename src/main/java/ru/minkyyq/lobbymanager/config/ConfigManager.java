package ru.minkyyq.lobbymanager.config;

import com.sun.org.apache.xerces.internal.xs.StringList;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Getter
public class ConfigManager {
    private final JavaPlugin plugin;
    private File file;
    private FileConfiguration config;

    public ConfigManager (JavaPlugin plugin) {
        this.plugin = plugin;
        reload();
        loadConfig();

        if (config == null) {
            throw new IllegalStateException("Ошибка парса config.yml");
        }

        parse();
    }

    private String notDrop;
    private String notCmd;
    private String cfgreload;
    private String notperm;
    private String notVoid;
    private String joinMsgBC;
    private String notchat;
    private List<String> joinMsgChat;
    private String leaveMsgBC;
    private int maxHP;
    private String gamemode;

    private boolean cmduse;
    private boolean drop;
    private boolean joinmsgB;
    private boolean jointpcord;
    private boolean joinmsgchatB;
    private boolean useChat;
    private boolean tptovoid;
    private boolean blockBreak;
    private boolean blockPlace;
    private boolean pvp;

    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private double spawnYaw;
    private double spawnPitch;
    private String spawnWorld;

    private String itemName;
    private String itemMaterial;
    private List<String> itemLore;
    private boolean glow;
    private String itemCommand;
    private boolean itemEnable;


    private void parse() {
        notDrop = config.getString("message.notdrop", "нельзя");
        notCmd = config.getString("message.notcmd", "нельзя писать команды");
        notperm = config.getString("message.notperm", "нету прав");
        notVoid = config.getString("message.notvoid", "куда ты сьебаться захотел?");
        joinMsgBC = config.getString("message.joinmsg", "[+] Игрок зашел ееее");
        joinMsgChat = config.getStringList("message.joinmsgchat");
        leaveMsgBC = config.getString("message.leavemsgchat", "он ливнул");
        cfgreload = config.getString("message.reload", "перезагрузил");
        notchat = config.getString("message.notchat");
        maxHP = config.getInt("settings.maxHP");
        gamemode = config.getString("settings.GAMEMODE");

        spawnX = config.getInt("settings.spawn.x");
        spawnY = config.getInt("settings.spawn.y");
        spawnZ = config.getInt("settings.spawn.z");
        spawnYaw = config.getDouble("settings.spawn.yaw");
        spawnPitch = config.getDouble("settings.spawn.pitch");
        spawnWorld = config.getString("settings.spawn.world");

        cmduse = config.getBoolean("settings.blockCmd");
        drop = config.getBoolean("settings.blockDrop");
        joinmsgB = config.getBoolean("settings.joinmsg");
        jointpcord = config.getBoolean("settings.jointpcord");
        joinmsgchatB = config.getBoolean("settings.joinmsgchat");
        useChat = config.getBoolean("settings.blockChat");
        tptovoid = config.getBoolean("settings.killtovoid");

        itemName = config.getString("item.name");
        itemLore = config.getStringList("item.lore");
        itemMaterial = config.getString("item.material");
        itemCommand = config.getString("item.open_command");
        glow = config.getBoolean("item.glow");
        itemEnable = config.getBoolean("item.enable");

        blockBreak = config.getBoolean("settings.blockBreak");
        blockPlace = config.getBoolean("settings.blockPlace");

        pvp = config.getBoolean("settings.blockPvp");
    }

    private void loadConfig() {
        if (file == null) return;

        config = YamlConfiguration.loadConfiguration(file);

        InputStream stream = plugin.getResource("config.yml");

        if (stream == null) return;

        Reader reader = new InputStreamReader(stream);
        FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(reader);

        config.setDefaults(defaultConfig);
    }

    private void reload() {
        if (file == null) {
            this.file = new File(plugin.getDataFolder() + "/config.yml");
        }
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
    }
}