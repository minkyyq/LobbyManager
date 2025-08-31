package ru.minkyyq.lobbymanager.config;

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
    private String joinMsg;
    private String notchat;
    private List<String> joinMsgChat;
    private String leaveMsgChat;
    private int maxHP;

    private boolean cmduse;
    private boolean drop;
    private boolean joinmsg;
    private boolean jointpcord;
    private boolean joinmsgchat;
    private boolean useChat;
    private boolean tptovoid;

    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private double spawnYaw;
    private double spawnPitch;
    private String spawnWorld;


    private void parse() {
        notDrop = config.getString("message.notdrop", "нельзя");
        notCmd = config.getString("message.notcmd", "нельзя писать команды");
        notperm = config.getString("message.notperm", "нету прав");
        notVoid = config.getString("message.notvoid", "куда ты сьебаться захотел?");
        joinMsg = config.getString("message.joinmsg", "[+] Игрок зашел ееее");
        joinMsgChat = config.getStringList("message.joinmsgchat");
        leaveMsgChat = config.getString("message.leavemsgchat", "он ливнул");
        cfgreload = config.getString("message.reload", "перезагрузил");
        notchat = config.getString("message.notchat");
        maxHP = config.getInt("settings.maxHP");

        spawnX = config.getInt("settings.spawn.x");
        spawnY = config.getInt("settings.spawn.y");
        spawnZ = config.getInt("settings.spawn.z");
        spawnYaw = config.getDouble("settings.spawn.yaw");
        spawnPitch = config.getDouble("settings.spawn.pitch");
        spawnWorld = config.getString("settings.spawn.world");

        cmduse = config.getBoolean("settings.cmdUse");
        drop = config.getBoolean("settings.drop");
        joinmsg = config.getBoolean("settings.joinmsg");
        jointpcord = config.getBoolean("settings.jointpcord");
        joinmsgchat = config.getBoolean("settings.joinmsgchat");
        useChat = config.getBoolean("settings.useChat");
        tptovoid = config.getBoolean("settings.tptovoid");
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