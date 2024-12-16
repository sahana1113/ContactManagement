package com.Query;


import java.util.Arrays;

import com.Bean.*;

public class Enum { 
	public enum Tables {
	    ALL_MAIL("all_mail", "a", BeanMail.class),
	    ALL_PHONE("all_phone", "p", BeanPhone.class),
	    CATEGORIES("categories", "cat", BeanCategory.class),
	    CATEGORY_USERS("category_users", "cu", BeanCategory.class),
	    CONTACT_DETAILS("contactDetails", "cd", BeanContactDetails.class),
	    CREDENTIALS("credentials", "c", BeanUserDetails.class),
	    SESSION("session", "s", BeanSession.class),
	    USER_DETAILS("userDetails", "ud", BeanUserDetails.class),
		SERVERS("servers","ser",BeanServer.class);

	    private final String tableName;
	    private final String alias;
	    private final Class<?> clazz; 

	    Tables(String tableName, String alias, Class<?> clazz) {
	        this.tableName = tableName;
	        this.alias = alias;
	        this.clazz = clazz;
	    }

	    public String getTableName() {
	        return tableName;
	    }

	    public String getAlias() {
	        return alias;
	    }
       
	    public Class<?> getClazz() {
	        return clazz;
	    }

	    public String withAlias() {
	        return tableName + " " + alias;
	    }

	    public static Tables fromTableName(String tableName) {
	        for (Tables table : Tables.values()) {
	            if (table.getTableName().equalsIgnoreCase(tableName)) {
	                return table;
	            }
	        }
	        throw new IllegalArgumentException("Table name not found: " + tableName);
	    }
	}

    public enum UserDetails implements Column{
    	user_id,
        username,
        usermail,
        gender,
        phonenumber,
        birthday;

        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        
        private static final String alias = "ud";
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "userDetails";  
        }
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum AllMail implements Column{
        user_id,
        altMail,
        is_primary;

        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "a";
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "all_mail";  
        }
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum AllPhone implements Column{
        user_id,
        altPhone,
        is_primary;

        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "p";
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "all_phone";  
        }
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum Categories implements Column{
        category_id,
        user_id,
        category_name;

        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "cat";
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "categories";  
        }
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum CategoryUsers implements Column{
        category_id,
        contact_id;

        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "cu";
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "category_users";  
        }
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum ContactDetails implements Column{
        contact_id,
        user_id,
        name,
        mail,
        phonenumber,
        gender,
        birthday,
        location,
        created_time,
        is_archive;
        
        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "cd";
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "contactDetails";  
        }
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum Credentials implements Column{
        user_id,
        password,
        flag;

        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "c";
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "credentials";  
        }
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum Session implements Column{
        sessionid,
        user_id,
        created_time,
        last_accessed,
        expiry_time;


        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "s";
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "session";  
        }
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }
    
    public enum Test implements Column{
        sessionid,
        user_id,
        created_time,
        last_accessed,
        expiry_time;


        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "t";
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "test";  
        }
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }
    public enum Servers implements Column{
		id,
		ip_address,
		port_number,
		created_time;

    	public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "ser";
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "servers";  
        }
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    	
    }
}
