package com.Query;

import java.util.ArrayList;
import java.util.List;

import com.Query.Enum.Tables;

public class Join {
	private String joinType; 
    private Tables tableName;
    private List<Condition> joinConditions;

    public Join(String joinType, Tables tableName) {
        this.joinType = joinType.toUpperCase();
        this.tableName = tableName;
        this.joinConditions = new ArrayList<>();
    }

    public Join on(Column leftColumn, String operator, Column rightColumn) {
        this.joinConditions.add(new Condition(leftColumn, operator, rightColumn));
        return this;
    }
    public Join on(Column leftColumn, String operator, Object rightColumn) {
        this.joinConditions.add(new Condition(leftColumn, operator, rightColumn));
        return this;
    }

    public String buildJoin() {
        StringBuilder joinBuilder = new StringBuilder();

        joinBuilder.append(joinType).append(" JOIN ").append(tableName.withAlias());

        if (!joinConditions.isEmpty()) {
            joinBuilder.append(" ON ");
            for (int i = 0; i < joinConditions.size(); i++) {
                Condition condition = joinConditions.get(i);
                if (i > 0) {
                    joinBuilder.append(" AND "); 
                }
                joinBuilder.append(condition);
            }
        }

        return joinBuilder.toString();
    }

}
