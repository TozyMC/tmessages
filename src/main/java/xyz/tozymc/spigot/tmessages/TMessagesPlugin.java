package xyz.tozymc.spigot.tmessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tozymc.spigot.tmessages.chat.ChatManager;

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
