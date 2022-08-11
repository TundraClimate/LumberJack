package io.github.tundraclimate.tree

import io.github.tundraclimate.coldlib.server.ListenEvent
import io.github.tundraclimate.coldlib.util.runTaskLater
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack

object OnBreakLog {
    fun register() {
        ListenEvent.addBreakBlockTask { e ->
            if (!e.block.type.isLog()) return@addBreakBlockTask
            val world = e.block.world
            if (LumberJack.conf.isDefaultEnable() || e.player.onSneak(ShiftAction.ENABLED)) {
                if (e.player.onSneak(ShiftAction.DISABLED))
                    return@addBreakBlockTask
                val searcher = LogSearcher(world)
                val collectLoc = e.block.location
                val logs = mutableListOf<Block>()
                val block = e.block
                val sap = getSapling(block.type)
                var count = LumberJack.conf.getBreakLimit()
                fun addLogs(loc: Location) {
                    val upper = searcher.checkUpper(loc).toMutableList()
                    val lower = searcher.checkLower(loc)
                    if (LumberJack.conf.isBreakLeaf()) upper.addAll(searcher.checkLeaf(loc))
                    logs.add(world.getBlockAt(loc))
                    count -= 1
                    if (count == 0) return
                    if (upper.isEmpty())
                        if (!LumberJack.conf.isBreakUpper()) {
                            if (lower.isEmpty()) return
                        } else return
                    upper.forEach {
                        if (!logs.contains(it))
                            addLogs(it.location)
                    }
                    if (!LumberJack.conf.isBreakUpper())
                        lower.forEach {
                            if (!logs.contains(it))
                                addLogs(it.location)
                        }
                }
                addLogs(collectLoc)
                if (!LumberJack.conf.isCollectItems())
                    logs.forEach { it.breakNaturally(e.player.inventory.itemInMainHand) }
                else {
                    val tmp = logs.map { it.type }
                    val b = world.getBlockAt(collectLoc)
                    logs.forEach { it.type = Material.AIR }
                    tmp.forEach {
                        b.type = it
                        b.breakNaturally(e.player.inventory.itemInMainHand)
                    }
                }
                if ((LumberJack.conf.isAutoSupply() || e.player.onSneak(ShiftAction.AUTO_SUPPLY)) && Location(
                        world,
                        collectLoc.blockX.toDouble(),
                        (collectLoc.blockY - 1).toDouble(),
                        collectLoc.blockZ.toDouble()
                    ).block.type.isDirt()
                ) {
                    if (e.player.inventory.contains(getSapling(block.type))) {
                        runTaskLater(1L) {
                            world.getBlockAt(collectLoc).type = sap
                            e.player.inventory.let {
                                val i = it.first(sap)
                                val newElem = ItemStack(it.elementAt(i))
                                newElem.amount -= 1
                                it.setItem(i, newElem)
                            }
                        }
                    }
                }
            }
        }
    }
}