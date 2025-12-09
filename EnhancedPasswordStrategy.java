package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * Password generation strategy that produces passwords containing
 * upper-case letters, lower-case letters, and digits using
 * {@link java.security.SecureRandom}.
 */
class EnhancedPasswordStrategy implements PasswordGenerationStrategy {

    private static final String ALLOWED =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789";

    private final SecureRandom secureRandom = new SecureRandom();

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
            int index = secureRandom.nextInt(ALLOWED.length());
            builder.append(ALLOWED.charAt(index));
        }
        return builder.toString();
    }
}
