package com.noahverkaik.utils

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta

/**
 * @author Noah Verkaik
 *
 * This class allows you to easily create item stacks,
 * much like a StringBuilder allows you to create strings.
 * Most of the properties are editable with simple
 * modifiers.
 */
class ItemStackBuilder(material: Material?) : Listener {
    private val itemStack: ItemStack = ItemStack(material!!)
    private val itemMeta: ItemMeta? = itemStack.itemMeta

    /**
     * Change the amount of the item.
     * @param amount The new amount
     */
    fun amount(amount: Int): ItemStackBuilder {
        itemStack.amount = amount
        return this
    }

    /**
     * Change the durability of the item.
     * @param durability The new durability
     */
    fun durability(durability: Short): ItemStackBuilder {
        itemStack.durability = durability
        return this
    }

    /**
     * Change the display name of the item.
     * @param displayName The new display name
     */
    fun displayName(displayName: String?): ItemStackBuilder {
        itemMeta!!.setDisplayName(displayName)
        return this
    }

    fun customModelData(data: Int): ItemStackBuilder {
        itemMeta!!.setCustomModelData(data)
        return this
    }

    /**
     * Change the lore of the item.
     * @param lore The new lore
     */
    fun lore(lore: List<String?>): ItemStackBuilder {
        if (lore.size > 0) {
            itemMeta!!.lore = lore
        }
        return this
    }

    fun enchant(enchanement: Enchantment?, level: Int, ignoreLevelRestriction: Boolean): ItemStackBuilder {
        itemMeta!!.addEnchant(enchanement!!, level, ignoreLevelRestriction)
        return this
    }

    fun itemFlags(vararg flags: ItemFlag?): ItemStackBuilder {
        itemMeta!!.addItemFlags(*flags)
        return this
    }

    fun skullOwner(player: Player): ItemStackBuilder {
        val playerheadmeta = itemMeta as SkullMeta?
        playerheadmeta!!.owner = player.name
        return this
    }

    /**
     * Turns the builder into an actual ItemStack which is able to be
     * used like you normally would.
     * @return The final ItemStack
     */
    fun build(): ItemStack {
        val clonedStack = itemStack.clone()
        clonedStack.itemMeta = itemMeta!!.clone()
        return clonedStack
    }

}