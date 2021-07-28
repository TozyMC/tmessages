package xyz.tozymc.spigot.tmessages.chat;

import org.bukkit.entity.Player;
import xyz.tozymc.spigot.tmessages.util.Formatter;

import java.util.Map;

public class ChatFormatter extends Formatter {

  private final ChatFeature feature;
  private String message;

  public ChatFormatter(ChatFeature feature) {
    super(feature.getFormat());
    this.feature = feature;
  }

  public ChatFormatter message(String message) {
    this.message = message;
    return this;
  }

  @Override
  public ChatFormatter player(Player player) {
    return (ChatFormatter) super.player(player);
  }

  @Override
  public ChatFormatter placeholder(String placeholder, Object replacement) {
    return (ChatFormatter) super.placeholder(placeholder, replacement);
  }

  @Override
  public ChatFormatter placeholders(Map<String, Object> placeholders) {
    return (ChatFormatter) super.placeholders(placeholders);
  }

  @Override
  public String format() {
    removeFeaturePrefix();
    placeholders.put("message", message);

    return super.format();
  }

  private void removeFeaturePrefix() {
    String prefix = feature.getPrefix();
    if (prefix != null && prefix.length() < message.length()) {
      message = message.substring(prefix.length());
    }
  }

  public ChatFeature getFeature() {
    return feature;
  }
}
