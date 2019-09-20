package com.seven.gen.mapper;


import com.seven.gen.bean.ColumnInfo;
import com.seven.gen.bean.TableInfo;

import java.util.List;

/**
 * 代码生成 数据层
 * 
 */
public interface GenMapper {

    /**
     * 查询ry数据库表信息
     * 
     *            表信息
     * @return 数据库表列表
     */
    public List<TableInfo> selectTableList();

    /**
     * 根据表名称查询信息
     * 
     * @param tableName
     *            表名称
     * @return 表信息
     */
    public TableInfo selectTableByName(String tableName);

    /**
     * 根据表名称查询列信息
     * 
     * @param tableName
     *            表名称
     * @return 列信息
     */
    public List<ColumnInfo> selectTableColumnsByName(String tableName);
}
