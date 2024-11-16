package de.silencio.harderDragon;

import de.silencio.harderDragon.listeners.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class HarderDragon extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EntitySpawnEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
