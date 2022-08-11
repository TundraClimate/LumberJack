package io.github.tundraclimate.tree

import org.bukkit.Material
import org.bukkit.entity.Player

fun Material.isLog() = when (this) {
    Material.OAK_LOG, Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG,
    Material.JUNGLE_LOG, Material.SPRUCE_LOG, Material.MANGROVE_LOG, Material.CRIMSON_STEM,
    Material.WARPED_STEM -> true

    else -> false
}

fun Material.isLeaf() = when (this) {
    Material.OAK_LEAVES, Material.BIRCH_LEAVES, Material.DARK_OAK_LEAVES, Material.JUNGLE_LEAVES,
    Material.MANGROVE_LEAVES, Material.SPRUCE_LEAVES, Material.ACACIA_LEAVES, Material.NETHER_WART_BLOCK,
    Material.WARPED_WART_BLOCK, Material.SHROOMLIGHT -> true

    else -> false
}

fun getSapling(log: Material): Material = when (log) {
    Material.BIRCH_LOG -> Material.BIRCH_SAPLING
    Material.DARK_OAK_LOG -> Material.DARK_OAK_SAPLING
    Material.JUNGLE_LOG -> Material.JUNGLE_SAPLING
    Material.MANGROVE_LOG -> Material.MANGROVE_PROPAGULE
    Material.SPRUCE_LOG -> Material.SPRUCE_SAPLING
    Material.ACACIA_LOG -> Material.ACACIA_SAPLING
    Material.CRIMSON_STEM -> Material.CRIMSON_FUNGUS
    Material.WARPED_STEM -> Material.WARPED_FUNGUS
    else -> Material.OAK_SAPLING
}

fun Material.isDirt() = when (this) {
    Material.DIRT, Material.GRASS_BLOCK, Material.CRIMSON_NYLIUM, Material.WARPED_NYLIUM -> true
    else -> false
}

fun Player.onSneak(action: ShiftAction) = LumberJack.conf.getOnShift().contains(action) && this.isSneaking
