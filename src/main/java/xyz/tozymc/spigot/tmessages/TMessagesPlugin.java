package xyz.tozymc.spigot.tmessages;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.tozymc.spigot.tmessages.chat.ChatManager;

public final class TMessagesPlugin extends JavaPlugin {

  private static TMessagesPlugin instance;
  private ChatManager chatManager;

  public static TMessagesPlugin getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;
    saveDefaultConfig();

    chatManager = new ChatManager(this);
    chatManager.reloadFeatures();
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  public ChatManager getChatManager() {
    return chatManager;
  }
}
