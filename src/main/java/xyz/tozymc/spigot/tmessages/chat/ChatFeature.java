package xyz.tozymc.spigot.tmessages.chat;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.tozymc.spigot.api.util.bukkit.permission.PermissionWrapper;

public class ChatFeature {

  private final boolean enable;
  private final String format;
  private String prefix;
  private PermissionWrapper permission;
  private Map<String, Function<Player, String>> variables;

  public ChatFeature(Map<String, Object> map) {
    this.enable = (boolean) map.get("Enable");
    this.format = String.valueOf(map.get("Format"));
    if (map.containsKey("ChatPrefix")) {
      this.prefix = String.valueOf(map.get("ChatPrefix"));
    }
    if (map.containsKey("Permission")) {
      this.permission = PermissionWrapper.of(String.valueOf(map.get("Permission")));
    }
  }

  public void chat(Player sender, String message, Collection<? extends Player> receives) {
    ChatFormatter formatter = new ChatFormatter(this);
    formatter.sender(sender).text(message);
    if (variables != null) {
      variables.forEach((var, function) -> {
        if (sender != null) {
          formatter.placeholder(var, function.apply(sender));
        }
      });
    }
    String format = formatter.format();
    receives.forEach(receive -> receive.sendMessage(format));
    Bukkit.getConsoleSender().sendMessage(format);
  }

  public void chat(String message, Collection<? extends Player> receives) {
    chat(null, message, receives);
  }

  public void addVariable(String var, Function<Player, String> function) {
    if (variables == null) {
      variables = new HashMap<>();
    }

    variables.put(var, function);
  }

  public boolean isEnable() {
    return enable;
  }

  public String getFormat() {
    return format;
  }

  public String getPrefix() {
    return prefix;
  }

  public PermissionWrapper getPermission() {
    return permission;
  }
}
