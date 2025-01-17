package com.Bean;

import java.util.Map;

import com.Query.Condition;

public class BeanAudit implements Bean{
    private long auditId;                
    private String table_name;
    private String record_key;
    private long created_time;        
    private String new_value;
    private String old_value;
    private String action;
    // Default constructor
    public BeanAudit() {}

    // Parameterized constructor
    public BeanAudit(long auditId, String table_name, String record_key, long created_time, String new_value, String old_value,String action) {
		this.auditId = auditId;
		this.table_name = table_name;
		this.record_key = record_key;
		this.created_time = created_time;
		this.new_value = new_value;
		this.old_value = old_value;
		this.action=action;
	}
    
    // Getters and Setters
    public long getAuditId() {
        return auditId;
    }

	public void setAuditId(long auditId) {
        this.auditId = auditId;
    }

    public String getTableName() {
        return table_name;
    }

    public void setTableName(String tableName) {
        this.table_name = tableName;
    }

    public String getRecordKey() {
        return record_key;
    }

    public void setRecordKey(String recordKey) {
        this.record_key = recordKey;
    }

    public long getCreatedTime() {
        return created_time;
    }

    public void setCreatedTime(long createdTime) {
        this.created_time = createdTime;
    }
    
    public String getNew_value() {
		return new_value;
	}

	public void setNew_value(String new_value) {
		this.new_value = new_value;
	}

	public String getOld_value() {
		return old_value;
	}

	public void setOld_value(String oldValues) {
		this.old_value = oldValues != null ? oldValues.toString() : null;
	}
	

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "BeanAudit [auditId=" + auditId + ", table_name=" + table_name + ", record_key=" + record_key
				+ ", created_time=" + created_time   + ", new_value="
				+ new_value + ", old_value=" + old_value + ", action=" + action + "]";
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getUser_id() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setUser_id(int user_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPrimaryId() {
		return (int) auditId;
	}

	@Override
	public long getUpdated_time() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPrimaryColumn() {
		// TODO Auto-generated method stub
		return "audit_id";
	}

}
