package com.Query;


import java.util.Arrays;

public class Enum { 
	public enum Tables{
		ALL_MAIL("all_mail","a"),
		ALL_PHONE("all_phone","p"),
		CATEGORIES("categories","cat"),
		CATEGORY_USERS("category_users","cu"),
		CONTACT_DETAILS("contactDetails","cd"),
		CREDENTIALS("credentials","c"),
		SESSION("session","s"),
		USER_DETAILS("userDetails","ud");
		private final String tableName;
	    private final String alias;

	    Tables(String tableName, String alias) {
	        this.tableName = tableName;
	        this.alias = alias;
	    }
	    
	    public String getTableName() {
	        return tableName;
	    }

	    public String getAlias() {
	        return alias;
	    }
	    public String withAlias() {
	        return tableName + " " + alias;
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
        usermail,
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
        phonenumber,
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
        created_time;

        
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
}
