package xyz.tozymc.spigot.tmessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tozymc.spigot.tmessages.chat.ChatManager;
import xyz.tozymc.spigot.tmessages.listener.PlayerListener;
import xyz.tozymc.spigot.tmessages.util.Config;

public final class TMessagesPlugin extends JavaPlugin {

  private static TMessagesPlugin instance;
  private ChatManager chatManager;
  private boolean papiSupport;

  public static TMessagesPlugin getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;
    saveDefaultConfig();

    if (Config.EVENT_ENABLE.getBool()) {
      Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    chatManager = new ChatManager(this);
    chatManager.reloadFeatures();

    papiSupport = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  public ChatManager getChatManager() {
    return chatManager;
  }

  public boolean isPapiSupport() {
    return papiSupport;
  }
}
