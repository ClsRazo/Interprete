package nousados;

import interprete.TablaSimbolos;
import interprete.Token;

import java.util.List;

import auxiliares.ExprVariable;
import auxiliares.Statement;
import auxiliares.StmtFunction;

public class StmtClass extends Statement {
    final Token name;
    final ExprVariable superclass;
    final List<StmtFunction> methods;

    StmtClass(Token name, ExprVariable superclass, List<StmtFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    @Override
    public void exec(TablaSimbolos tabla){
        
    }
}
