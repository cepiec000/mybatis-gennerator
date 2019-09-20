package com.seven.gen.service;

import com.seven.gen.bean.ColumnInfo;
import com.seven.gen.bean.TableInfo;
import com.seven.gen.config.GenConfig;
import com.seven.gen.mapper.GenMapper;
import com.seven.gen.util.GenUtils;
import com.seven.gen.util.MyBatisUtil;
import com.seven.gen.util.StringUtils;
import com.seven.gen.util.VelocityInitializer;
import org.apache.ibatis.session.SqlSession;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 代码生成 服务层处理
 */
public class GenServiceImpl implements IGenService {

    public GenServiceImpl() {
        SqlSession sqlSession = MyBatisUtil.getSession();
        GenMapper mapper = sqlSession.getMapper(GenMapper.class);
        this.genMapper = mapper;
    }

    private GenMapper genMapper;


    /**
     * 查询ry数据库表信息
     * <p>
     * 表信息
     *
     * @return 数据库表列表
     */
    @Override
    public List<TableInfo> selectTableList() {
        return genMapper.selectTableList();
    }

    /**
     * 生成代码
     *
     * @param tableName 表名称
     * @return 数据
     */
    @Override
    public void generatorCode(String tableName, AtomicInteger i) {
        // 查询表信息
        TableInfo table = genMapper.selectTableByName(tableName);
        // 查询列信息
        List<ColumnInfo> columns = genMapper.selectTableColumnsByName(tableName);
        // 生成代码
        generatorCode(table, columns);
        i.decrementAndGet();
    }


    /**
     * 生成代码
     */
    public void generatorCode(TableInfo table, List<ColumnInfo> columns) {
        // 表名转换成Java属性名
        table.setBeanPath(GenConfig.getBeanSuffix().toLowerCase());
        String className = GenUtils.tableToJava(table.getTableName());
        table.setClassName(className);
        table.setClassname(StringUtils.uncapitalize(className));
        // 列信息
        table.setColumns(GenUtils.transColums(columns));
        // 设置主键
        table.setPrimaryKey(table.getColumnsLast());
        VelocityInitializer.initVelocity();


        VelocityContext context = GenUtils.getVelocityContext(table);
        // 获取模板列表
        List<String> templates = GenUtils.getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            GenUtils.printFile(table, template, sw);
        }
    }
}
