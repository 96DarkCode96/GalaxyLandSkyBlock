package me.darkcode.galaxylandskyblock.objects;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Item {

    private final ItemStack item;

    public Item(){
        item = new ItemStack(Material.AIR, 1);
    }

    public Item setMaterial(Material material){
        item.setType(material);
        return this;
    }

    public Item setAmount(int amount){
        item.setAmount(amount);
        return this;
    }

    public Item setUnbreakable(boolean unbreakable){
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return this;
    }

    public Item clearEnchants(){
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        for(Enchantment enchantment : meta.getEnchants().keySet()){
            meta.removeEnchant(enchantment);
        }
        item.setItemMeta(meta);
        return this;
    }

    public Item addEnchant(Enchantment enchantment, int value, boolean ignoreLimit){
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(enchantment, value, ignoreLimit);
        item.setItemMeta(meta);
        return this;
    }

    public Item setItemFlags(ItemFlag...itemFlags){
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        for(ItemFlag itemFlag : meta.getItemFlags()){
            meta.removeItemFlags(itemFlag);
        }
        meta.addItemFlags(itemFlags);
        item.setItemMeta(meta);
        return this;
    }

    public Item setLore(List<String> lore){
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public Item setDisplayName(String name){
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack build(){
        return item;
    }

}
