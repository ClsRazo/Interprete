package auxiliares;

import interprete.TablaSimbolos;

public class StmtReturn extends Statement {
    final Expression value;

    public StmtReturn(Expression value) {
        this.value = value;
    }

    @Override
    public void exec(TablaSimbolos tabla){
        if(value.solve(tabla) instanceof Double)
            throw new RuntimeException(value.solve(tabla));
            
    }
}
