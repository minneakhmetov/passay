/* See LICENSE for licensing and NOTICE for copyright. */
package org.passay;

import java.util.Arrays;

import org.passay.logic.PasswordData;
import org.passay.rule.RepeatCharactersRule;
import org.testng.annotations.DataProvider;

/**
 * Unit test for {@link RepeatCharactersRule}.
 *
 * @author  Amichai Rothman
 */
public class RepeatCharactersRuleTest extends AbstractRuleTest
{

  /**
   * @return  Test data.
   *
   * @throws  Exception  On test data generation failure.
   */
  @DataProvider(name = "passwords")
  public Object[][] passwords()
    throws Exception
  {
    return
      new Object[][] {
        // test valid password
        {
          new RepeatCharactersRule(),
          new PasswordData("p4zRcv8#n65"),
          null,
        },
        // test repeating character
        {
          new RepeatCharactersRule(),
          new PasswordData("p4&&&&&#n65"),
          codes(RepeatCharactersRule.ERROR_CODE),
        },
        // test longer repeating character
        {
          new RepeatCharactersRule(),
          new PasswordData("p4vvvvvvv#n65"),
          codes(RepeatCharactersRule.ERROR_CODE),
        },

        // test valid password for long sequence
        {
          new RepeatCharactersRule(7),
          new PasswordData("p4zRcv8#n65"),
          null,
        },
        // test long sequence with short repeat
        {
          new RepeatCharactersRule(7),
          new PasswordData("p4&&&&&#n65"),
          null,
        },
        // test long sequence with long repeat
        {
          new RepeatCharactersRule(7),
          new PasswordData("p4vvvvvvv#n65"),
          codes(RepeatCharactersRule.ERROR_CODE),
        },
        // test multiple matches
        {
          new RepeatCharactersRule(),
          new PasswordData("p4&&&&&#n65FFFFF"),
          codes(RepeatCharactersRule.ERROR_CODE),
        },
        // test multiple matches with allowed count
        {
          new RepeatCharactersRule(5, 3),
          new PasswordData("p4&&&&&#n65FFFFF"),
          null,
        },
        // test single match when max is two
        {
          new RepeatCharactersRule(5, 2),
          new PasswordData("p4&&&&&#n65FFFF"),
          null,
        },
        // test two matches when max is two
        {
          new RepeatCharactersRule(5, 2),
          new PasswordData("p4&&&&&#n65FFFFF"),
          codes(RepeatCharactersRule.ERROR_CODE),
        },
        // test two matches when max is more than two
        {
          new RepeatCharactersRule(5, 3),
          new PasswordData("p4&&&&&#n65FFFFF"),
          null,
        },
      };
  }


  /**
   * @return  Test data.
   *
   * @throws  Exception  On test data generation failure.
   */
  @DataProvider(name = "messages")
  public Object[][] messages()
    throws Exception
  {
    final String message = "Password contains %d sequences of %d or more repeated characters, but only %d allowed: %s.";
    return
      new Object[][] {
        {
          new RepeatCharactersRule(2, 2),
          new PasswordData("paaxvbbdkccx"),
          new String[] {String.format(message, 3, 2, 2, Arrays.asList("aa", "bb", "cc"))},
        },
      };
  }
}
