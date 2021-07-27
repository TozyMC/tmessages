package xyz.tozymc.spigot.tmessages.util;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.tozymc.spigot.api.title.util.Colors;
import xyz.tozymc.spigot.tmessages.TMessagesPlugin;

import java.util.Map;
import java.util.Objects;

public enum Config {
  EVENT_ENABLE("Event.Enable"),
  EVENT_JOIN("Event.Join"),
  EVENT_QUIT("Event.Quit"),

  CHAT_GENERAL_SECTION("Chat.General"),
  CHAT_BROADCAST_SECTION("Chat.Broadcast"),
  CHAT_GLOBAL_SECTION("Chat.Global"),
  CHAT_LOCAL_SECTION("Chat.Local"),

  MESSAGES_NO_PERMISSION("Messages.NoPermission"),
  MESSAGES_CONFIG_RELOAD("Messages.ConfigReload");

  private static final FileConfiguration CONFIG;

  static {
    CONFIG = TMessagesPlugin.getInstance().getConfig();
  }

  private final String path;

  Config(String path) {
    this.path = path;
  }

  public String getString() {
    return Objects.requireNonNull(CONFIG.getString(path));
  }

  public String getColored() {
    return Colors.color(getString());
  }

  public boolean getBool() {
    return CONFIG.getBoolean(path);
  }

  public Map<String, Object> getMap() {
    return Objects.requireNonNull(CONFIG.getConfigurationSection(path)).getValues(true);
  }
}
