package de.chaos.armoredElytra.utils.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class CustomItem {
    String DisplayName;
    String id;
    ItemStack Stack;
}
