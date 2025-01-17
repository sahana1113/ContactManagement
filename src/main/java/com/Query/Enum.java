package com.Query;


import java.util.Arrays;

import com.Bean.*;

public class Enum { 
	public enum Tables {
	    ALL_MAIL("all_mail", "a", BeanMail.class,"email_id"),
	    ALL_PHONE("all_phone", "p", BeanPhone.class,"phone_id"),
	    CATEGORIES("categories", "cat", BeanCategory.class,"category_id"),
	    CATEGORY_USERS("category_users", "cu", BeanCategory.class,"cat_id"),
	    CONTACT_DETAILS("contactDetails", "cd", BeanContactDetails.class,"contact_id"),
	    CREDENTIALS("credentials", "c", BeanUserDetails.class,"id"),
	    SESSION("session", "s", BeanSession.class,"session_id"),
	    USER_DETAILS("userDetails", "ud", BeanUserDetails.class,"user_id"),
		SERVERS("servers","ser",BeanServer.class,"server_id"),
		AUDITLOG("audit_log","al",BeanAudit.class,"audit_id");

	    private final String tableName;
	    private final String alias;
	    private final Class<?> clazz; 
	    private final String primary;

	    Tables(String tableName, String alias, Class<?> clazz,String primary) {
	        this.tableName = tableName;
	        this.alias = alias;
	        this.clazz = clazz;
	        this.primary=primary;
	    }
	    public String getPrimary()
	    {
	    	return primary;
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
        birthday,
        created_time,
        updated_time;

    	@Override
        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        
        public static Column getPrimary() {
        	return user_id;
        }
        
        private static final String alias = "ud";
        @Override
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "userDetails";  
        }
        @Override
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum AllMail implements Column{
    	email_id,
        user_id,
        altMail,
        is_primary,
        email_verified,
        verify_token,
        expiry_time,
        created_time,
        updated_time;

    	@Override
        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "a";
        @Override
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "all_mail";  
        }
        @Override
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum AllPhone implements Column{
    	phone_id,
        user_id,
        altPhone,
        is_primary,
        created_time,
        updated_time;

    	@Override
        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "p";
        @Override
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "all_phone";  
        }
        @Override
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum Categories implements Column{
        category_id,
        user_id,
        category_name,
        created_time,
        updated_time;

    	@Override
        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "cat";
        @Override
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "categories";  
        }
        @Override
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum CategoryUsers implements Column{
        category_id,
        contact_id,
        created_time,
        updated_time;

    	@Override
        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "cu";
        @Override
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "category_users";  
        }
        @Override
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
        is_archive,
        updated_time;
        
    	@Override
        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "cd";
        @Override
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "contactDetails";  
        }
        @Override
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }

    public enum Credentials implements Column{
    	id,
        user_id,
        password,
        flag,
        created_time,
        updated_time;

    	@Override
        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "c";
        @Override
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "credentials";  
        }
        @Override
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

    	@Override
        public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "s";
        @Override
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "session";  
        }
        @Override
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    }
    
    public enum Servers implements Column{
		id,
		ip_address,
		port_number,
		created_time;
    	@Override
    	public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "ser";
        @Override
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "servers";  
        }
        @Override
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    	
    }
    public enum Default implements Column{
    	QUESTION_MARK("?"),
    	FALSE("false"),
    	TRUE("true"),
    	NULL("NULL"),
    	CUSTOM("");
    	private String value;

        Default(String value) {
            this.value = value;
        }
        
        public Default setValue(String value) {
            if (this == CUSTOM) {
                this.value = value;
            } else {
                throw new UnsupportedOperationException("Cannot set value for predefined enum constants.");
            }
			return this;
        }

        @Override
        public String getColumnName() {
            return this.value;  
        }

		@Override
		public String getAlias() {
			return this.value;
		}

		@Override
		public String getColumnNamesWithAlias() {
			return this.value;
		}
    }
    public enum AuditLog implements Column{
		audit_id,
		table_name,
		record_key,
		created_time,
		new_value,
		old_value,
		action;
        
    	@Override
    	public String getColumnName() {
            return this.name();
        }

        public static Column[] getColumnNames() {
        	return Arrays.stream(values())
                    .toArray(Column[]::new);
        }
        private static final String alias = "al";
        @Override
        public String getAlias() {
            return alias;
        }
        public static String getTableName() {
            return "audit_log";  
        }
        @Override
        public String getColumnNamesWithAlias() {
            return alias+"."+this.name();
        }
    	
    }
}
