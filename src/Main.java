import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // this uses h2 by default but change to match your database
        String databaseUrl = "jdbc:sqlite:android_bug.db";
        // create a connection source to our database
        ConnectionSource connectionSource =
                new JdbcConnectionSource(databaseUrl);

        Dao<BugInfo, String> bugDao = DaoManager.createDao(connectionSource, BugInfo.class);
        if (!bugDao.isTableExists()) {
            TableUtils.createTable(connectionSource, BugInfo.class);
        }
        //文件根路径
        String path = args[0];
        //获取根路径下所有文件夹
        int total = 0;
        File[] dirs = new File(path).listFiles();
        for (File dir : dirs) {
            //对于每一个文件夹，遍历其所有文件
            if (dir.isDirectory()) {
                System.out.println(String.format("开始扫描%s文件夹", dir.getName()));
                System.out.println("================================================");
                File[] files = dir.listFiles();
                for (File file : files) {
                    if (file.isFile() && !file.getName().endsWith("bug")) {
                        System.out.println(String.format("  读取%s-%s文件", dir.getName(), file.getName()));
                        //读取文件
                        String content = readFile(file);
                        //规范格式
                        //转化为Bean
                        List<BugInfo> bugs = format(content);
//                        for (BugInfo bug : bugs) {
//                            bugDao.createOrUpdate(bug);
//                        }
                        //   bugDao.create(bugs);
                        total += bugs.size();
                        System.out.println(String.format("  读取%d个bug", bugs.size()));
                        System.out.println("    ================================================");
                    }
                }
            }
        }
        System.out.println(total);

    }

    private static String readFile(File file) {
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        try {
            // System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));

            String tempString = "";
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if (line != 1) {
                    result.append(tempString).append("\r\n");
                }
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    return result.toString();
                } catch (IOException e1) {
                }
            }
        }

        return null;
    }

    private static List<BugInfo> format(String content) {
        Type listType = new TypeToken<LinkedList<BugInfo>>() {
        }.getType();
        return new Gson().fromJson(content, listType);
    }

}
