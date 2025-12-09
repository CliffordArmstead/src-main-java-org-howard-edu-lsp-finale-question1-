package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * Password generation strategy that produces passwords containing
 * letters only (A–Z, a–z).
 */
class LettersPasswordStrategy implements PasswordGenerationStrategy {

    private static final String LETTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";

    private final Random random = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be positive.");
        }

        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(LETTERS.length());
            builder.append(LETTERS.charAt(index));
        }
        return builder.toString();
    }
}
