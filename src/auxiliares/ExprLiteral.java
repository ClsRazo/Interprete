package auxiliares;

import interprete.TablaSimbolos;

public class ExprLiteral extends Expression {
    final Object value;

    public ExprLiteral(Object value) {
        this.value = value;
    }

    @Override
    public Object solve(TablaSimbolos tabla){
        return value;
    }
}
