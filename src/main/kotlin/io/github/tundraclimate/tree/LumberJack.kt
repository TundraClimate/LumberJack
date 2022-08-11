package io.github.tundraclimate.tree

import io.github.tundraclimate.coldlib.ColdLib
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

class LumberJack : JavaPlugin() {
    companion object{
        lateinit var conf: LJConfig
    }

    override fun onEnable() {
        ColdLib.plugin = this
        conf = LJConfig()
        init()
    }

    private fun init() {
        OnBreakLog.register()
        saveDefaultConfig()
    }
}