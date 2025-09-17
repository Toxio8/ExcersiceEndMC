package fr.toxio.endmc.entities;

import org.bukkit.Location;
import java.util.UUID;

public class Zone {
    private String name;
    private UUID world;
    private int x1, y1, z1;
    private int x2, y2, z2;

    public Zone() {}

    public Zone(String name, Location p1, Location p2) {
        this.name = name;
        this.world = p1.getWorld().getUID();
        this.x1 = Math.min(p1.getBlockX(), p2.getBlockX());
        this.x2 = Math.max(p1.getBlockX(), p2.getBlockX());
        this.y1 = Math.min(p1.getBlockY(), p2.getBlockY());
        this.y2 = Math.max(p1.getBlockY(), p2.getBlockY());
        this.z1 = Math.min(p1.getBlockZ(), p2.getBlockZ());
        this.z2 = Math.max(p1.getBlockZ(), p2.getBlockZ());
    }

    public boolean contains(Location loc) {
        if (!loc.getWorld().getUID().equals(world)) return false;
        int x = loc.getBlockX(), y = loc.getBlockY(), z = loc.getBlockZ();
        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getWorld() {
        return world;
    }

    public void setWorld(UUID world) {
        this.world = world;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getZ1() {
        return z1;
    }

    public void setZ1(int z1) {
        this.z1 = z1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getZ2() {
        return z2;
    }

    public void setZ2(int z2) {
        this.z2 = z2;
    }

}
