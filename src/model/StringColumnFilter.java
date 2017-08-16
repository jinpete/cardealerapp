package model;

import javax.sql.RowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;
import java.sql.SQLException;

abstract class StringColumnFilter implements Predicate {
    private String filterVal;
    private int colID;
    private String colName;

    public StringColumnFilter(String filterVal, int colID, String colName) {
        this.filterVal = filterVal;
        this.colID = colID;
        this.colName = colName;
    }

    public boolean evaluate(RowSet rowSet) {
        if (rowSet == null) {
            return false;
        }

        FilteredRowSet filteredRowSet = (FilteredRowSet) rowSet;
        boolean eval = false;
        try {
            String val = filteredRowSet.getString(colID);
            if (val.equalsIgnoreCase(filterVal))
                eval = true;
        } catch (SQLException e) {
            return false;
        }
        return eval;
    }

    public boolean evaluate(Object value, String colName) {
        boolean eval = true;
        if (colName.equalsIgnoreCase(this.colName)) {
            String val = (String) value;
            if (val.equalsIgnoreCase(filterVal))
                eval = true;
            else
                eval = false;
        }
        return eval;
    }

    public boolean evaluate(Object value, int colNum) {
        boolean eval = true;
        if (colID == colNum) {
            String val = (String) value;
            if (val.equalsIgnoreCase(filterVal))
                eval = true;
            else
                eval = false;
        }
        return eval;
    }
}