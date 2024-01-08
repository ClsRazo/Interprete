package auxiliares;

import interprete.TablaSimbolos;
import interprete.Token;

import java.util.ArrayList;
import java.util.List;

public class ExprCallFunction extends Expression{
    final Expression callee;
    // final Token paren;
    final List<Expression> arguments;

    public ExprCallFunction(Expression callee, /*Token paren,*/ List<Expression> arguments) {
        this.callee = callee;
        // this.paren = paren;
        this.arguments = arguments;
    }

    @Override
    public Object solve(TablaSimbolos tabla){
        final ArrayList<Object> solvedExp=new ArrayList<>();
        //Que devuelve?
        for(Expression ex:arguments)
        {
            solvedExp.add(ex.solve(tabla));
        }
        return solvedExp;
    }
}
