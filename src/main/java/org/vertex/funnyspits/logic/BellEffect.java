package org.vertex.funnyspits.logic;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;

import javax.swing.text.html.parser.Entity;

public class BellEffect {
//    public static void playBellEffect(Block block) {
//        World world = block.getWorld();
//        world.playSound(block.getLocation(), Sound.BLOCK_BELL_USE,
//                3.0f, 1.0f);
//        byte face = 2;
//        BlockFace facing =
//                ((Directional)block.getBlockData()).getFacing();
//        if(facing == BlockFace.NORTH) {
//            face = 2;
//        } else if(facing == BlockFace.SOUTH) {
//            face = 3;
//        } else if(facing == BlockFace.WEST) {
//            face = 4;
//        } else if(facing == BlockFace.EAST) {
//            face = 5;
//        }
////        BlockPosition position = new (block.getX(), block.getY(), block.getZ());
//        for(Entity entity : world.getNearbyEntities(block.getLocation(), 32, 32, 32)) {
//            if(entity instanceof Player) {
//                PlayerConnection connection =
//                        ((CraftPlayer)entity).getHandle().playerConnection;
//                connection.sendPacket(
//                        new PacketPlayOutBlockAction(position,
//                                Blocks.BELL, 1, face));
//            }
//        }
//    }
}
