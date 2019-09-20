package com.seven.gen;


import com.seven.gen.bean.TableInfo;
import com.seven.gen.service.GenServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ApplicationRun {

    public static void main(String[] args) {

        GenServiceImpl genService = new GenServiceImpl();
        List<String> tableNameList = new ArrayList<>();

        tableNameList.add("user_role");
        tableNameList.add("user_menu");
        tableNameList.add("user_role_menu");


        List<TableInfo> allTables = genService.selectTableList();
        List<TableInfo> tables = null;
        tables = allTables.stream().map(t -> {
            if (tableNameList.contains(t.getTableName())) {
                return t;
            }
            return null;
        }).collect(Collectors.toList());
        AtomicInteger count = new AtomicInteger(tables.size());
        System.out.println(">>>>>>开始生成代码<<<<<<<");
        for (TableInfo table : tables) {
            new Thread(() -> {
                genService.generatorCode(table.getTableName(), count);
            }).start();
        }

        while (count.get() > 0) {
            //skip
        }
        System.out.println(">>>>>>结束生成代码<<<<<<<");
    }

}
