package xyz.tozymc.spigot.tmessages.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public final class PlaceholderApiHelper {

  private PlaceholderApiHelper() {}

  public static String setPlaceholders(Player player, String text) {
    return PlaceholderAPI.setPlaceholders(player, text);
  }
}
