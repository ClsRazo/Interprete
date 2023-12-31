package auxiliares;

import interprete.TablaSimbolos;

public class StmtPrint extends Statement {
    final Expression expression;

    public StmtPrint(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void exec(TablaSimbolos tabla){
        Object valor=expression.solve();
        System.out.println(""+valor);
    }
}
