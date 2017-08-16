package model;

public class StyleFilter extends StringColumnFilter {
    public StyleFilter(String style) {
        super(style, 4, "style");
    }
}
