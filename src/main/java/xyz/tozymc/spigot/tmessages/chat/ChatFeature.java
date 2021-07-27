package xyz.tozymc.spigot.tmessages.chat;

import java.util.Map;
import xyz.tozymc.spigot.api.util.bukkit.permission.PermissionWrapper;

public class ChatFeature {

  private final boolean enable;
  private final String format;
  private String prefix;
  private PermissionWrapper permission;

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
