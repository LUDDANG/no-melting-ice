package kr.enak.minecraft.plugins.nomeltingice

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFadeEvent

class IceMeltListener : Listener {
    companion object {
        val X = 1742..1775
        val Y = 56..256
        val Z = -441..-398
    }

    @EventHandler
    fun onIceMelt(e: BlockFadeEvent) {
        when (e.block.type) {
            Material.ICE,
            Material.BLUE_ICE,
            Material.PACKED_ICE,
            Material.FROSTED_ICE,
            Material.LEGACY_PACKED_ICE,
            Material.LEGACY_FROSTED_ICE,
            Material.LEGACY_ICE -> {
            }

            else -> return
        }

        e.isCancelled = (e.block.x in X && e.block.y in Y && e.block.z in Z)
    }
}