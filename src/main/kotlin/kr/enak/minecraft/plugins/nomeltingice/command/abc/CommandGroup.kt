package kr.enak.minecraft.plugins.nomeltingice.command.abc

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class CommandGroup(
    name: String,
    vararg alias: String,
) : CommandExecutor(name, *alias) {
    private val subcommands: MutableMap<String, org.bukkit.command.CommandExecutor> = hashMapOf()
    private val subcommandsAlias: MutableMap<String, org.bukkit.command.CommandExecutor> = hashMapOf()

    protected fun subcommands(vararg subcommands: CommandExecutor) {
        this.subcommands.putAll(subcommands.associateBy { it.name })
        subcommands.forEach { command ->
            command.names().forEach { name ->
                subcommandsAlias[name] = command
            }
        }
    }

    override fun sendHelpMessage(sender: CommandSender, label: String) {
        val subcommandNames = subcommands.keys.joinToString("|")
        if (subcommandNames.isEmpty()) {
            sender.sendMessage("/$label - 하위 명령어가 없습니다.")
            return
        }

        sender.sendMessage(
            "",
            "========== [ 사용법 ] ==========",
            "/$label <$subcommandNames>",
        )
    }

    open fun onCommand(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        (args.getOrNull(0)?.let { subcommandsAlias[it] } as? CommandExecutor)?.sendHelpMessage(sender, label)
            ?: sendHelpMessage(sender, label)
        return true
    }

    final override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean = (args.getOrNull(0)?.let { subcommandsAlias[it] }?.onCommand(
        sender,
        command,
        label + " " + args[0],
        args.slice(1 until args.size).toTypedArray()
    ) ?: this.onCommand(sender, label, args)).also { result ->
        if (!result) sendHelpMessage(sender, label)
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
    ): MutableList<String>? = args.getOrNull(0)?.let { subcommandsAlias[it] }?.let { subcommand ->
        if (subcommand is CommandExecutor) subcommand.onTabComplete(
            sender,
            command,
            alias,
            args.slice(1 until args.size).toTypedArray()
        )
        else null
    } ?: subcommands.keys.toMutableList()
}