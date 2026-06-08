package com.portal.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ValidationUtil – covers every rule from the task spec.
 *
 * Run with: mvn test
 */
@DisplayName("ValidationUtil")
class ValidationUtilTest {

    // ── Happy path ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("valid name + email + phone → no errors")
    void fullValidInput() {
        var result = ValidationUtil.validateContact("Jane Doe", "jane@example.com", "9876543210");
        assertTrue(result.isValid(), "Expected no validation errors");
    }

    @Test
    @DisplayName("valid name + email, no phone → no errors")
    void phoneIsOptional() {
        var result = ValidationUtil.validateContact("Bob", "bob@test.org", "");
        assertTrue(result.isValid(), "Phone is optional – empty value should pass");
    }

    // ── Name validation ───────────────────────────────────────────────────────

    @Nested
    @DisplayName("Name field")
    class NameTests {

        @Test
        @DisplayName("null name → error")
        void nullName() {
            var result = ValidationUtil.validateContact(null, "a@b.com", "");
            assertFalse(result.isValid());
            assertEquals("Please enter valid name", result.getError("name"));
        }

        @Test
        @DisplayName("blank name → error")
        void blankName() {
            var result = ValidationUtil.validateContact("   ", "a@b.com", "");
            assertFalse(result.isValid());
            assertNotNull(result.getError("name"));
        }

        @Test
        @DisplayName("single character → too short → error")
        void singleCharName() {
            var result = ValidationUtil.validateContact("A", "a@b.com", "");
            assertFalse(result.isValid());
            assertNotNull(result.getError("name"));
        }

        @Test
        @DisplayName("51-character name → too long → error")
        void tooLongName() {
            String longName = "A".repeat(51);
            var result = ValidationUtil.validateContact(longName, "a@b.com", "");
            assertFalse(result.isValid());
            assertNotNull(result.getError("name"));
        }

        @Test
        @DisplayName("exactly 2 characters → valid")
        void twoCharName() {
            var result = ValidationUtil.validateContact("Jo", "a@b.com", "");
            assertTrue(result.isValid());
        }

        @Test
        @DisplayName("exactly 50 characters → valid")
        void fiftyCharName() {
            var result = ValidationUtil.validateContact("A".repeat(50), "a@b.com", "");
            assertTrue(result.isValid());
        }
    }

    // ── Email validation ──────────────────────────────────────────────────────

    @Nested
    @DisplayName("Email field")
    class EmailTests {

        @ParameterizedTest(name = "valid email: {0}")
        @ValueSource(strings = {
            "user@example.com",
            "firstname.lastname@domain.co.uk",
            "user+tag@sub.domain.io",
            "a@b.org"
        })
        void validEmails(String email) {
            var result = ValidationUtil.validateContact("Test User", email, "");
            assertNull(result.getError("email"), "Expected no email error for: " + email);
        }

        @ParameterizedTest(name = "invalid email: {0}")
        @ValueSource(strings = {
            "notanemail",
            "@nodomain.com",
            "missing@",
            "spaces in@email.com",
            ""
        })
        void invalidEmails(String email) {
            var result = ValidationUtil.validateContact("Test User", email, "");
            assertEquals("Invalid email address", result.getError("email"));
        }
    }

    // ── Phone validation ──────────────────────────────────────────────────────

    @Nested
    @DisplayName("Phone field")
    class PhoneTests {

        @Test
        @DisplayName("10-digit phone → valid")
        void validPhone() {
            var result = ValidationUtil.validateContact("Jane", "j@test.com", "9876543210");
            assertNull(result.getError("phone"));
        }

        @Test
        @DisplayName("empty phone → valid (optional)")
        void emptyPhone() {
            var result = ValidationUtil.validateContact("Jane", "j@test.com", "");
            assertNull(result.getError("phone"));
        }

        @Test
        @DisplayName("9-digit phone → error")
        void shortPhone() {
            var result = ValidationUtil.validateContact("Jane", "j@test.com", "987654321");
            assertEquals("Use 10-digit format", result.getError("phone"));
        }

        @Test
        @DisplayName("11-digit phone → error")
        void longPhone() {
            var result = ValidationUtil.validateContact("Jane", "j@test.com", "98765432101");
            assertEquals("Use 10-digit format", result.getError("phone"));
        }

        @Test
        @DisplayName("phone with spaces/dashes → stripped → valid if 10 digits remain")
        void phoneWithFormatting() {
            var result = ValidationUtil.validateContact("Jane", "j@test.com", "987-654-3210");
            assertNull(result.getError("phone"),
                       "Formatting characters should be stripped before validation");
        }
    }

    // ── Sanitise helper ───────────────────────────────────────────────────────

    @Nested
    @DisplayName("sanitise()")
    class SanitiseTests {

        @Test
        @DisplayName("HTML special chars → escaped")
        void escapesHtml() {
            assertEquals("&lt;script&gt;alert(&#x27;xss&#x27;)&lt;/script&gt;",
                         ValidationUtil.sanitise("<script>alert('xss')</script>"));
        }

        @Test
        @DisplayName("null input → empty string")
        void nullInput() {
            assertEquals("", ValidationUtil.sanitise(null));
        }

        @Test
        @DisplayName("normal string → unchanged")
        void normalString() {
            assertEquals("Jane Doe", ValidationUtil.sanitise("  Jane Doe  "));
        }
    }

    // ── Multiple errors ───────────────────────────────────────────────────────

    @Test
    @DisplayName("all fields invalid → three errors")
    void allInvalid() {
        var result = ValidationUtil.validateContact("", "bad-email", "123");
        assertFalse(result.isValid());
        assertEquals(3, result.getErrors().size(),
                     "Expected errors for name, email, and phone");
    }
}
