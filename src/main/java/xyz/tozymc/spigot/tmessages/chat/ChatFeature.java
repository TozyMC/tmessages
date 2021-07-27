package xyz.tozymc.spigot.tmessages.chat;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import xyz.tozymc.spigot.api.util.bukkit.permission.PermissionWrapper;

public class ChatFeature implements ConfigurationSerializable {

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

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("Enable", enable);
    map.put("Format", format);
    map.put("ChatPrefix", prefix);
    map.put("Permission", permission.getPermission());
    return map;
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
