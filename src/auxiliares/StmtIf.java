package auxiliares;

import interprete.TablaSimbolos;

public class StmtIf extends Statement {
    final Expression condition;
    final Statement thenBranch;
    final Statement elseBranch;

    public StmtIf(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public void exec(TablaSimbolos tabla){
        if(condition.solve(tabla) instanceof Boolean){
            boolean evaluacion = (boolean) condition.solve(tabla);
            if(evaluacion){
                if(thenBranch!=null)
                    thenBranch.exec(tabla);
                else{
                    System.out.println("ERROR: Sentencia IF sin cuerpo");
                }
            }else if(elseBranch!=null){
                elseBranch.exec(tabla);
            }
        }
    }
}
