package kr.enak.minecraft.plugins.nomeltingice.command.abc

abstract class CommandMeta {
    abstract val name: String

    open val alias: List<String> = emptyList()

    fun names(): List<String> = listOf(name, *alias.toTypedArray())
}