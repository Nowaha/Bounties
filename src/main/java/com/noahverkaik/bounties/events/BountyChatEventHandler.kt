package com.noahverkaik.bounties.events

import com.noahverkaik.bounties.Bounties
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class BountyChatEventHandler(private val main: Bounties) : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun onAsyncPlayerChat(event: AsyncPlayerChatEvent) {
        if (main.activeBounties.contains(event.player.uniqueId)) {
            event.format = "${main.chatPrefix} ${ChatColor.RESET}${event.format}"
        }
    }

}