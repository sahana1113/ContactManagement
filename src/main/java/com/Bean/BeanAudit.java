package com.Bean;

import java.util.Map;

public class BeanAudit {
    private long auditId;                
    private String tableName;            
    private String recordKey;            
    private long createdTime;            
    private Long previousUpdateTime;     
    private String changedData;          

    // Default constructor
    public BeanAudit() {}

    // Parameterized constructor
    public BeanAudit(long auditId, String tableName, String recordKey, long createdTime,Long previousUpdateTime, String changedData) {
        this.auditId = auditId;
        this.tableName = tableName;
        this.recordKey = recordKey;
        this.createdTime = createdTime;
        this.previousUpdateTime = previousUpdateTime;
        this.changedData = changedData;
    }

    // Getters and Setters
    public long getAuditId() {
        return auditId;
    }

    public void setAuditId(long auditId) {
        this.auditId = auditId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getPreviousUpdateTime() {
        return previousUpdateTime;
    }

    public void setPreviousUpdateTime(Long previousUpdateTime) {
        this.previousUpdateTime = previousUpdateTime;
    }

    public String getChangedData() {
        return changedData;
    }

    public void setChangedData(String changedData) {
        this.changedData = changedData;
    }

//    public Map<String, Object> getChangedDataAsMap() {
//       return new Gson().fromJson(changedData, Map.class);
//    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "auditId=" + auditId +
                ", tableName='" + tableName + '\'' +
                ", recordKey='" + recordKey + '\'' +
                ", createdTime=" + createdTime +
                ", previousUpdateTime=" + previousUpdateTime +
                ", changedData='" + changedData + '\'' +
                '}';
    }
}
