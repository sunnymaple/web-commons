package com.seagetech.web.commons.view.mapper.provider;

import java.util.List;
import java.util.Map;

/**
 * 导入实现逻辑
 *
 * @author gdl
 * @date 2020/1/9 14:11
 * @company 矽甲（上海）信息科技有限公司
 */
public class DynamicImportProvider {
    private static final String INSERT_PRE = "INSERT INTO ";
    private static final String LEFT_BRACKET = " (";
    private static final String RIGHT_BRACKET = ") ";
    private static final String VALUES = " VALUES ";
    private static final String COMMA = ",";
    private static final String RIGHT_MARK = "'";
    private static final String LEFT_MARK = "'";

    public String batchImport(List<Map<String, Object>> sqlMap, String tableName) throws Exception {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlPre = new StringBuilder();
        StringBuilder sqlColumn = new StringBuilder();
        StringBuilder sqlValue = new StringBuilder();
        sqlPre.append(INSERT_PRE)
                .append(tableName)
                .append(LEFT_BRACKET);
        for (int i = 0; i < sqlMap.size(); i++) {
            if (i == 0) {
                for (Map.Entry<String, Object> stringObjectEntry : sqlMap.get(i).entrySet()) {
                    sqlColumn.append(stringObjectEntry.getKey())
                            .append(COMMA);
                }
                sqlColumn.delete(sqlColumn.length() - 1, sqlColumn.length());
                sqlColumn.append(RIGHT_BRACKET);
            }
            sqlValue.append(LEFT_BRACKET);
            for (Map.Entry<String, Object> stringObjectEntry : sqlMap.get(i).entrySet()) {
                sqlValue.append(LEFT_MARK)
                        .append(stringObjectEntry.getValue())
                        .append(RIGHT_MARK)
                        .append(COMMA);
            }

            sqlValue.delete(sqlValue.length() - 1, sqlValue.length());
            sqlValue.append(RIGHT_BRACKET)
                    .append(COMMA);

        }
        sqlValue.delete(sqlValue.length() - 1, sqlValue.length());
        sql.append(sqlPre)
                .append(sqlColumn)
                .append(VALUES)
                .append(sqlValue);
        return sql.toString();
    }

}
