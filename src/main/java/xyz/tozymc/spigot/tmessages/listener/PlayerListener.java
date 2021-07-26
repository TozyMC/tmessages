package xyz.tozymc.spigot.tmessages.listener;

import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_BROADCAST_ENABLE;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_BROADCAST_FORMAT;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_BROADCAST_PERMISSION;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_BROADCAST_PREFIX;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_GENERAL_FORMAT;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_GLOBAL_ENABLE;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_GLOBAL_FORMAT;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_GLOBAL_PERMISSION;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_GLOBAL_PREFIX;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_LOCAL_ENABLE;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_LOCAL_FORMAT;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_LOCAL_PERMISSION;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_LOCAL_PREFIX;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.tozymc.spigot.tmessages.util.Config;

public class PlayerListener implements Listener {

  @EventHandler
  public void onSpecialChat(AsyncPlayerChatEvent event) {
    if (!event.isAsynchronous()) {
      return;
    }

    if (event.isCancelled()) {
      return;
    }

    String message = event.getMessage();
    if (message.startsWith(CHAT_BROADCAST_PREFIX.getString())) {
      chatBroadcast(event);
      return;
    }
    if (message.startsWith(CHAT_GLOBAL_PREFIX.getString())) {
      chatGlobal(event);
      return;
    }
    if (message.startsWith(CHAT_LOCAL_PREFIX.getString())) {
      chatLocal(event);
    }
  }

  private void chatBroadcast(AsyncPlayerChatEvent event) {
    if (!CHAT_BROADCAST_ENABLE.getBool()) {
      return;
    }
    Player sender = event.getPlayer();
    if (!sender.hasPermission(CHAT_BROADCAST_PERMISSION.getString())) {
      sendNoPermission(sender);
      return;
    }
    event.setCancelled(true);

    String message = event.getMessage().substring(CHAT_BROADCAST_FORMAT.getString().length());
    String format = CHAT_BROADCAST_FORMAT.format(sender, message);
    sendMessageTo(format, Bukkit.getOnlinePlayers());
  }

  private void chatGlobal(AsyncPlayerChatEvent event) {
    if (!CHAT_GLOBAL_ENABLE.getBool()) {
      return;
    }
    Player sender = event.getPlayer();
    if (!sender.hasPermission(CHAT_GLOBAL_PERMISSION.getString())) {
      sendNoPermission(sender);
      return;
    }
    event.setCancelled(true);

    String message = event.getMessage().substring(CHAT_GLOBAL_FORMAT.getString().length());
    String format = CHAT_GLOBAL_FORMAT.format(sender, sender.getName(), sender.getDisplayName(),
        message);
    sendMessageTo(format, Bukkit.getOnlinePlayers());
  }

  private void chatLocal(AsyncPlayerChatEvent event) {
    if (!CHAT_LOCAL_ENABLE.getBool()) {
      return;
    }
    Player sender = event.getPlayer();
    if (!sender.hasPermission(CHAT_LOCAL_PERMISSION.getString())) {
      sendNoPermission(sender);
      return;
    }
    event.setCancelled(true);

    World world = sender.getWorld();
    String message = event.getMessage().substring(CHAT_LOCAL_PREFIX.getString().length());
    String format = CHAT_LOCAL_FORMAT.format(sender, sender.getName(), sender.getDisplayName(),
        message, world.getName());
    sendMessageTo(format, world.getPlayers());
  }

  private void sendNoPermission(Player player) {
    player.sendMessage(Config.MESSAGES_NO_PERMISSION.getColored());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
    if (!event.isAsynchronous()) {
      return;
    }

    if (event.isCancelled()) {
      return;
    }
    event.setCancelled(true);

    Player sender = event.getPlayer();
    String format = CHAT_GENERAL_FORMAT.format(sender, sender.getName(), sender.getDisplayName());
    sendMessageTo(format, Bukkit.getOnlinePlayers());
  }

  private void sendMessageTo(String message, Collection<? extends Player> players) {
    players.forEach(player -> player.sendMessage(message));
    Bukkit.getConsoleSender().sendMessage(message);
  }
}
