package io.github.tundraclimate.tree

import io.github.tundraclimate.coldlib.ColdLib
import org.bukkit.configuration.file.FileConfiguration

class LJConfig {
    private val plugin = ColdLib.plugin
    private val conf: FileConfiguration = plugin.config

    fun isDefaultEnable() = conf.getBoolean("default_enable", true)

    fun isBreakLeaf() = conf.getBoolean("break_leaf", true)

    fun getBreakLimit() = conf.getInt("break_limit", 200)

    fun isBreakUpper() = conf.getBoolean("break_only_upper", false)

    fun isCollectItems() = conf.getBoolean("collect_items", true)

    fun isAutoSupply() = conf.getBoolean("auto_supply", true)

    fun getOnShift(): List<ShiftAction> =
        conf.getList("on_shift", emptyList<String>())?.filterIsInstance<String>()?.mapNotNull {
            runCatching {
                ShiftAction.valueOf(it)
            }.getOrNull()
        }.let { it ?: emptyList() }
}