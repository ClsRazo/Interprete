package auxiliares;

import interprete.TablaSimbolos;
import java.util.List;

public class StmtBlock extends Statement{
    final List<Statement> statements;

    public StmtBlock(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void exec(TablaSimbolos tabla){
    }
}
