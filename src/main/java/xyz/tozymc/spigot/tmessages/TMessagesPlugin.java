package xyz.tozymc.spigot.tmessages;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.tozymc.spigot.tmessages.listener.PlayerListener;

public final class TMessagesPlugin extends JavaPlugin {

  private static TMessagesPlugin instance;

  public static TMessagesPlugin getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;
    saveDefaultConfig();

    getServer().getPluginManager().registerEvents(new PlayerListener(), this);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
