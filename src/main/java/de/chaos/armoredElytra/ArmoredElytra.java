package de.chaos.armoredElytra;

import de.chaos.armoredElytra.items.ElytraLogic;
import de.chaos.armoredElytra.items.EquipArmorEvent;
import de.chaos.armoredElytra.listeners.JoinEvent;
import de.chaos.armoredElytra.utils.Config;
import de.chaos.armoredElytra.utils.TexturePack;
import de.chaos.armoredElytra.utils.config.ConfigObject;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmoredElytra extends JavaPlugin {

    private Plugin plugin;
    private Config config;
    private TexturePack texturePack;
    private ConfigObject configObject;
    public static String ELYTRA_ID = "ThisIsAnElytra";

    @Override
    public void onEnable() {
        plugin = this;

        config = new Config(this);
        config.loadConfigs();

        configObject = config.readConfig();

        texturePack = new TexturePack(configObject);

        registerEvent(new JoinEvent(texturePack));
        registerEvent(new ElytraLogic(plugin, configObject));
        registerEvent(new EquipArmorEvent(plugin, configObject));


        // Plugin startup logic

    }

    public void registerEvent(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void renewConfig() {
        this.configObject = config.readConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
