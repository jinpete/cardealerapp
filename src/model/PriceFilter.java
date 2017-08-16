package model;

public class PriceFilter extends NumericColumnFilter {
    public PriceFilter(double minPrice, double maxPrice) {
        super(minPrice, maxPrice, 7, "price");
    }
}