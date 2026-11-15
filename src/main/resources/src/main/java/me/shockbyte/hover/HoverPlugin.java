package me.shockbyte.hover;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerProfile;

import java.util.UUID;

public class HoverPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        try {
            var method = event.getClass().getMethod("getPlayerSample");
            @SuppressWarnings("unchecked")
            var sample = (java.util.List<PlayerProfile>) method.invoke(event);
            sample.clear();
            sample.add(createProfile("&f&lqq.shockbyte.me"));
            sample.add(createProfile(" "));
            sample.add(createProfile("&3НАШ ТГК: &3&l&o@shock_byte"));
            sample.add(createProfile("&6НАШ САЙТ: &6&l&oshockbyte.trademc.org"));
        } catch (Exception ignored) {
        }
    }

    private PlayerProfile createProfile(String text) {
        return Bukkit.createPlayerProfile(UUID.randomUUID(), ChatColor.translateAlternateColorCodes('&', text));
    }
}
