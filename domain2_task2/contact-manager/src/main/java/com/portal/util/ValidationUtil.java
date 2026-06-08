package com.portal.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Centralises all form-input validation so the servlets stay thin.
 *
 * Usage:
 *   ValidationResult result = ValidationUtil.validateContact(name, email, phone);
 *   if (!result.isValid()) { ... result.getErrors() ... }
 */
public class ValidationUtil {

    // Pre-compiled patterns – reused across requests for efficiency
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[0-9]{10}$");

    private ValidationUtil() {
        // Utility class – no instantiation needed
    }

    /**
     * Validates name, email and (optional) phone.
     * Returns a result object carrying any field-level error messages.
     */
    public static ValidationResult validateContact(String name, String email, String phone) {
        // LinkedHashMap preserves insertion order so errors appear top-to-bottom
        Map<String, String> errors = new LinkedHashMap<>();

        // ── Name ────────────────────────────────────────────────────────────
        if (isBlank(name)) {
            errors.put("name", "Please enter valid name");
        } else if (name.trim().length() < 2 || name.trim().length() > 50) {
            errors.put("name", "Name must be between 2 and 50 characters");
        }

        // ── Email ───────────────────────────────────────────────────────────
        if (isBlank(email)) {
            errors.put("email", "Invalid email address");
        } else if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            errors.put("email", "Invalid email address");
        }

        // ── Phone (optional) ────────────────────────────────────────────────
        if (!isBlank(phone)) {
            // Strip common formatting characters before matching
            String digits = phone.replaceAll("[\\s\\-().+]", "");
            if (!PHONE_PATTERN.matcher(digits).matches()) {
                errors.put("phone", "Use 10-digit format");
            }
        }

        return new ValidationResult(errors);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Basic XSS sanitisation – escapes the five HTML special characters.
     * Used when we store user input so stored data is always safe to render.
     */
    public static String sanitise(String input) {
        if (input == null) return "";
        return input.trim()
                    .replace("&",  "&amp;")
                    .replace("<",  "&lt;")
                    .replace(">",  "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'",  "&#x27;");
    }

    // ── Inner result class ───────────────────────────────────────────────────

    public static class ValidationResult {
        private final Map<String, String> errors;

        public ValidationResult(Map<String, String> errors) {
            this.errors = errors;
        }

        public boolean isValid() {
            return errors.isEmpty();
        }

        public Map<String, String> getErrors() {
            return errors;
        }

        /** Convenience: first error message for a given field, or null. */
        public String getError(String field) {
            return errors.get(field);
        }
    }
}
