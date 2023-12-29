package auxiliares;

import interprete.Token;

public class StmtVar extends Statement {
    final Token name;
    final Expression initializer;

    public StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }
    
    @Override
    public void exec(){
        
    }
}
