package me.shockbyte.hover;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.status.server.WrapperStatusServerResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class HoverPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().getSettings().checkForUpdates(false);
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        PacketEvents.getAPI().getEventManager().registerListener(new PacketListenerAbstract(PacketListenerPriority.NORMAL) {
            @Override
            public void onPacketSend(PacketSendEvent event) {
                if (event.getPacketType() == PacketType.Status.Server.RESPONSE) {
                    WrapperStatusServerResponse wrapper = new WrapperStatusServerResponse(event);
                    JsonObject json = wrapper.getComponent();
                    if (json.has("players")) {
                        JsonObject players = json.getAsJsonObject("players");
                        JsonArray sample = new JsonArray();

                        addLine(sample, "&f&lqq.shockbyte.me");
                        addLine(sample, " ");
                        addLine(sample, "&3НАШ ТГК: &3&l&o@shock_byte");
                        addLine(sample, "&6НАШ САЙТ: &6&l&oshockbyte.trademc.org");

                        players.add("sample", sample);
                        wrapper.setComponent(json);
                    }
                }
            }
        });
        PacketEvents.getAPI().init();
    }

    private void addLine(JsonArray array, String text) {
        JsonObject entry = new JsonObject();
        entry.addProperty("name", ChatColor.translateAlternateColorCodes('&', text));
        entry.addProperty("id", UUID.randomUUID().toString());
        array.add(entry);
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }
}
