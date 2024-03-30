package kr.enak.minecraft.plugins.nomeltingice.command

import kr.enak.minecraft.plugins.nomeltingice.NoMeltingIce
import kr.enak.minecraft.plugins.nomeltingice.utils.forEachTry
import kr.enak.minecraft.plugins.nomeltingice.command.abc.CommandGroup
import org.bukkit.Bukkit

class CmdNoMeltingIce : CommandGroup("nomeltingice", "nmi") {
    init {
        subcommands(
            simple("on") { sender, _, _, _ ->
                NoMeltingIce.noMelt = true
                sender.sendMessage("얼음 녹음 방지를 켰습니다")
                return@simple true
            },
            simple("off") { sender, _, _, _ ->
                NoMeltingIce.noMelt = false
                sender.sendMessage("얼음 녹음 방지를 껐습니다")
                return@simple true
            },
        )
    }

    fun register() {
        listOf(
            this.name,
            *(this.alias).toTypedArray(),
        ).forEachTry {
            Bukkit.getPluginCommand(it)?.setExecutor(this)
        }
    }
}