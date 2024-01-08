package auxiliares;

import interprete.TablaSimbolos;

public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    public StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void exec(TablaSimbolos tabla){

        while(!(boolean)condition.solve())
        {
            body.exec(tabla);
        }
    }
}
