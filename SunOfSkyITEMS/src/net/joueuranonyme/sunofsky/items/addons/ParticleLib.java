package net.joueuranonyme.sunofsky.items.addons;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class ParticleLib {
	public static void spawnLine(Particle particle, Location loc1, Location loc2, int number) {
		World w = loc1.getWorld();
		double x = loc1.getX(),
			   y = loc1.getY(),
			   z = loc1.getZ(),
			   lx = (loc2.getX()-x)/number,
			   ly = (loc2.getY()-y)/number,
			   lz = (loc2.getZ()-z)/number;
		Location al = loc1;
		for(int i=1; i < number+1; i++) {
			w.spawnParticle(particle, al, 1, 0.02, 0.02, 0.02, 0.01);
			al.add(lx, ly, lz);
		}
		
	}
}
