import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UpdateNote {

    public static final String BASEURL = "https://android-review.googlesource.com/q/";
    public static void main(String[] args) throws SQLException, IOException {

        //sqlite
        //ConnectionSource connection = setupSqlite();
        //mysql
        JdbcConnectionSource connection = setupMySql();
        Dao<BugInfo, Integer> bugDao = DaoManager.createDao(connection, BugInfo.class);

        while (true) {

            // get our query builder from the DAO
            QueryBuilder<BugInfo, Integer> queryBuilder =
                    bugDao.queryBuilder();
            queryBuilder.where().eq("checked", "0").and().gt("uid",1736);
            queryBuilder.orderBy("uid",true);
            queryBuilder.limit(1L);
            PreparedQuery<BugInfo> preparedQuery = queryBuilder.prepare();
            List<BugInfo> bugList = bugDao.query(preparedQuery);

            show(bugList);

            BugInfo bugInfo = bugList.get(0);
            Scanner sc = new Scanner(System.in);
            System.out.println("title:");
            String title = sc.nextLine();
            System.out.println("desc:");
            String desc = sc.nextLine();
            System.out.println("note:");
            int note = sc.nextInt();
            bugInfo.setTitle(title);
            bugInfo.setDes(desc);
            bugInfo.setNote(note);
            bugInfo.setChecked(1);
            bugDao.update(bugInfo);
        }
    }

    private static void show(List<BugInfo> bugList) {

        for (BugInfo bug : bugList) {
            String[] temp = bug.getId().split("~");
            int size = temp.length;
            String url = BASEURL+temp[size-1];
            System.out.println(bug.getUid()+"  "+url);
            try {
                Runtime.getRuntime().exec(String.format("cmd   /c   start   %s ",url));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }




    }

    private static JdbcConnectionSource setupMySql() throws SQLException {
        String databaseUrl = "jdbc:mysql://10.131.252.160/android_bug";
        JdbcConnectionSource connection = new JdbcConnectionSource(databaseUrl);
        connection.setUsername("root");
        connection.setPassword("root");

        return connection;
    }


}
