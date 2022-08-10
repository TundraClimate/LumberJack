package io.github.tundraclimate.tree

import io.github.tundraclimate.coldlib.ColdLib
import org.bukkit.configuration.file.FileConfiguration

class LJConfig {
    init {
        saveDefaultConfig()
    }
    private val plugin = ColdLib.plugin
    private val conf: FileConfiguration = plugin.config

    private fun saveDefaultConfig() = plugin.saveDefaultConfig()

    fun isBreakLeaf() = conf.getBoolean("break_leaf")

    fun getBreakLimit() = conf.getInt("break_limit")

    fun isBreakUpper() = conf.getBoolean("break_only_upper")

    fun isCollectItems() = conf.getBoolean("collect_items")
}