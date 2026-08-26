package de.chaos.armoredElytra.utils;

import com.google.gson.Gson;
import de.chaos.armoredElytra.utils.config.ConfigObject;
import de.chaos.armoredElytra.utils.config.DefaultConfigValues;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class Config {
    Plugin plugin;
    String fileName = "Config.json";
    ConfigObject config;
    private File file;
    private Gson gson = new Gson();


    public Config(Plugin plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder().getPath() + "//"+ fileName);
    }

    public void saveConfig(ConfigObject configObject) {


        try (FileWriter stringWriter = new FileWriter(file)) {
            if (!file.exists()) file.createNewFile();
            stringWriter.write(gson.toJson(configObject));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ConfigObject readConfig() {
        if (!file.exists()) {
            saveConfig(ConfigObject.builder()
                    .PackURL(DefaultConfigValues.PackURL)
                    .PackHash(DefaultConfigValues.PackHash)
                    .PackName(DefaultConfigValues.PackName)
                    .netherite_name(DefaultConfigValues.netherite_name)
                    .diamond_name(DefaultConfigValues.diamond_name)
                    .iron_name(DefaultConfigValues.iron_name)
                    .gold_name(DefaultConfigValues.gold_name)
                    .internal_enforce_pack(DefaultConfigValues.internal_enforce_pack)
                    .build());
        }
        try {
            ConfigObject configObject = gson.fromJson(new FileReader(file), ConfigObject.class);
            return configObject;
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return readConfig();
        }
    }

    public void loadConfigs() {
        File path = plugin.getDataFolder();
        if (!path.exists()) {
            path.mkdir();
        }

        ConfigObject configObject = this.readConfig();
        this.config = configObject;
    }
}
