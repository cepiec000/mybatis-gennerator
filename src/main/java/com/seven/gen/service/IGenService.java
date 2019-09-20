package com.seven.gen.service;


import com.seven.gen.bean.TableInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 代码生成 服务层
 * 
 */
public interface IGenService {

    /**
     * 查询ry数据库表信息
     * 
     *            表信息
     * @return 数据库表列表
     */
    public List<TableInfo> selectTableList();

    /**
     * 生成代码
     * 
     * @param tableName
     *            表名称
     * @return 数据
     */
    public void generatorCode(String tableName,AtomicInteger i);


}
