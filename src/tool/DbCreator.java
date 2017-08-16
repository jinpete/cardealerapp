package tool;
import java.sql.*;

public class DbCreator {
    private static final String DB_URL = "jdbc:derby:CarDealerDB;create=true;user=me;password=passwd";
    private static Connection conn;
    private static String sqlText;
    // keep track of connection status
    private static boolean dbConnected = false;

    public static void connectDb() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        conn = DriverManager.getConnection(DB_URL);
        dbConnected = true;
    }

    public static void closeDb() {
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

    public static void createAutomobileTable() throws SQLException {
        boolean tableExists = getTableExists("AUTOMOBILE");
        if(!tableExists) {
            sqlText = "create table automobile (" +
                    "automobileID integer not null generated always as identity (start with 1, increment by 1)," +
                    "make varchar(20) not null," +
                    "model varchar(20) not null," +
                    "style varchar(20) not null," +
                    "condition varchar(5) not null," +
                    "modelyear int not null," +
                    "price double not null," +
                    "mileage int not null," +
                    "primary key (automobileID))";
            System.out.println("Creating employee table\n...");
            executeDdl();
        }
    }

    public static void insertAutomobiles() throws SQLException {
        int rowCount = getTableRowCount("AUTOMOBILE");
        if (rowCount == 0) {
            sqlText = "insert into automobile(make, model, style, condition, modelyear, price, mileage) values" +
                    "('Ford','Explorer','SUV','New',2017,31999,25)," +
                    "('Ford','F150','Truck','New',2017,28999,18)," +
                    "('Toyota','Camry','Sedan','New',2017,22999,16)," +
                    "('Honda','Accord','Sedan','New',2017,24999,7)," +
                    "('Ford','Fusion','Sedan','New',2017,21999,10)," +
                    "('Toyota','Highlander','SUV','New',2017,35999,5)," +
                    "('Honda','Odyssey','Minivan','New',2017,28999,15)," +
                    "('Honda','Pilot','SUV','New',2017,34999,9)," +
                    "('Honda','Ridgeline','Truck','New',2017,26999,8)," +
                    "('Honda','Sienna','Minivan','New',2017,28999,2)," +
                    "('Toyota','Tundra','Truck','New',2017,37999,4)," +
                    "('Subaru','Impreza','Sedan','New',2017,24999,16)," +
                    "('Kia','Sorento','SUV','New',2017,29999,10)," +
                    "('Audi','A4','Sedan','Used',2010,10999,68547)," +
                    "('BMW','530i','Sedan','Used',2005,8999,152450)," +
                    "('Ford','Explorer','SUV','Used',2015,24599,20150)," +
                    "('Jeep','Wrangler','SUV','Used',2013,18999,45905)," +
                    "('Acura','MDX','SUV','Used',2011,17599,89521)," +
                    "('Lexus','GS300','Sedan','Used',1995,3199,201542)," +
                    "('Honda','Civic','Sedan','Used',1999,2299,185945)";

                    executeDdl();
        }
    }

    public static void createInventoryTable() {
        // to do
    }

    public static void createSalesTable() {
        // to do
    }

    private static boolean getTableExists(String tableName) throws SQLException {
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet resultSet = dbm.getTables(null, null, tableName, null);
        if (resultSet.next())
            return true;
        else
            return false;
    }

    private static int getTableRowCount(String tableName) throws SQLException {

        int returnRowCount = 0;
        String sql = "select count(*) from " + tableName;
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            returnRowCount = resultSet.getInt(1);
        }
        return returnRowCount;
    }

    private static void executeDdl() {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlText);
        }
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
}
