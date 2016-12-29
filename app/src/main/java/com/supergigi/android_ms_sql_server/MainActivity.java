package com.supergigi.android_ms_sql_server;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    String ipaddress = "";
    String db = "";
    String username = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MyAsyncTask().execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Connection connection = null;
            String ConnectionURL = null;
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL = "jdbc:jtds:sqlserver://" + ipaddress + ";"

                        + "databaseName=" + db + ";user=" + username
                        + ";password=" + password + ";";
                connection = DriverManager.getConnection(ConnectionURL);

                String query = "SELECT name FROM sys.databases";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                    String name = rs.getString(1);
                    Log.e(LOG_TAG, "name : " + name);
                }

            } catch (SQLException se) {
                Log.e("ERRO", se.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("ERRO", e.getMessage());
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage());
            }

            return null;
        }
    }
}
