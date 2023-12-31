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
        boolean evaluacion = (boolean) condition.solve();
        if(evaluacion==true)
        {
            thenBranch.exec(tabla);
        }if(elseBranch!=null)
            {
                elseBranch.exec(tabla);
        }
    }
}
