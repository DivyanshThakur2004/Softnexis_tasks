package com.portal.util;

import com.portal.model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple in-memory contact store backed by a synchronised list.
 *
 * This intentionally lives outside the HTTP session so that all
 * browser tabs/windows share the same contact list for the duration
 * of the server process.  Task 3 will swap this out for a proper DB.
 *
 * Thread-safety: all mutating methods are synchronised on the instance.
 */
public class ContactStore {

    // Singleton – one store per JVM process
    private static final ContactStore INSTANCE = new ContactStore();

    private final List<Contact> contacts = new ArrayList<>();

    private ContactStore() {
        // Seed with a couple of sample entries so the list isn't empty on first load
        contacts.add(new Contact("Alice Johnson",  "alice@example.com",  "9876543210"));
        contacts.add(new Contact("Bob Martinez",   "bob@example.com",    "8765432109"));
        contacts.add(new Contact("Carol Williams", "carol@example.com",  ""));
    }

    public static ContactStore getInstance() {
        return INSTANCE;
    }

    /** Returns an unmodifiable snapshot – callers cannot mutate the backing list. */
    public synchronized List<Contact> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(contacts));
    }

    public synchronized void add(Contact contact) {
        contacts.add(contact);
    }

    public synchronized boolean deleteById(String id) {
        return contacts.removeIf(c -> c.getId().equals(id));
    }

    public synchronized int count() {
        return contacts.size();
    }

    /**
     * Simple case-insensitive search across name, email and phone.
     * Returns an empty list (never null) when the query is blank.
     */
    public synchronized List<Contact> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAll();
        }
        String q = query.trim().toLowerCase();
        List<Contact> results = new ArrayList<>();
        for (Contact c : contacts) {
            if (contains(c.getName(),  q) ||
                contains(c.getEmail(), q) ||
                contains(c.getPhone(), q)) {
                results.add(c);
            }
        }
        return Collections.unmodifiableList(results);
    }

    private boolean contains(String field, String query) {
        return field != null && field.toLowerCase().contains(query);
    }
}
