package xyz.tozymc.spigot.tmessages.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.tozymc.spigot.api.title.util.Colors;
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

  public String format(Player player) {
    String val = getString();
    val = Colors.color(val);
    return PlaceholderAPI.setPlaceholders(player, val);
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
