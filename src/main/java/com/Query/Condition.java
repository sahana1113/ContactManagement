package com.Query;

import java.util.ArrayList;
import java.util.List;

import com.Query.Enum.Default;

public class Condition {
    private Column field;  
    private String field1;
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
    public Condition(String field1,String operator,Object value)
    {
    	this.field1=field1;
    	 this.operator = operator;
    	 this.value=value;
         this.alias=false;
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
    

    public Column getField() {
		return field;
	}
	public void setField(Column field) {
		this.field = field;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getLogicalOperator() {
		return logicalOperator;
	}
	public void setLogicalOperator(String logicalOperator) {
		this.logicalOperator = logicalOperator;
	}
	public List<Condition> getSubConditions() {
		return subConditions;
	}
	public void setSubConditions(List<Condition> subConditions) {
		this.subConditions = subConditions;
	}
	public boolean isAlias() {
		return alias;
	}
	public void setAlias(boolean alias) {
		this.alias = alias;
	}
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
            return (field!=null?field:field1) + " " + operator + " " +  
        	    (value instanceof Column ? ((Column) value).getColumnNamesWithAlias() : value.toString());

        }
    }
}