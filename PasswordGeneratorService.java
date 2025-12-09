package org.howard.edu.lsp.finale.question1;

import java.util.HashMap;
import java.util.Map;

/**
 * Service responsible for generating passwords based on a selected algorithm.
 * 
 * <p>This service exposes a simple public API:</p>
 * <ul>
 *   <li>{@link #getInstance()}</li>
 *   <li>{@link #setAlgorithm(String)}</li>
 *   <li>{@link #generatePassword(int)}</li>
 * </ul>
 *
 * <p>Only a single shared instance of this service exists (Singleton pattern),
 * and the password-generation behavior is delegated to interchangeable
 * strategy objects (Strategy pattern).</p>
 */
public class PasswordGeneratorService {

    /*
     * ============================================================
     *  Design Pattern Documentation
     * ============================================================
     *
     * 1. Patterns used:
     *    - Singleton:
     *      The PasswordGeneratorService is implemented as a Singleton
     *      (a single, globally shared instance) via a private constructor
     *      and a public static getInstance() method.
     *
     *    - Strategy:
     *      The actual password-generation logic is encapsulated in
     *      separate classes implementing the PasswordGenerationStrategy
     *      interface (e.g., BasicPasswordStrategy, EnhancedPasswordStrategy,
     *      LettersPasswordStrategy). The service holds a reference to a
     *      strategy and delegates generation to it.
     *
     *    - Simple Factory (within the service):
     *      A small factory mechanism maps string names (e.g., "basic",
     *      "enhanced", "letters") to concrete strategy instances using
     *      a Map. This allows the caller to select an algorithm at run
     *      time via setAlgorithm(String).
     *
     * 2. Why these patterns are appropriate:
     *    - Singleton ensures there is a single, shared access point to
     *      password generation across the entire application, matching
     *      the requirement that only one instance may exist and that
     *      clients share the same configuration.
     *
     *    - Strategy directly supports the requirement that password-
     *      generation behavior be swappable and extensible. New
     *      algorithms can be introduced by creating new strategy
     *      implementations without changing client code, which relies
     *      only on the stable service API.
     *
     *    - The internal Simple Factory simplifies algorithm selection
     *      by name. The caller specifies a string (e.g., "basic"), and
     *      the service looks up the appropriate strategy. This keeps
     *      clients decoupled from concrete classes and satisfies the
     *      run-time selection requirement.
     *
     *  Overall, this combination of patterns meets the stated goals:
     *  multiple approaches, run-time selection, future expansion,
     *  swappable behavior, and a single shared access point.
     * ============================================================
     */

    /** Singleton instance of the PasswordGeneratorService. */
    private static final PasswordGeneratorService INSTANCE = new PasswordGeneratorService();

    /** Registry mapping algorithm names to strategy implementations. */
    private final Map<String, PasswordGenerationStrategy> algorithms = new HashMap<>();

    /** Currently selected algorithm strategy. */
    private PasswordGenerationStrategy currentStrategy;

    /**
     * Private constructor to enforce Singleton behavior.
     * 
     * <p>Registers all built-in algorithms.</p>
     */
    private PasswordGeneratorService() {
        // Register built-in algorithms.
        algorithms.put("basic", new BasicPasswordStrategy());
        algorithms.put("enhanced", new EnhancedPasswordStrategy());
        algorithms.put("letters", new LettersPasswordStrategy());
    }

    /**
     * Retrieve the single shared instance of the service.
     *
     * @return the global {@code PasswordGeneratorService} instance
     */
    public static PasswordGeneratorService getInstance() {
        return INSTANCE;
    }

    /**
     * Select the algorithm to use for subsequent password generation.
     *
     * @param name the algorithm name; supported values are:
     *             "basic", "enhanced", "letters"
     * @throws IllegalArgumentException if {@code name} is {@code null} or
     *                                  does not correspond to a supported
     *                                  algorithm
     */
    public synchronized void setAlgorithm(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Algorithm name must not be null.");
        }

        String key = name.toLowerCase();
        PasswordGenerationStrategy strategy = algorithms.get(key);
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown algorithm: " + name);
        }
        this.currentStrategy = strategy;
    }

    /**
     * Generate a password of the given length using the currently selected
     * algorithm.
     *
     * @param length the requested password length; must be positive
     * @return a generated password string
     *
     * @throws IllegalStateException if no algorithm has been selected via
     *                               {@link #setAlgorithm(String)} before
     *                               calling this method
     * @throws IllegalArgumentException if {@code length} is not positive
     */
    public synchronized String generatePassword(int length) {
        if (currentStrategy == null) {
            throw new IllegalStateException(
                    "No algorithm selected. Call setAlgorithm(String) before generating a password.");
        }
        // Delegate length validation to the strategy; strategies also check length.
        return currentStrategy.generate(length);
    }

    /**
     * Clear the currently selected algorithm.
     *
     * <p>This method is package-private and primarily intended for use in
     * unit tests to ensure that generatePassword(int) throws the correct
     * exception when an algorithm has not been configured.</p>
     */
    void clearAlgorithm() {
        this.currentStrategy = null;
    }
}
