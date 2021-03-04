package com.noahverkaik.bounties

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddBountyCommand(private val main: Bounties) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            sender.sendMessage("§cYou can not use this command.")
            return true
        }
        return true
    }

}