package com.Audit;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;


import com.Bean.BeanAudit;
import com.Query.Enum.Tables;

public class AuditLogService {
    public static BeanAudit createAuditLog(Tables tableName, com.Query.Condition recordKey,Long updatedTime, JSONObject oldValues, JSONObject newValues,String action) {
    		BeanAudit auditLog = new BeanAudit();
    		auditLog.setTableName(tableName.getTableName());
    		auditLog.setRecordKey(recordKey.toString()); 
    		auditLog.setCreatedTime(System.currentTimeMillis()/1000);
            auditLog.setOld_value(oldValues != null ? oldValues.toString() : null);
            auditLog.setNew_value(newValues.toString());
            auditLog.setAction(action);
    		return auditLog;
   }
    public static String prepareValue(Map<String, Object> map) {
        String newValue = convertMapToJsonString(map);
        return newValue;
    }
    
    public static String convertMapToJsonString(Map<String, Object> map) {
        StringBuilder jsonString = new StringBuilder("{");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            jsonString.append("\"").append(key).append("\":");
            
            if (value instanceof String) {
                jsonString.append("\"").append(value).append("\"");
            } else {
                jsonString.append(value);
            }

            jsonString.append(", ");
        }

        if (jsonString.length() > 1) {
            jsonString.setLength(jsonString.length() - 2);
        }
        jsonString.append("}");

        return jsonString.toString();
    }
    
    public static String mapFields(Object obj) {
        Map<String, Object> fieldMap = new HashMap<>();

        if (obj == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); 
            try {
                String fieldName = field.getName();
                Object fieldValue = field.get(obj);

                if (isFieldUnset(field, fieldValue)) {
                    continue;
                }
                
                fieldMap.put(fieldName, fieldValue);
            } catch (IllegalAccessException e) {
                System.err.println("Unable to access field: " + field.getName());
            }
        }
        	return prepareValue(fieldMap);
    }
    private static boolean isFieldUnset(Field field, Object value) {
        if (value == null) {
            return true; 
        }

        Class<?> fieldType = field.getType();
        if (fieldType.isPrimitive()) {
            if (fieldType == boolean.class && !(boolean) value) return true;
            if (fieldType == char.class && (char) value == '\u0000') return true;
            if ((fieldType == byte.class || fieldType == short.class ||
                 fieldType == int.class || fieldType == long.class) && ((Number) value).longValue() == 0)
                return true;
            if ((fieldType == float.class || fieldType == double.class) && ((Number) value).doubleValue() == 0.0)
                return true;
        }

        return false; 
    }
}
