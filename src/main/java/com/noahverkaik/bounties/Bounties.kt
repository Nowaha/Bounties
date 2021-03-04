package com.noahverkaik.bounties

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Bounties : JavaPlugin() {

    val activeBounties = HashMap<UUID, Int>()

    override fun onEnable() {
        Bukkit.getPluginCommand("addbounty")!!.setExecutor(AddBountyCommand(this))
    }

}