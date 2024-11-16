package de.silencio.harderDragon.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntitySpawnEvent implements Listener {

    // More health
    private static final double EXTRA_HEALTH = 1000.0;

    // Damage Threshold
    private static final double HP_THRESHOLD = 300.0;
    private double lastMilestone = 0.0;

    // Resistance effect
    private int resistanceLevel = 0;

    @EventHandler
    private void onEntitySpawn(org.bukkit.event.entity.EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof EnderDragon dragon)) return;

        // Increase dragon health
        dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(EXTRA_HEALTH);
        dragon.setHealth(dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());

        lastMilestone = dragon.getHealth();
    }

    @EventHandler
    public void onDragonDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof EnderDragon dragon)) return;

        // Get dragon's current health after damage
        double currentHealth = dragon.getHealth() - e.getFinalDamage();

        // Ensure currentHealth does not exceed max health
        currentHealth = Math.min(currentHealth, dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        if (lastMilestone - currentHealth >= HP_THRESHOLD) {
            lastMilestone -= HP_THRESHOLD;

            // Add a level of resistance to the dragon
            resistanceLevel++;
            resistanceLevel = Math.min(resistanceLevel, 3);
            dragon.removePotionEffect(PotionEffectType.RESISTANCE);
            dragon.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, resistanceLevel, false, false));
        }


        dragon.setHealth(currentHealth);
    }
}
