package xyz.tozymc.spigot.tmessages;

import org.bukkit.plugin.java.JavaPlugin;

public final class TMessagesPlugin extends JavaPlugin {

  private static TMessagesPlugin instance;

  public static TMessagesPlugin getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
