package com.Query;

import java.util.ArrayList;
import java.util.List;

public class Condition {
    private Column field;            
    private String operator;         
    private String value="?";            
    private String logicalOperator;  
    private List<Condition> subConditions; 

    public Condition(Column field, String operator,Boolean alias) {
    	if(alias) {
    		this.field=field.getColumnNamesWithAlias();
    	}
        this.field = field;
        this.operator = operator;
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
    
    public List<Column> getFieldNames() {
        List<Column> fieldNames = new ArrayList<>();
                if (field != null) {
            fieldNames.add(field); 
        }
                if (subConditions != null) {
            for (Condition subCondition : subConditions) {
                fieldNames.addAll(subCondition.getFieldNames());
            }
        }
        
        return fieldNames;
    }

    @Override
    public String toString() {
        if (subConditions != null) {
            StringBuilder builder = new StringBuilder("(");
            for (int i = 0; i < subConditions.size(); i++) {
                builder.append(subConditions.get(i));
                if (i < subConditions.size() - 1) {
                    builder.append(" ").append(logicalOperator).append(" ");
                }
            }
            builder.append(")");
            return builder.toString();
        } else {
            return field + " " + operator + " " + value;
        }
    }
}