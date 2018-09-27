package elements;

import visitors.JSONVisitor;

public class PrimitiveOrWrapper implements Element {
    String name, value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public PrimitiveOrWrapper(String name, String value) {
        this.name=name;
        this.value=value;
    }

    public String[] accept(JSONVisitor visitor) {
        return visitor.visit(this);
    }


}
