/* See LICENSE for licensing and NOTICE for copyright. */
package org.passay.data.sequence;

import org.passay.logic.CharacterSequence;

/**
 * Archaic cyrillic character sequence.
 *
 * @author Middleware Services
 */
public enum ArchaicCyrillicSequenceData implements SequenceData {

  /**
   * Alphabetical sequence.
   */
  Alphabetical(
    "ILLEGAL_ALPHABETICAL_SEQUENCE",
    new CharacterSequence[] {
      new CharacterSequence("абвгдеёжзийклмнопрстуфхцчшщъыьэюяіѣѳѵ", "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯІѢѲѴ"),
    });

  /**
   * Error code.
   */
  private final String errorCode;

  /**
   * Character sequences.
   */
  private final CharacterSequence[] sequences;

  /**
   * Creates new cyrillic sequence data.
   *
   * @param code Error code used for message resolution.
   * @param seqs One or more character sequences.
   */
  ArchaicCyrillicSequenceData(final String code, final CharacterSequence[] seqs)
  {
    errorCode = code;
    sequences = seqs;
  }

  @Override
  public String getErrorCode()
  {
    return errorCode;
  }

  @Override
  public CharacterSequence[] getSequences()
  {
    return sequences;
  }
}
