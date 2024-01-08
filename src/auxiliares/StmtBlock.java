package auxiliares;

import interprete.TablaSimbolos;
import interprete.Token;

import java.util.List;

public class StmtBlock extends Statement{
    final List<Statement> statements;

    public StmtBlock(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void exec(TablaSimbolos tabla){
                                //tabla "principal"
        for(Statement st:statements)
        {
            st.exec(tabla); 
        }
        
    }
    
}
