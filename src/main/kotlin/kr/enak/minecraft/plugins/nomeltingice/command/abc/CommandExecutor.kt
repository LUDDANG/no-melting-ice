package kr.enak.minecraft.plugins.nomeltingice.command.abc

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

abstract class CommandExecutor(
    override val name: String,
    vararg alias: String,
) : CommandMeta(), CommandExecutor, TabCompleter {
    override val alias: List<String> = alias.toList()

    companion object {
        fun simple(
            name: String,
            vararg alias: String,
            onCommand: (sender: CommandSender, command: Command, label: String, args: Array<out String>) -> Boolean,
        ): kr.enak.minecraft.plugins.nomeltingice.command.abc.CommandExecutor {
            return object : kr.enak.minecraft.plugins.nomeltingice.command.abc.CommandExecutor(name, *alias) {
                override fun onCommand(
                    sender: CommandSender, command: Command, label: String, args: Array<out String>,
                ): Boolean = onCommand(sender, command, label, args)
            }
        }
    }

    open fun sendHelpMessage(sender: CommandSender, label: String) {
        sender.sendMessage("/$label [...args]")
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender Source of the command.  For players tab-completing a
     * command inside of a command block, this will be the player, not
     * the command block.
     * @param command Command which was executed
     * @param alias The alias used
     * @param args The arguments passed to the command, including final
     * partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>,
    ): MutableList<String>? = mutableListOf()
}