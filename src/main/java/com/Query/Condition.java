package com.Query;

import java.util.ArrayList;
import java.util.List;

import com.Query.Enum.Default;

public class Condition {
    private Column field;            
    private String operator;         
    private Object value=Default.QUESTION_MARK;            
    private String logicalOperator;  
    private List<Condition> subConditions; 
    private boolean alias=false;
   // private  List<Column> fieldNames = new ArrayList<>();

    public Condition(Column field, String operator,Boolean alias) {
        this.field = field;
        this.operator = operator;
        this.alias=alias;
        this.logicalOperator = null;  
        this.subConditions = null;
    }
    public Condition(Column field,String operator,Object value)
    {
    	this.field=field;
    	 this.operator = operator;
    	 this.value=value;
         this.alias=true;
         this.logicalOperator = null;  
         this.subConditions = null;
    }

    public Condition(String logicalOperator) {
        this.field = null;           
        this.operator = null;        
        this.logicalOperator = logicalOperator;
        this.subConditions = new ArrayList<>();
    }

	public Condition addSubCondition(Condition condition) {
        if (this.subConditions != null) {
            this.subConditions.add(condition);
        } else {
            throw new IllegalStateException("Cannot add sub-conditions to a simple condition.");
        }
        return this;
    }
    
//    public List<Column> getFieldNames() {
//       
//         if (field != null && value==Default.QUESTION_MARK) {
//            fieldNames.add(field); 
//        }
//                if (subConditions != null &&  value==Default.QUESTION_MARK) {
//            for (Condition subCondition : subConditions) {
//                fieldNames.addAll(subCondition.getFieldNames());
//            }
//        }
//        
//        return fieldNames;
//    }

    @Override
    public String toString() {
        if (subConditions != null) {
            StringBuilder builder = new StringBuilder("(");
            int size=subConditions.size();
            for (int i = 0; i < size; i++) {
                builder.append(subConditions.get(i));
                if (i < size - 1) {
                    builder.append(" ").append(logicalOperator).append(" ");
                }
            }
            builder.append(")");
            return builder.toString();
        } else {
        	if(alias)
        		return field.getColumnNamesWithAlias() + " " + operator + " " + 
        	    (value instanceof Column ? ((Column) value).getColumnNamesWithAlias() : value.toString());
            return field + " " + operator + " " + ((Column) value).getColumnName();
        }
    }
}