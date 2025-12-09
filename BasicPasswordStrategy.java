package org.howard.edu.lsp.finale.question1;

import java.util.Random;

/**
 * Password generation strategy that produces passwords containing
 * digits only (0–9) using {@link java.util.Random}.
 */
class BasicPasswordStrategy implements PasswordGenerationStrategy {

    private static final String DIGITS = "0123456789";
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
            int index = random.nextInt(DIGITS.length());
            builder.append(DIGITS.charAt(index));
        }
        return builder.toString();
    }
}
