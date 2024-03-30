package kr.enak.minecraft.plugins.nomeltingice

import kr.enak.minecraft.plugins.nomeltingice.command.CmdNoMeltingIce
import kr.entree.spigradle.annotations.PluginMain
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class NoMeltingIce : JavaPlugin() {
    companion object {
        lateinit var INSTANCE: NoMeltingIce

        var noMelt: Boolean = true
    }

    init {
        INSTANCE = this
    }
    override fun onEnable() {
        super.onEnable()

        try {
            CmdNoMeltingIce().register()
            Bukkit.getPluginManager().registerEvents(IceMeltListener(), this)
        } catch (e: Throwable) {
            logger.info("설정 중 오류가 발생하여 플러그인을 비활성화합니다")
            this.isEnabled = false
        }

        logger.info("=== 활성화된 얼음 녹음 방지 범위 ===")
        logger.info("X = " + IceMeltListener.X.toString())
        logger.info("Y = " + IceMeltListener.Y.toString())
        logger.info("Z = " + IceMeltListener.Z.toString())
    }

    override fun onDisable() {
        super.onDisable()
    }
}