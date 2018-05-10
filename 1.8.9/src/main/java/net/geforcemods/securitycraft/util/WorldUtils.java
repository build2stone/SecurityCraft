package net.geforcemods.securitycraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class WorldUtils{

	/**
	 * Correctly schedules a task for execution on the main thread depending on if the
	 * provided world is client- or serverside
	 */
	public static void addScheduledTask(World w, Runnable r)
	{
		if(w.isRemote) //clientside
			Minecraft.getMinecraft().addScheduledTask(r);
		else //serverside
			((WorldServer)w).addScheduledTask(r);
	}

	/**
	 * Performs a ray trace against all blocks (except liquids) from the starting X, Y, and Z
	 * to the end point, and returns true if a block is within that path.
	 *
	 * Args: Starting X, Y, Z, ending X, Y, Z.
	 */
	public static boolean isPathObstructed(World world, int x1, int y1, int z1, int x2, int y2, int z2){
		return isPathObstructed(world, (double) x1, (double) y1, (double) z1, (double) x2, (double) y2, (double) z2);
	}

	/**
	 * Performs a ray trace against all blocks (except liquids) from the starting X, Y, and Z
	 * to the end point, and returns true if a block is within that path.
	 *
	 * Args: Starting X, Y, Z, ending X, Y, Z.
	 */
	public static boolean isPathObstructed(World world, double x1, double y1, double z1, double x2, double y2, double z2) {
		return world.rayTraceBlocks(new Vec3(x1, y1, z1), new Vec3(x2, y2, z2)) != null;
	}
}