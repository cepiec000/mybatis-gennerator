package com.seven.gen.bean;


import java.util.Date;
import java.util.List;

/**
 * ry 数据库表
 *
 */
public class TableInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 表的主键列信息
     */
    private ColumnInfo primaryKey;

    /**
     * 表的列名(不包含主键)
     */
    private List<ColumnInfo> columns;

    /**
     * 类名(第一个字母大写)
     */
    private String className;

    /**
     * 类名(第一个字母小写)
     */
    private String classname;

    /**
     * 根据bean后缀来生成bean目录
     **/
    private String beanPath;

    private String beanName;

    private Date createTime;
    private Date updateTime;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment==null?"":tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public ColumnInfo getColumnsLast() {
        ColumnInfo columnInfo = null;
        if (columns != null && columns.size() > 0) {
            columnInfo = columns.get(0);
        }
        return columnInfo;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public String getBeanName() {
        if (this.getBeanPath() == null) {
            this.beanName = className;
        } else if (this.getBeanPath().equalsIgnoreCase("DO")) {
            this.beanName = className  + "DO" ;
        } else if (this.getBeanPath().equalsIgnoreCase("Bean")) {
            this.beanName = className  + "Bean" ;
        } else if (this.getBeanPath().equalsIgnoreCase("Entity")) {
            this.beanName = className  + "Entity" ;
        } else {
            this.beanName = this.className;
        }
        return this.beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public ColumnInfo getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ColumnInfo primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBeanPath() {
        return beanPath;
    }

    public void setBeanPath(String beanPath) {
        this.beanPath = beanPath;
        setBeanName(beanPath);
    }

    @Override
    public String toString() {
        return "TableInfo [tableName=" + tableName + ", tableComment=" + tableComment + ", primaryKey=" + primaryKey
                + ", columns=" + columns + ", className=" + className + ", classname=" + classname + "]" ;
    }
}
