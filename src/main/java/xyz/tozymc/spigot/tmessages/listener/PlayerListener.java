package xyz.tozymc.spigot.tmessages.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.tozymc.spigot.tmessages.util.Config;
import xyz.tozymc.spigot.tmessages.util.Formatter;

public class PlayerListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Formatter formatter = new Formatter(Config.EVENT_JOIN.getString()).player(event.getPlayer());
    event.setJoinMessage(formatter.format());
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Formatter formatter = new Formatter(Config.EVENT_QUIT.getString()).player(event.getPlayer());
    event.setQuitMessage(formatter.format());
  }
}
