package com.Query;


import java.util.Arrays;

public class Enum {

    public enum UserDetails {
        username,
        usermail,
        gender,
        phonenumber,
        birthday;

        public String getColumnName() {
            return this.name();
        }

        public static String[] getColumnNames() {
            return Arrays.stream(values())
                         .map(UserDetails::getColumnName)
                         .toArray(String[]::new);
        }
    }

    public enum AllMail {
        user_id,
        user_email,
        is_primary,
        email_verified,
        verify_token,
        expiry_time;

        public String getColumnName() {
            return this.name();
        }

        public static String[] getColumnNames() {
            return Arrays.stream(values())
                         .map(AllMail::getColumnName)
                         .toArray(String[]::new);
        }
    }

    public enum AllPhone {
        user_id,
        phone,
        is_primary;

        public String getColumnName() {
            return this.name();
        }

        public static String[] getColumnNames() {
            return Arrays.stream(values())
                         .map(AllPhone::getColumnName)
                         .toArray(String[]::new);
        }
    }

    public enum Categories {
        category_id,
        user_id,
        category_name;

        public String getColumnName() {
            return this.name();
        }

        public static String[] getColumnNames() {
            return Arrays.stream(values())
                         .map(Categories::getColumnName)
                         .toArray(String[]::new);
        }
    }

    public enum CategoryUsers {
        category_id,
        contact_id;

        public String getColumnName() {
            return this.name();
        }

        public static String[] getColumnNames() {
            return Arrays.stream(values())
                         .map(CategoryUsers::getColumnName)
                         .toArray(String[]::new);
        }
    }

    public enum ContactDetails {
        contact_id,
        user_id,
        name,
        mail,
        phonenumber,
        gender,
        birthday,
        location,
        created_time;

        
        public String getColumnName() {
            return this.name();
        }

        public static String[] getColumnNames() {
            return Arrays.stream(values())
                         .map(ContactDetails::getColumnName)
                         .toArray(String[]::new);
        }
    }

    public enum Credentials {
        user_id,
        password,
        flag;

        public String getColumnName() {
            return this.name();
        }

        public static String[] getColumnNames() {
            return Arrays.stream(values())
                         .map(Credentials::getColumnName)
                         .toArray(String[]::new);
        }
    }

    public enum Session {
        sessionid,
        user_id,
        created_time,
        last_accessed,
        expiry_time;


        public String getColumnName() {
            return this.name();
        }

        public static String[] getColumnNames() {
            return Arrays.stream(values())
                         .map(Session::getColumnName)
                         .toArray(String[]::new);
        }
    }
}
