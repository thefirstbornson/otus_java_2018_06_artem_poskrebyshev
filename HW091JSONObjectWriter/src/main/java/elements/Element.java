package elements;

import visitors.JSONVisitor;

public interface Element {
    public String[] accept(JSONVisitor visitor);
    }
