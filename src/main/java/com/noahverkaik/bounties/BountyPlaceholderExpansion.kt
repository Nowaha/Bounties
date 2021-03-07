package com.noahverkaik.bounties

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class BountyPlaceholderExpansion(private val main: Bounties) : PlaceholderExpansion() {


    override fun getIdentifier(): String {
        return "bounty"
    }

    override fun getAuthor(): String {
        return "Nowaha"
    }

    override fun getVersion(): String {
        return "1.0"
    }

    override fun onRequest(player: OfflinePlayer?, params: String): String? {
        if (params.equals("test")) {
            return "success"
        }

        if (player == null || !player.isOnline) { return null }

        when(params) {
            "isbountied" -> return main.activeBounties.contains(player.uniqueId).toString()
            "bounty-tab" -> {
                if (main.activeBounties.contains(player.uniqueId)) {
                    return "${main.tabPrefix}${ChatColor.RESET} "
                } else {
                    return ""
                }
            }
            "bounty-chat" -> {
                if (main.activeBounties.contains(player.uniqueId)) {
                    return "${main.chatPrefix}${ChatColor.RESET} "
                } else {
                    return ""
                }
            }
            else -> {
                return null
            }
        }
    }

}