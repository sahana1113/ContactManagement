package com.Dao;

import java.sql.SQLException;
import java.util.List;

import com.Bean.*;
import com.Bean.BeanAudit;
import com.Query.Column;
import com.Query.Enum.AuditLog;
import com.Query.Enum.Tables;
import com.Query.QueryLayer;

public class DaoAudit {
	public static boolean insertAuditLog(BeanAudit auditLog){
        try {
			int k=QueryLayer.buildInsertQuery(Tables.AUDITLOG,
					auditLog, 
					null,
					new Column[] {AuditLog.table_name,AuditLog.record_key,AuditLog.created_time,AuditLog.previous_update_time,AuditLog.new_value,AuditLog.old_value,AuditLog.action});
			return k>0;
		} catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
	public static String selectPrevious(Column[]column,Tables table)
	{
		try {
			List<Bean>list=QueryLayer.buildSelectQuery(table, column, null, null, null, null, column);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
    
     
}
