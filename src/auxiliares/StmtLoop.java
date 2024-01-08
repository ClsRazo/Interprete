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
        if(condition.solve(tabla) instanceof Boolean){
            while((boolean)condition.solve(tabla)){
                body.exec(tabla);
            }
        }else{
            throw new RuntimeException("Error: El ciclo no es capaz de soportar la condición, no es Booleana.\n");
        }
    }
}
