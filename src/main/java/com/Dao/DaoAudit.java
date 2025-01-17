package com.Dao;

import java.sql.SQLException;
import java.util.List;

import com.Bean.*;
import com.Query.Column;
import com.Query.Condition;
import com.Query.Enum.AuditLog;
import com.Query.Enum.Tables;
import com.Query.QueryLayer;

public class DaoAudit {
	public static boolean insertAuditLog(BeanAudit auditLog){
        try {
        	auditLog.setCreatedTime(System.currentTimeMillis()/1000);
			int k=QueryLayer.buildInsertQuery(Tables.AUDITLOG,
					auditLog,
					new Column[] {AuditLog.table_name,AuditLog.record_key,AuditLog.created_time,AuditLog.new_value,AuditLog.old_value,AuditLog.action});
			return k>0;
		} catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
	public static <T> Bean selectPrevious(Column[]column,Tables tableName,int record_id)
	{
		List<Bean>list=null;
		try {
			list=(List<Bean>) QueryLayer.buildSelectQuery(tableName, column, new Condition(tableName.getPrimary(),"=",record_id), tableName.getClazz(), null, null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
    
     
}
