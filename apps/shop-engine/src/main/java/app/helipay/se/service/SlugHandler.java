package app.helipay.se.service;

import java.text.Normalizer;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class SlugHandler
{

  private SlugHandler() {
  }

  private static final Pattern PATTERN_NON_ASCII = Pattern.compile("[^\\p{ASCII}]+");
  private static final Pattern PATTERN_HYPHEN_SEPARATOR = Pattern.compile("[\\s+]+");
  private static final Pattern PATTERN_TRIM_DASH = Pattern.compile("^-$");
  private static final String EMPTY = "";
  private static final String HYPHEN = "-";


  public static String getSlugger(String name, String slug) {
    return slug != null && !slug.isEmpty() ? slugger(slug) : slugger(name);
  }

  public static String getSlugger(String slugOrName) {
    return slugger(slugOrName);
  }


  // https://github.com/slugify/slugify/blob/master/src/main/java/com/github/slugify/Slugify.java
  private static String slugger(final String text) {
    return Optional.of(text)
                   .map(String::trim)
                   .filter(Predicate.not(EMPTY::equals))
                   // at this line we can filter some specific words e.g, bad-words -> ""

                   .map(SlugHandler::normalize)
                   .map(str -> PATTERN_NON_ASCII.matcher(str).replaceAll(EMPTY))
                   .map(str -> PATTERN_HYPHEN_SEPARATOR.matcher(str).replaceAll(HYPHEN))
                   .map(str -> PATTERN_TRIM_DASH.matcher(str).replaceAll(EMPTY))
                   .map(String::toLowerCase)
//                   .map(str -> str.concat(HYPHEN + Instant.now().getEpochSecond()))
                   .orElse(EMPTY);
  }



  private static String enLanguageProvider(String text) {
    return Optional.of(text).stream().map(String::toLowerCase).findAny().orElse(EMPTY);
  }

  private static String grLanguageProvider() {
    return "";
  }

  private static String faLanguageProvider() {
    return "";
  }

  private static String normalize(final String input) {
    return Normalizer.normalize(input, Normalizer.Form.NFKD);
  }
}