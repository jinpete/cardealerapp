package model;
//
//import javax.sql.RowSet;
//import javax.sql.rowset.FilteredRowSet;
//import java.sql.SQLException;

public class ManufacturerFilter extends StringColumnFilter {

    public ManufacturerFilter(String manufacturer) {
        super(manufacturer, 2, "make");
    }

//
//    public boolean evaluate(RowSet rowSet) {
//        if (rowSet == null) {
//            return false;
//        }
//
//        FilteredRowSet filteredRowSet = (FilteredRowSet) rowSet;
//        boolean eval = false;
//        try {
//            String val = filteredRowSet.getString(colID);
//            if (val.equalsIgnoreCase(filterVal))
//                eval = true;
//        } catch (SQLException e) {
//            return false;
//        }
//        return eval;
//    }
//
//    public boolean evaluate(Object value, String colName) {
//        boolean eval = true;
//        if (colName.equalsIgnoreCase(this.colName)) {
//            String val = ((String) value).toString();
//            if (val.equalsIgnoreCase(filterVal)) {
//                eval = true;
//            } else {
//                eval = false;
//            }
//        }
//        return eval;
//    }
//
//    public boolean evaluate(Object value, int colNum) {
//        boolean eval = true;
//        if (colID == colNum) {
//            String val = ((String) value).toString();
//            if (val.equalsIgnoreCase(filterVal)) {
//                eval = true;
//            } else {
//                eval = false;
//            }
//        }
//        return eval;
//    }
}