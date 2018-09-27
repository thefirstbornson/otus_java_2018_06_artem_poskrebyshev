package visitors;

import elements.PrimitiveOrWrapper;

import java.util.HashMap;

public interface JSONVisitor {
     String[] visit (PrimitiveOrWrapper primitiveOrWrapper);

}