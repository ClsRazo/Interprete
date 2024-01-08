package nousados;

import interprete.TablaSimbolos;
import auxiliares.Expression;
import interprete.Token;

public class ExprSuper extends Expression {
    // final Token keyword;
    final Token method;

    ExprSuper(Token method) {
        // this.keyword = keyword;
        this.method = method;
    }

    @Override
    public Object solve(TablaSimbolos tabla){
        return null;
    }
}