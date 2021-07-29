package xyz.tozymc.spigot.tmessages.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;
import xyz.tozymc.spigot.api.title.util.Colors;

public class Formatter {

  private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{(\\w+)}");
  protected final Map<String, Object> placeholders = new HashMap<>();
  protected String text;
  protected Player player;

  public Formatter(String text) {
    this.text = text;
  }

  public Formatter player(Player player) {
    this.player = player;
    return this;
  }

  public Formatter placeholder(String placeholder, Object replacement) {
    this.placeholders.put(placeholder, replacement);
    return this;
  }

  public Formatter placeholders(Map<String, Object> placeholders) {
    this.placeholders.putAll(placeholders);
    return this;
  }

  public String format() {
    if (text == null) {
      throw new NullPointerException("Text cannot be null");
    }

    if (player != null) {
      Placeholders.addPlayerPlaceholders(player, placeholders);
      text = Placeholders.setPlaceholderApi(player, text);
    }

    color();
    format0();
    return text;
  }

  private void color() {
    text = Colors.color(text);
  }

  private void format0() {
    Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);
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
    text = buff.toString();
  }
}
