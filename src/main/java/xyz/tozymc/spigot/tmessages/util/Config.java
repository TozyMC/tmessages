package xyz.tozymc.spigot.tmessages.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.tozymc.spigot.api.title.util.Colors;
import xyz.tozymc.spigot.tmessages.TMessagesPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public enum Config {
  EVENT_ENABLE("Event.Enable"),
  EVENT_JOIN("Event.Join", "player", "displayname"),
  EVENT_QUIT("Event.Quit", "player", "displayname"),

  CHAT_GENERAL_ENABLE("Chat.General.Enable"),
  CHAT_GENERAL_FORMAT("Chat.General.Format", "player", "displayname", "message"),

  CHAT_BROADCAST_ENABLE("Chat.Broadcast.Enable"),
  CHAT_BROADCAST_FORMAT("Chat.Broadcast.Format", "message"),
  CHAT_BROADCAST_PREFIX("Chat.Broadcast.Prefix"),
  CHAT_BROADCAST_PERMISSION("Chat.Broadcast.Permission"),

  CHAT_GLOBAL_ENABLE("Chat.Global.Enable"),
  CHAT_GLOBAL_FORMAT("Chat.Global.Format", "player", "displayname", "message"),
  CHAT_GLOBAL_PREFIX("Chat.Global.Prefix"),
  CHAT_GLOBAL_PERMISSION("Chat.Global.Permission"),

  CHAT_LOCAL_ENABLE("Chat.Local.Enable"),
  CHAT_LOCAL_FORMAT("Chat.Local.Format", "player", "displayname", "message", "world"),
  CHAT_LOCAL_PREFIX("Chat.Local.Prefix"),
  CHAT_LOCAL_PERMISSION("Chat.Local.Permission"),

  MESSAGES_NO_PERMISSION("Messages.NoPermission"),
  MESSAGES_CONFIG_RELOAD("Messages.ConfigReload");

  private static final FileConfiguration CONFIG;
  private static final Pattern VARS_PATTER = Pattern.compile("\\{(\\w+)}");

  static {
    CONFIG = TMessagesPlugin.getInstance().getConfig();
  }

  private final String path;
  private Map<String, Integer> vars;

  Config(String path) {
    this.path = path;
  }

  Config(String path, String... vars) {
    this.path = path;
    this.vars = new HashMap<>();
    IntStream.range(0, vars.length).forEach(i -> this.vars.put(vars[i], i));
  }

  public String format(Player player, Object... varsReplacements) {
    String text = format(player);
    Matcher matcher = VARS_PATTER.matcher(text);
    StringBuffer buffer = new StringBuffer();
    while (matcher.find()) {
      int index = vars.getOrDefault(matcher.group(1), -1);
      matcher.appendReplacement(buffer, index > -1 ? String.valueOf(varsReplacements[index]) : "");
    }
    matcher.appendTail(buffer);
    return buffer.toString();
  }

  public String format(Player player) {
    return PlaceholderAPI.setPlaceholders(player, getColored());
  }

  public String getString() {
    return CONFIG.getString(path);
  }

  public String getColored() {
    return Colors.color(Objects.requireNonNull(CONFIG.getString(path)));
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
