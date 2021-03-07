package com.noahverkaik.bounties.events

import com.noahverkaik.bounties.Bounties
import com.noahverkaik.bounties.sendConfigMessage
import com.noahverkaik.utils.ItemStackBuilder
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import java.util.AbstractMap

class BountyEntityDamageByEntityEventHandler(private val main: Bounties) : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (!main.activeBounties.contains(event.entity.uniqueId)) return

        val target = event.entity as Player

        // Is the damage enough to kill the target?
        if (event.finalDamage >= target.health) {
            var killer: Player? = null

            if (event.damager is Projectile) {
                var projectile = event.damager as Projectile
                if (projectile.shooter is Player) {
                    killer = projectile.shooter as Player
                }
            } else if (event.damager is Player) {
                killer = event.damager as Player
            }

            if (killer != null) {
                main.activeBounties.remove(target.uniqueId)

                if (!main.activeBounties.contains(killer.uniqueId)) {
                    val lastClaimed: Long = main.claimedBounties.getOrDefault(killer.uniqueId, -1L)
                    val shouldGiveBounty = (lastClaimed == -1L || System.currentTimeMillis() - lastClaimed >= main.claimEvery)

                    if (shouldGiveBounty) {
                        Bukkit.broadcastMessage(
                            Bounties.langFile.loadMessage("broadcast.claimed").placeholders(
                                AbstractMap.SimpleEntry("%killer%", killer.name),
                                AbstractMap.SimpleEntry("%target%", target.name)
                            ).color().build()
                        )

                        killer.inventory.addItem(ItemStackBuilder(main.bountyType).amount(main.bountyAmount).build()).forEach {
                            killer.world.dropItem(killer.location, it.value)
                        }

                        main.claimedBounties[killer.uniqueId] = System.currentTimeMillis()
                        return
                    }
                } else {
                    main.activeBounties.remove(killer.uniqueId)
                    killer.sendConfigMessage("player.lost-bounty")
                }

                Bukkit.broadcastMessage(
                    Bounties.langFile.loadMessage("broadcast.claimed-too-recently").placeholders(
                        AbstractMap.SimpleEntry("%killer%", killer.name),
                        AbstractMap.SimpleEntry("%target%", target.name)
                    ).color().build()
                )
            }
        }
    }

}