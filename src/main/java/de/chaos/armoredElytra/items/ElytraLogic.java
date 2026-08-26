package de.chaos.armoredElytra.items;

import de.chaos.armoredElytra.ArmoredElytra;
import de.chaos.armoredElytra.utils.config.ConfigObject;
import de.chaos.armoredElytra.utils.item.CustomItem;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import io.papermc.paper.datacomponent.item.Equippable;
import io.papermc.paper.datacomponent.item.ItemArmorTrim;
import io.papermc.paper.datacomponent.item.ItemEnchantments;
import io.papermc.paper.registry.keys.SoundEventKeys;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;


public class ElytraLogic implements Listener {
    public Plugin plugin;
    public ConfigObject configObject;
    @Getter
    TextComponent netherite_display = Component.text("Armored_Netherite_Elytra");
    TextComponent diamond_display = Component.text("Armored_Diamond_Elytra");
    TextComponent iron_display = Component.text("Armored_Iron_Elytra");
    TextComponent gold_display = Component.text("Armored_golden_Elytra");


    public ElytraLogic(Plugin plugin, ConfigObject configObject) {
        this.plugin = plugin;
        this.configObject = configObject;
        Bukkit.addRecipe(this.getSmithingRecipie());

    }




    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory inventory = event.getInventory();
        ItemStack plate;
        ItemStack elytra;


        if ((inventory.getSecondItem() != null ) && (inventory.getFirstItem() != null)) {
            plate = inventory.getFirstItem();
            elytra = inventory.getSecondItem();
            if (elytra.getType().equals(Material.ELYTRA)) {

                Map<Enchantment, Integer> enchantElytra = elytra.getEnchantments();

                if (plate.getType().name().contains("_CHESTPLATE")) {

                    // gets pre crafted Item
                    CustomItem customItem = addCustomElytra(plate.getType());

                    // Create modified result item
                    ItemStack result = customItem.getStack();

                    // Enchants
                    Map<Enchantment, Integer> enchantPlate = plate.getEnchantments();

                    Map<Enchantment, Integer> finalEnchantMap = new HashMap<Enchantment, Integer>();

                    for (Enchantment enchantment : enchantElytra.keySet()) {
                        finalEnchantMap.put(enchantment, enchantElytra.get(enchantment));
                    }
                    for (Enchantment enchantment : enchantPlate.keySet()) {
                        finalEnchantMap.put(enchantment, enchantPlate.get(enchantment));
                    }

                    ItemEnchantments itemEnchantments = ItemEnchantments.itemEnchantments(finalEnchantMap);

                    // Set Enchants
                    result.setData(DataComponentTypes.ENCHANTMENTS, itemEnchantments);

                    // Armor trims
                    if (plate.getData(DataComponentTypes.TRIM) != null) {
                        ItemArmorTrim trim = plate.getData(DataComponentTypes.TRIM);
                        result.setData(DataComponentTypes.TRIM, trim);
                    }



                    // Item Name

                    result.setData(DataComponentTypes.ITEM_NAME, Component.text(customItem.getDisplayName()));
                    event.getView().setRepairCost(1);
                    event.setResult(result);
                }

            }
        }
    }


    public CustomItem addCustomElytra(Material plate) {
        ItemStack item = new ItemStack(plate);

        if (plate.equals(Material.NETHERITE_CHESTPLATE)) {
            NamespacedKey key = new NamespacedKey(configObject.getPackName(), configObject.getNetherite_name());
            return new CustomItem(netherite_display.content(), configObject.getNetherite_name(), constructPlate(plate, key));
        }

        if (plate.equals(Material.DIAMOND_CHESTPLATE)) {
            NamespacedKey key = new NamespacedKey(configObject.getPackName(), configObject.getDiamond_name());
            return new CustomItem(diamond_display.content(), configObject.getDiamond_name(), constructPlate(plate, key));
        }
        if (plate.equals(Material.IRON_CHESTPLATE)) {
            NamespacedKey key = new NamespacedKey(configObject.getPackName(), configObject.getIron_name());
            return new CustomItem(iron_display.content(), configObject.getIron_name(), constructPlate(plate, key));
        }
        if (plate.equals(Material.GOLDEN_CHESTPLATE)) {
            NamespacedKey key = new NamespacedKey(configObject.getPackName(), configObject.getGold_name());
            return new CustomItem(gold_display.content(), configObject.getGold_name(), constructPlate(plate, key));
        }

        return null;
    }

    public ItemStack constructPlate(Material material, NamespacedKey key) {
        ItemStack plate = new ItemStack(material);
        plate.setData(DataComponentTypes.GLIDER);

        CustomModelData modelData = CustomModelData.customModelData()
                .addFloat(10001f)
                .build();

        plate.setData(DataComponentTypes.CUSTOM_MODEL_DATA, modelData);
        plate.setData(DataComponentTypes.ITEM_MODEL, key);


        plate.editMeta(itemMeta ->  {
         itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, ArmoredElytra.ELYTRA_ID);
        });
        Equippable equippable = Equippable.equippable(EquipmentSlot.CHEST)
                .equipSound(SoundEventKeys.ITEM_ARMOR_EQUIP_NETHERITE)
                .assetId(key)
                .build();
        plate.setData(DataComponentTypes.EQUIPPABLE, equippable);
        return plate;
    }
    public SmithingTransformRecipe getSmithingRecipie() {
        NamespacedKey netherKey = new NamespacedKey(plugin, netherite_display.content().toLowerCase());

        SmithingTransformRecipe recipe = new SmithingTransformRecipe(
                netherKey,
                addCustomElytra(Material.NETHERITE_CHESTPLATE).getStack(),
                new RecipeChoice.MaterialChoice(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                new RecipeChoice.MaterialChoice(addCustomElytra(Material.DIAMOND_CHESTPLATE).getStack().getType()),
                new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT)
        );

        return recipe;
    }
}
