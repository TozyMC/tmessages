package xyz.tozymc.spigot.tmessages.util;

import com.google.common.collect.ImmutableMap;
import org.bukkit.entity.Player;
import xyz.tozymc.spigot.tmessages.TMessagesPlugin;

import java.util.Map;
import java.util.function.Function;

public final class Placeholders {

  private static final ImmutableMap<String, Function<Player, String>> PLAYER_PLACEHOLDER;

  static {
    PLAYER_PLACEHOLDER = ImmutableMap.of("player", Player::getName,
        "displayname", Player::getDisplayName,
        "name", Player::getName);
  }

  private Placeholders() {}

  public static String setPlaceholderApi(Player player, String text) {
    if (TMessagesPlugin.getInstance().isPapiSupport()) {
      return PlaceholderApiHelper.setPlaceholders(player, text);
    }
    return text;
  }

  public static void addPlayerPlaceholders(Player player, Map<String, Object> placeholders) {
    PLAYER_PLACEHOLDER.forEach((var, f) -> placeholders.put(var, f.apply(player)));
  }
}
