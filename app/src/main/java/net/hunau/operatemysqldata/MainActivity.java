package net.hunau.operatemysqldata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.hunau.entity.DBLink;

public class MainActivity extends AppCompatActivity {
    private final String REMOTE_IP = "110.53.162.165";
    private final String URL = "jdbc:mysql://" + REMOTE_IP + "/test?";
    private final String USER = "root";
    private final String PASSWORD = "sx123456AaBb";

    private Connection conn;
    private Button onInsert;
    private Button onDelete;
    private Button onUpdate;
    private Button onQuery;
    protected TextView display;

    private TextView testId;
    private TextView testName;
    private TextView testPwd;
    private TextView testSex;
    private RadioButton isUsed;

    DBLink util = new DBLink();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onInsert = (Button) findViewById(R.id.onInsert);
        onDelete = (Button) findViewById(R.id.onDelete);
        onUpdate = (Button) findViewById(R.id.onUpdate);
        onQuery = (Button) findViewById(R.id.onQuery);
        display = (TextView) findViewById(R.id.display);

        testId = (TextView) findViewById(R.id.testId);
        testName = (TextView) findViewById(R.id.testName);
        testPwd = (TextView) findViewById(R.id.testPwd);
        testSex = (TextView) findViewById(R.id.testSex);
        isUsed = (RadioButton) findViewById(R.id.yes);

        onConn();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
            } finally {
                conn = null;
            }
        }
    }

    public void onConn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = util.openConnection(URL, USER, PASSWORD);
                Log.i("onConn", "onConn");
            }
        }).start();
    }

    public void onInsert(View view) {
        final String id = testId.getText().toString();
        final String name = testName.getText().toString();
        final String pwd = testPwd.getText().toString();
        final String sex = testSex.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "insert into tb_user(name,pwd,sexy,isused) values('" + name + "','" + pwd + "','" + sex + "',1)";
                util.execSQL(conn, sql);
                Log.i("onInsert", "onInsert");
            }
        }).start();
        display.setText("插入记录成功！");
    }

    public void onDelete(View view) {
        final String id = testId.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "delete from tb_user where id=" + id;
                util.execSQL(conn, sql);
                Log.i("onDelete", "onDelete");
            }
        }).start();
        display.setText("删除记录成功！");
    }

    public void onUpdate(View view) {
        final String id = testId.getText().toString();
        final String name = testName.getText().toString();
        final String pwd = testPwd.getText().toString();
        final String sex = testSex.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "update tb_user set name='" + name + "',pwd = '" + pwd + "',sex = '" + sex + "' where id = " + id;
                util.execSQL(conn, sql);
                Log.i("onUpdate", "onUpdate");
            }
        }).start();
        display.setText("更新记录成功！");
    }

    public void onQuery(View view) {
        final String[] str = {""};
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list = util.query(conn, "select * from tb_user");
                Log.i("onQuery", "onQuery");
                for (String i : list) {
                    str[0] += i + "\n";
                }
            }
        }).start();
        display.setText(str[0]);
    }

    public void findById(View view) {
        final String id = testId.getText().toString();
        final String[] str = {""};
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list = util.query(conn, "select * from tb_user where id = " + id);
                for (String i : list) {
                    System.out.println(i);
                    str[0] += i + "\n";
                }
                Log.i("onQuery", "onQuery");
            }
        }).start();
        display.setText(str[0]);
    }
}
