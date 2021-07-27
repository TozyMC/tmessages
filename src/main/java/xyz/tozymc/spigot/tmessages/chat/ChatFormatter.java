package xyz.tozymc.spigot.tmessages.chat;

import org.bukkit.entity.Player;
import xyz.tozymc.spigot.api.title.util.Colors;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatFormatter {

  private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{(\\w+)}");

  private final ChatFeature feature;
  private final Map<String, Object> placeholders = new HashMap<>();
  private String format;
  private String text;
  private Player sender;

  public ChatFormatter(ChatFeature feature) {
    this.feature = feature;
    this.format = feature.getFormat();
  }

  public ChatFormatter text(String text) {
    this.text = text;
    return this;
  }

  public ChatFormatter sender(Player sender) {
    this.sender = sender;
    return this;
  }

  public ChatFormatter placeholder(String placeholder, Object replacement) {
    this.placeholders.put(placeholder, replacement);
    return this;
  }

  public ChatFormatter placeholders(Map<String, Object> placeholders) {
    this.placeholders.putAll(placeholders);
    return this;
  }


  public String format() {
    if (text == null) {
      throw new NullPointerException("Text cannot be null");
    }
    removeFeaturePrefix();
    placeholders.put("message", text);

    if (sender != null) {
      setPlaceholders(sender);

      placeholders.put("player", sender.getName());
      placeholders.put("name", sender.getName());
      placeholders.put("displayname", sender.getDisplayName());
    }

    colorFormat();
    format0();
    return format;
  }

  private void removeFeaturePrefix() {
    String prefix = feature.getPrefix();
    if (prefix != null && prefix.length() < text.length()) {
      text = text.substring(prefix.length());
    }
  }

  private void setPlaceholders(Player sender) {
    // TODO: Add placeholder api support
  }

  private void colorFormat() {
    format = Colors.color(format);
  }

  private void format0() {
    Matcher matcher = PLACEHOLDER_PATTERN.matcher(format);
    boolean result = matcher.find();
    if (!result) {
      return;
    }
    StringBuffer buff = new StringBuffer();
    do {
      String replacement = String.valueOf(placeholders.get(matcher.group(1)));
      matcher.appendReplacement(buff, replacement);
      result = matcher.find();
    } while (result);
    matcher.appendTail(buff);
    format = buff.toString();
  }

  public ChatFeature getFeature() {
    return feature;
  }
}
