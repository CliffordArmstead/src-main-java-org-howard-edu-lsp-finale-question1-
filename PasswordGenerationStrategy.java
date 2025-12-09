# src-main-java-org-howard-edu-lsp-finale-question1-
package org.howard.edu.lsp.finale.question1;

/**
 * Strategy interface for password generation algorithms.
 * 
 * <p>Each concrete implementation encapsulates a distinct algorithm for
 * generating password strings.</p>
 */
public interface PasswordGenerationStrategy {
    /**
     * Generate a password of the specified length.
     *
     * @param length the desired length of the generated password; must be positive
     * @return a generated password string
     * @throws IllegalArgumentException if {@code length} is not positive
     */
    String generate(int length);
}
  
