package tool;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.FilteredRowSetImpl;

import javax.sql.rowset.FilteredRowSet;
import java.sql.*;

public class DbOps {
    private final String DB_URL = "jdbc:derby:CarDealerDB;user=me;password=passwd";
    private Connection conn;
    private String sqlText;

    // keep track of connection status
    private boolean dbConnected = false;

    private void connectDb() throws SQLException {
        conn = DriverManager.getConnection(DB_URL);
        dbConnected = true;
    }

    public void closeDb() {
        if (dbConnected) {
            try {
                conn.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            finally {
                dbConnected = false;
            }
        }
    }

    public void getSql(String selectSqlType) {
        switch (selectSqlType) {
            case "automobile":
                sqlText = "select automobileID, make, model, style, condition, " +
                        "modelyear, price, mileage " +
                        "from automobile";
                break;
            case "inventory":
                sqlText = "select * from inventory";
                break;
            case "sales":
                sqlText = "select * from sales";
                break;
        }
    }

    public void getMakeList() {
        sqlText = "select distinct make from automobile order by make";
    }

    public void getStyleList() {
        sqlText = "select distinct style from automobile order by style";
    }

    public FilteredRowSet execQuery() throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        FilteredRowSet filteredRowSet = new FilteredRowSetImpl();

        try {
            connectDb();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sqlText);

            filteredRowSet.populate(resultSet);
        }
        catch (SQLException ex) {
            System.out.println("There was a problem executing SQL query.");
            throw ex;
        }
        finally {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            closeDb();
        }
        return filteredRowSet;
    }

    public void execUpdate(String query) throws SQLException {
        Statement statement = null;

        try {
            connectDb();
            statement = conn.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException ex) {
            System.out.println("There was a problem with update SQL statement.");
            throw ex;
        }
        finally {
            if (statement != null)
                statement.close();
            closeDb();
        }
    }
}
