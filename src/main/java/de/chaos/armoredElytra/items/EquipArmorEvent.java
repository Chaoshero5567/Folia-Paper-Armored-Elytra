package de.chaos.armoredElytra.items;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.profile.PlayerProfile;
import de.chaos.armoredElytra.ArmoredElytra;
import de.chaos.armoredElytra.utils.config.ConfigObject;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class EquipArmorEvent implements Listener {
    Plugin plugin;
    ConfigObject configObject;

    public EquipArmorEvent(Plugin plugin, ConfigObject configObject) {
        this.plugin = plugin;
        this.configObject = configObject;
    }

    @EventHandler
    public void onArmorEquip(PlayerArmorChangeEvent event) {
        if (event.getSlot() == EquipmentSlot.CHEST) {
            NamespacedKey key = new NamespacedKey(plugin, ArmoredElytra.ELYTRA_ID);

            if (event.getNewItem().getPersistentDataContainer().has(key)) {
                ItemStack itemStack = event.getNewItem();
                PlayerProfile profile = Bukkit.createProfile(event.getPlayer().getUniqueId());

                ResolvableProfile resolvableProfile = ResolvableProfile.resolvableProfile()
                                .uuid(event.getPlayer().getUniqueId())
                                        .addProperties(profile.getProperties())
                                                .build();

                itemStack.setData(DataComponentTypes.PROFILE, resolvableProfile);
            }
        }
    }
}
