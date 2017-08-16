package model;

public class ConditionFilter extends StringColumnFilter{
    public ConditionFilter(String condition) {
        super(condition, 5, "condition");
    }
}
