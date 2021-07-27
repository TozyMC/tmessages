package xyz.tozymc.spigot.tmessages.chat;

import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_BROADCAST_SECTION;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_GENERAL_SECTION;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_GLOBAL_SECTION;
import static xyz.tozymc.spigot.tmessages.util.Config.CHAT_LOCAL_SECTION;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tozymc.spigot.tmessages.chat.listener.PlayerChatListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class ChatManager {

  private final JavaPlugin plugin;
  private final Map<FeatureType, ChatFeature> features = new HashMap<>();

  public ChatManager(JavaPlugin plugin) {
    this.plugin = plugin;
    registerListeners();
  }

  private void registerListeners() {
    Bukkit.getPluginManager().registerEvents(new PlayerChatListener(this), plugin);
  }

  public void reloadFeature() {
    features.clear();
    features.put(FeatureType.GENERAL, new ChatFeature(CHAT_GENERAL_SECTION.getMap()));
    features.put(FeatureType.BROADCAST, new ChatFeature(CHAT_BROADCAST_SECTION.getMap()));
    features.put(FeatureType.GLOBAL, new ChatFeature(CHAT_GLOBAL_SECTION.getMap()));

    ChatFeature chatLocalFeature = new ChatFeature(CHAT_LOCAL_SECTION.getMap());
    chatLocalFeature.addVariable("world", player -> player.getWorld().getName());
    features.put(FeatureType.LOCAL, chatLocalFeature);
  }

  public boolean requestEvent(ChatFeature feature, AsyncPlayerChatEvent event) {
    if (!feature.isEnable()) {
      return false;
    }
    String prefix = feature.getPrefix();
    if (prefix == null) {
      return false;
    }
    boolean requested = event.getMessage().startsWith(prefix);
    if (!requested) {
      return false;
    }
    if (feature.getPermission().notHas(event.getPlayer())) {
      return false;
    }
    event.setCancelled(true);
    return true;
  }

  public void chat(Entry<FeatureType, ChatFeature> featureEntry, Player sender, String message) {
    FeatureType type = featureEntry.getKey();
    ChatFeature feature = featureEntry.getValue();
    switch (type) {
      case BROADCAST:
        broadcast(feature, message);
        break;
      case LOCAL:
        chatToWorldPlayers(feature, sender, message);
        break;
      case GENERAL:
      case GLOBAL:
      default:
        chatToAllPlayers(feature, sender, message);
        break;
    }
  }

  public void chatToAllPlayers(ChatFeature feature, Player sender, String message) {
    feature.chat(sender, message, Bukkit.getOnlinePlayers(), true);
  }

  public void chatToWorldPlayers(ChatFeature feature, Player sender, String message) {
    feature.chat(sender, message, sender.getWorld().getPlayers(), false);
  }

  public void broadcast(ChatFeature feature, String message) {
    feature.chat(message, Bukkit.getOnlinePlayers(), true);
  }

  public ChatFeature getFeature(FeatureType type) {
    return features.get(type);
  }

  public Map<FeatureType, ChatFeature> getFeatures() {
    return features;
  }

  public enum FeatureType {GENERAL, BROADCAST, GLOBAL, LOCAL}
}
