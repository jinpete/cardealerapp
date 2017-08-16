package model;

import javax.sql.RowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;
import java.sql.SQLException;

abstract class NumericColumnFilter implements Predicate {
    private double minVal;
    private double maxVal;
    private int colID;
    private String colName;

    public NumericColumnFilter(double minVal, double maxVal, int colID, String colName) {
        this.minVal = minVal;
        this.maxVal = maxVal;
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
            double colVal = filteredRowSet.getDouble(colID);
            if ((colVal >= minVal) && (colVal <= maxVal)) {
                eval = true;
            }
        } catch (SQLException e) {
            return false;
        }
        return eval;
    }

    public boolean evaluate(Object value, String colName) {
        boolean eval = true;
        if (colName.equalsIgnoreCase(this.colName)) {
            double colVal = (Double) value;
            if (colVal >= minVal && colVal <= maxVal) {
                eval = true;
            } else {
                eval = false;
            }
        }
        return eval;
    }

    public boolean evaluate(Object value, int colNum) {
        boolean eval = true;
        if (colID == colNum) {
            double colVal = (Double) value;
            if (colVal >= minVal && colVal <= maxVal)
                eval = true;
            else
                eval = false;
        }
        return eval;
    }
}