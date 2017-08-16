package model;

public class MileageFilter extends NumericColumnFilter {
    public MileageFilter(double maxMileage) {
        super(0,maxMileage, 8, "mileage");
    }
}
