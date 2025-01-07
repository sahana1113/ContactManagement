package com.Audit;

import org.json.JSONObject;

import com.Bean.BeanAudit;
import com.Query.Enum.Tables;

public class AuditLogService {
    public BeanAudit createAuditLog(Tables tableName, JSONObject recordKey,Long previousUpdateTime,Long updatedTime, JSONObject oldValues, JSONObject newValues,String action) {
    		BeanAudit auditLog = new BeanAudit();
    		auditLog.setTableName(tableName.getTableName());
    		auditLog.setRecordKey(recordKey.toString()); 
    		auditLog.setCreatedTime(System.currentTimeMillis()/1000);
    		auditLog.setPreviousUpdateTime(previousUpdateTime);
            auditLog.setOld_value(oldValues != null ? oldValues.toString() : null);
            auditLog.setNew_value(newValues.toString());
            auditLog.setAction(action);
    		return auditLog;
   }
}
