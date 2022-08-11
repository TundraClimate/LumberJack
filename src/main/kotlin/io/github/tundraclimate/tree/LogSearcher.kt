package io.github.tundraclimate.tree

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block

class LogSearcher(private val world: World) {
    private fun locationOf(x: Int, y: Int, z: Int) = Location(world, x.toDouble(), y.toDouble(), z.toDouble())

    fun checkUpper(loc: Location): List<Block> {
        val blocks = mutableListOf<Block>()
        (0..1).forEach { n ->
            (-1..1).forEach { i ->
                (-1..1).forEach { j ->
                    if (!(i == 0 && n == 0 && j == 0))
                    blocks.add(
                        locationOf(
                            loc.blockX + i,
                            loc.blockY + n,
                            loc.blockZ + j
                        ).block
                    )
                }
            }
        }
        return blocks.filter { it.type.isLog() }
    }

    fun checkLower(loc: Location): List<Block> {
        val blocks = mutableListOf<Block>()
        (-1..1).forEach { i ->
            (-1..1).forEach { j ->
                blocks.add(
                    locationOf(
                        loc.blockX + i,
                        loc.blockY - 1,
                        loc.blockZ + j
                    ).block
                )
            }
        }
        return blocks.filter { it.type.isLog() }
    }

    fun checkLeaf(loc: Location): List<Block> {
        val blocks = mutableListOf<Block>()
        (-1..1).forEach { n ->
            (-1..1).forEach { i ->
                (-1..1).forEach { j ->
                    if (!(i == 0 && n == 0 && j == 0))
                        blocks.add(
                            locationOf(
                                loc.blockX + i,
                                loc.blockY + n,
                                loc.blockZ + j
                            ).block
                        )
                }
            }
        }
        return blocks.filter { it.type.isLeaf() }
    }
}