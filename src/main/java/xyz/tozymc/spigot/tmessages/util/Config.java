package xyz.tozymc.spigot.tmessages.util;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.tozymc.spigot.tmessages.TMessagesPlugin;

public enum Config {
  ;

  private static final FileConfiguration CONFIG;

  static {
    CONFIG = TMessagesPlugin.getInstance().getConfig();
  }

  private final String path;

  Config(String path) {
    this.path = path;
  }

  public String getString() {
    return CONFIG.getString(path);
  }

  public int getInt() {
    return CONFIG.getInt(path);
  }

  public boolean getBool() {
    return CONFIG.getBoolean(path);
  }

  public String getPath() {
    return path;
  }
}
