package com.contactmanager.dao;

import com.contactmanager.model.Contact;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {

    private final DataSource dataSource;

    public ContactDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addContact(Contact contact)
            throws SQLException {

        String sql =
                "INSERT INTO contacts(name,email,phone) VALUES(?,?,?)";

        try (
                Connection conn =
                        dataSource.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getEmail());
            stmt.setString(3, contact.getPhone());

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Contact> getAllContacts()
            throws SQLException {

        List<Contact> contacts =
                new ArrayList<>();

        String sql =
                "SELECT * FROM contacts ORDER BY id DESC";

        try (
                Connection conn =
                        dataSource.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                Contact contact =
                        new Contact();

                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setEmail(rs.getString("email"));
                contact.setPhone(rs.getString("phone"));

                contacts.add(contact);
            }
        }

        return contacts;
    }

    @Override
    public List<Contact> searchContacts(String keyword)
            throws SQLException {

        List<Contact> contacts =
                new ArrayList<>();

        String sql =
                """
                SELECT *
                FROM contacts
                WHERE LOWER(name)
                LIKE LOWER(?)
                OR LOWER(email)
                LIKE LOWER(?)
                """;

        try (
                Connection conn =
                        dataSource.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1,
                    "%" + keyword + "%");

            stmt.setString(2,
                    "%" + keyword + "%");

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                Contact c =
                        new Contact();

                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));

                contacts.add(c);
            }
        }

        return contacts;
    }
}