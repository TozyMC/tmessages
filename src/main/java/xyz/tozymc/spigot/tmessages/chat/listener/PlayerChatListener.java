package xyz.tozymc.spigot.tmessages.chat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.tozymc.spigot.tmessages.chat.ChatFeature;
import xyz.tozymc.spigot.tmessages.chat.ChatManager;
import xyz.tozymc.spigot.tmessages.chat.ChatManager.FeatureType;

public class PlayerChatListener implements Listener {

  private final ChatManager manager;

  public PlayerChatListener(ChatManager manager) {this.manager = manager;}

  @EventHandler(priority = EventPriority.HIGH)
  public void onSpecialFeatureRequest(AsyncPlayerChatEvent event) {
    if (!event.isAsynchronous()) {
      return;
    }
    if (event.isCancelled()) {
      return;
    }
    manager.getFeatures()
        .entrySet()
        .stream()
        .filter(entry -> manager.requestEvent(entry.getValue(), event))
        .findFirst()
        .ifPresent(entry -> manager.chat(entry, event.getPlayer(), event.getMessage()));
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onGeneralChatRequest(AsyncPlayerChatEvent event) {
    if (!event.isAsynchronous()) {
      return;
    }
    if (event.isCancelled()) {
      return; // Special chat (broadcast, global, local,...) has requested.
    }

    event.setCancelled(true);
    ChatFeature feature = manager.getFeature(FeatureType.GENERAL);
    manager.chatToAllPlayers(feature, event.getPlayer(), event.getMessage());
  }
}
