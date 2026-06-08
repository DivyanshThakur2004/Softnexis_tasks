package com.contactmanager.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtil {

    private static DataSource dataSource;

    public static DataSource getDataSource() {

        try {

            if (dataSource == null) {

                Context ctx = new InitialContext();

                dataSource =
                        (DataSource) ctx.lookup(
                                "java:comp/env/jdbc/ContactDB");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSource;
    }
}