/* See LICENSE for licensing and NOTICE for copyright. */
package org.passay.rule;

import org.passay.logic.MatchBehavior;
import org.passay.logic.PasswordData;
import org.passay.util.PasswordUtils;
import org.passay.rule.result.RuleResult;
import org.passay.rule.result.RuleResultMetadata;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Rule for determining if a password contains whitespace characters. Whitespace is defined as tab (0x09), line feed
 * (0x0A), vertical tab (0x0B), form feed (0x0C), carriage return (0x0D), and space (0x20).
 *
 * @author  Middleware Services
 */
public class WhitespaceRule implements Rule
{

  /** Error code for whitespace rule violation. */
  public static final String ERROR_CODE = "ILLEGAL_WHITESPACE";

  /** Characters: TAB,LF,VT,FF,CR,Space. */
  protected static final char[] CHARS = {(byte) 0x09, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0x0D, (byte) 0x20};

  /** Whether to report all whitespace matches or just the first. */
  protected boolean reportAllFailures;

  /** Stores the whitespace characters that are allowed. */
  private final char[] whitespaceCharacters;

  /** Where to match whitespace. */
  private final MatchBehavior matchBehavior;


  /**
   * Creates a new whitespace rule.
   */
  public WhitespaceRule()
  {
    this(CHARS, MatchBehavior.Contains, true);
  }


  /**
   * Creates a new whitespace rule.
   *
   * @param  behavior  how to match whitespace
   */
  public WhitespaceRule(final MatchBehavior behavior)
  {
    this(CHARS, behavior, true);
  }


  /**
   * Creates a new whitespace rule.
   *
   * @param  chars  characters that are whitespace
   */
  public WhitespaceRule(final char[] chars)
  {
    this(chars, MatchBehavior.Contains, true);
  }


  /**
   * Creates a new whitespace rule.
   *
   * @param  behavior  how to match whitespace
   * @param  reportAll  whether to report all matches or just the first
   */
  public WhitespaceRule(final MatchBehavior behavior, final boolean reportAll)
  {
    this(CHARS, behavior, reportAll);
  }


  /**
   * Creates a new whitespace rule.
   *
   * @param  chars  whitespace characters
   * @param  behavior  how to match whitespace
   */
  public WhitespaceRule(final char[] chars, final MatchBehavior behavior)
  {
    this(chars, behavior, true);
  }


  /**
   * Creates a new whitespace rule.
   *
   * @param  chars  whitespace characters
   * @param  reportAll  whether to report all matches or just the first
   */
  public WhitespaceRule(final char[] chars, final boolean reportAll)
  {
    this(chars, MatchBehavior.Contains, reportAll);
  }


  /**
   * Creates a new whitespace rule.
   *
   * @param  chars  whitespace characters
   * @param  behavior  how to match whitespace
   * @param  reportAll  whether to report all matches or just the first
   */
  public WhitespaceRule(final char[] chars, final MatchBehavior behavior, final boolean reportAll)
  {
    for (char c : chars) {
      if (!Character.isWhitespace(c)) {
        throw new IllegalArgumentException("Character '" + c + "' is not whitespace");
      }
    }
    whitespaceCharacters = chars;
    matchBehavior = behavior;
    reportAllFailures = reportAll;
  }


  /**
   * Returns the whitespace characters for this rule.
   *
   * @return  whitespace characters
   */
  public char[] getWhitespaceCharacters()
  {
    return whitespaceCharacters;
  }


  /**
   * Returns the match behavior for this rule.
   *
   * @return  match behavior
   */
  public MatchBehavior getMatchBehavior()
  {
    return matchBehavior;
  }


  @Override
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult();
    final String text = passwordData.getPassword();
    for (char c : whitespaceCharacters) {
      if (matchBehavior.match(text, c)) {
        result.addError(ERROR_CODE, createRuleResultDetailParameters(c));
        if (!reportAllFailures) {
          break;
        }
      }
    }
    result.setMetadata(createRuleResultMetadata(passwordData));
    return result;
  }


  /**
   * Creates the parameter data for the rule result detail.
   *
   * @param  c  whitespace character
   *
   * @return  map of parameter name to value
   */
  protected Map<String, Object> createRuleResultDetailParameters(final char c)
  {
    final Map<String, Object> m = new LinkedHashMap<>();
    m.put("whitespaceCharacter", c);
    m.put("matchBehavior", matchBehavior);
    return m;
  }


  /**
   * Creates the rule result metadata.
   *
   * @param  password  data used for metadata creation
   *
   * @return  rule result metadata
   */
  protected RuleResultMetadata createRuleResultMetadata(final PasswordData password)
  {
    return new RuleResultMetadata(
      RuleResultMetadata.CountCategory.Whitespace,
      PasswordUtils.countMatchingCharacters(String.valueOf(whitespaceCharacters), password.getPassword()));
  }


  @Override
  public String toString()
  {
    return
      String.format(
        "%s@%h::reportAllFailures=%s,matchBehavior=%s,whitespaceCharacters=%s",
        getClass().getName(),
        hashCode(),
        reportAllFailures,
        matchBehavior,
        Arrays.toString(whitespaceCharacters));
  }
}
