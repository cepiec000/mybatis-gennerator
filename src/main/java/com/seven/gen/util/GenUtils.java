package com.seven.gen.util;

import com.seven.gen.bean.ColumnInfo;
import com.seven.gen.bean.TableInfo;
import com.seven.gen.config.GenConfig;
import org.apache.velocity.VelocityContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器 工具类
 */
public class GenUtils {

    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = getProjectPath();


    /**
     * 设置列信息
     */
    public static List<ColumnInfo> transColums(List<ColumnInfo> columns) {
        // 列信息
        List<ColumnInfo> columsList = new ArrayList<ColumnInfo>();
        for (ColumnInfo column : columns) {
            // 列名转换成Java属性名
            String attrName = StringUtils.convertToCamelCase(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));
            // 列的数据类型，转换成Java类型
            String attrType = CommonMap.javaTypeMap.get(column.getDataType());
            column.setAttrType(attrType);
            columsList.add(column);
        }
        return columsList;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static VelocityContext getVelocityContext(TableInfo table) {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        String packageName = GenConfig.getPackageBase();
        String corePackage = GenConfig.getCorePackageBase();
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("tableComment", replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey", table.getPrimaryKey());
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getClassname());
        velocityContext.put("moduleName", getModuleName(packageName));
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("package", packageName);
        velocityContext.put("corePackage", corePackage);
        velocityContext.put("author", GenConfig.getAuthor());
        velocityContext.put("datetime", DateUtils.getDate());
        velocityContext.put("beanPath", table.getBeanPath().equals("do") ? table.getBeanPath().toUpperCase() : table.getBeanPath());
        velocityContext.put("beanName", table.getBeanName());
        return velocityContext;
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/Entity.java.vm");
        templates.add("vm/Mapper.java.vm");
        templates.add("vm/Service.java.vm");
        templates.add("vm/ServiceImpl.java.vm");
        templates.add("vm/Controller.java.vm");
        templates.add("vm/Mapper.xml.vm");
        return templates;
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName) {
        return StringUtils.convertToCamelCase(tableName);
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, TableInfo table, String moduleName) {
        // 小写类名
        String classname = table.getClassname();
        // 大写类名
        String className = table.getClassName();
        String javaPath = PROJECT_PATH;
        if (StringUtils.isNotEmpty(classname)) {
//            javaPath += classname.replace("." , "/") + "/" ;
        }
        if (template.contains("Entity.java.vm")) {
            return javaPath + GenConfig.getBeanSuffix().toLowerCase() + "/" + table.getBeanName() + ".java";
        }
        if (template.contains("Mapper.java.vm")) {
            return javaPath + "mapper" + "/" + className + "Mapper.java";
        }
        if (template.contains("Service.java.vm")) {
            return javaPath + "service" + "/" + "I" + className + "Service.java";
        }
        if (template.contains("ServiceImpl.java.vm")) {
            return javaPath + "service" + "/" + className + "ServiceImpl.java";
        }
        if (template.contains("Controller.java.vm")) {
            return javaPath + "controller" + "/" + className + "Controller.java";
        }
        if (template.contains("Mapper.xml.vm")) {
            return javaPath + "mapper" + "/" + className + "Mapper.xml";
        }
        return null;
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
        return moduleName;
    }

    public static String getProjectPath() {
        String packageName = GenConfig.getPackageBase();
        StringBuffer projectPath = new StringBuffer();
        projectPath.append("main/java/");
        projectPath.append(packageName.replace(".", "/"));
        projectPath.append("/");
        return projectPath.toString();
    }

    public static String replaceKeyword(String keyword) {
        String keyName = keyword == null ? "null" : keyword.replaceAll("(?:表|信息)", "");
        return keyName;
    }

    public static void printFile(TableInfo table, String template, StringWriter sw) {
        String packageName = GenUtils.getProjectPath();
        String moduleName = GenUtils.getModuleName(packageName);
        String fileName = GenUtils.getFileName(template, table, moduleName);
        String filePath = GenConfig.getPath() + fileName;
        PrintStream in = null;
        FileOutputStream out = null;
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            out = new FileOutputStream(file, false);
            in = new PrintStream(out);
            in.print(sw.toString());
            System.out.println(fileName + " create success");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
