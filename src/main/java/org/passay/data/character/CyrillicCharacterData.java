/* See LICENSE for licensing and NOTICE for copyright. */
package org.passay.data.character;

/**
 * Cyrillic character data.
 *
 * @author  Middleware Services
 */
public enum CyrillicCharacterData implements CharacterData {

  /**
   * Lower case characters.
   */
  LowerCase("INSUFFICIENT_LOWERCASE", "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"),

  /**
   * Upper case characters.
   */
  UpperCase("INSUFFICIENT_UPPERCASE", "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");

  /**
   * Error code.
   */
  private final String errorCode;

  /**
   * Characters.
   */
  private final String characters;

  /**
   * Creates a new cyrillic character data.
   *
   * @param code          Error code
   * @param charString    Characters as string
   */
  CyrillicCharacterData(final String code, final String charString)
  {
    errorCode = code;
    characters = charString;
  }

  @Override
  public String getErrorCode()
  {
    return errorCode;
  }

  @Override
  public String getCharacters()
  {
    return characters;
  }
}
