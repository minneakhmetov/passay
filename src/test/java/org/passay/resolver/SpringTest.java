/* See LICENSE for licensing and NOTICE for copyright. */
package org.passay.resolver;

import java.util.ArrayList;
import org.passay.logic.PasswordData;
import org.passay.rule.validator.PasswordValidator;
import org.passay.rule.Rule;
import org.passay.rule.result.RuleResult;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * Unit test for Spring integration.
 *
 * @author  Middleware Services
 */
public class SpringTest
{


  /**
   * Attempts to load all Spring application context XML files to verify proper wiring.
   */
  @Test(groups = "passtest")
  public void testSpringWiring()
  {
    final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
      new String[] {"/spring-context.xml", });
    AssertJUnit.assertTrue(context.getBeanDefinitionCount() > 0);

    final PasswordValidator validator = new PasswordValidator(
      new ArrayList<>(context.getBeansOfType(Rule.class).values()));
    final PasswordData pd = new PasswordData("springtest");
    pd.setUsername("springuser");

    final RuleResult result = validator.validate(pd);
    AssertJUnit.assertNotNull(result);
  }
}
