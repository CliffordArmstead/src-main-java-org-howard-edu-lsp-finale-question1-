package org.howard.edu.lsp.finale.question1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test suite for {@link PasswordGeneratorService}.
 */
public class PasswordGeneratorServiceTest {

    private PasswordGeneratorService service;

    @BeforeEach
    public void setup() {
        service = PasswordGeneratorService.getInstance();
        // Ensure we start each test without a previously selected algorithm.
        service.clearAlgorithm();
    }

    @Test
    public void checkInstanceNotNull() {
        // verify that 'service' is not null
        assertNotNull(service, "PasswordGeneratorService instance should not be null.");
    }

    @Test
    public void checkSingleInstanceBehavior() {
        PasswordGeneratorService second = PasswordGeneratorService.getInstance();

        // Verify that both 'service' (created in @BeforeEach)
        // and 'second' refer to the EXACT same object in memory.
        assertSame(service, second,
                "getInstance() must always return the same Singleton instance.");
    }

    @Test
    public void generateWithoutSettingAlgorithmThrowsException() {
        PasswordGeneratorService s = PasswordGeneratorService.getInstance();
        // Ensure no algorithm is selected
        s.clearAlgorithm();

        // verify correct exception behavior
        assertThrows(IllegalStateException.class,
                () -> s.generatePassword(5),
                "Calling generatePassword without selecting an algorithm "
                        + "must throw IllegalStateException.");
    }

    @Test
    public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
        service.setAlgorithm("basic");
        String p = service.generatePassword(10);

        // verify required behavior
        assertNotNull(p, "Generated password should not be null.");
        assertEquals(10, p.length(), "Basic algorithm should generate the correct length.");

        for (char c : p.toCharArray()) {
            assertTrue(Character.isDigit(c),
                    "Basic algorithm must generate digits only, but found: " + c);
        }
    }

    @Test
    public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
        service.setAlgorithm("enhanced");
        String p = service.generatePassword(12);

        // verify required behavior
        assertNotNull(p, "Generated password should not be null.");
        assertEquals(12, p.length(), "Enhanced algorithm should generate the correct length.");

        for (char c : p.toCharArray()) {
            assertTrue(isAlphaNumeric(c),
                    "Enhanced algorithm must generate alphanumeric characters only, but found: " + c);
        }
    }

    @Test
    public void lettersAlgorithmGeneratesLettersOnly() {
        service.setAlgorithm("letters");
        String p = service.generatePassword(8);

        // verify required behavior
        assertNotNull(p, "Generated password should not be null.");
        assertEquals(8, p.length(), "Letters algorithm should generate the correct length.");

        for (char c : p.toCharArray()) {
            assertTrue(Character.isLetter(c),
                    "Letters algorithm must generate letters only, but found: " + c);
        }
    }

    @Test
    public void switchingAlgorithmsChangesBehavior() {
        service.setAlgorithm("basic");
        String p1 = service.generatePassword(10);

        service.setAlgorithm("letters");
        String p2 = service.generatePassword(10);

        service.setAlgorithm("enhanced");
        String p3 = service.generatePassword(10);

        // verify correct behavior characteristics of each algorithm

        // Basic: digits only
        assertEquals(10, p1.length(), "Basic password length must be 10.");
        for (char c : p1.toCharArray()) {
            assertTrue(Character.isDigit(c),
                    "Basic algorithm must generate digits only, but found: " + c);
        }

        // Letters: letters only
        assertEquals(10, p2.length(), "Letters password length must be 10.");
        for (char c : p2.toCharArray()) {
            assertTrue(Character.isLetter(c),
                    "Letters algorithm must generate letters only, but found: " + c);
        }

        // Enhanced: alphanumeric
        assertEquals(10, p3.length(), "Enhanced password length must be 10.");
        for (char c : p3.toCharArray()) {
            assertTrue(isAlphaNumeric(c),
                    "Enhanced algorithm must generate alphanumeric characters only, but found: " + c);
        }
    }

    /**
     * Helper method to determine whether a character is alphanumeric (A–Z, a–z, 0–9).
     *
     * @param c the character to test
     * @return {@code true} if the character is a letter or digit; {@code false} otherwise
     */
    private boolean isAlphaNumeric(char c) {
        return Character.isLetter(c) || Character.isDigit(c);
    }
}
