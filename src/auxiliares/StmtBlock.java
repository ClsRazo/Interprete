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
                                    //tabla "principal"
        for(Statement st:statements)
        {
            if(st instanceof StmtIf || st instanceof StmtLoop)
            {
                //cada que se cree una nueva tabla, la tabla "principal" pasa como externa (bloque superior)
                TablaSimbolos tactual=new TablaSimbolos(tabla);
                st.exec(tactual);
            }else{
                st.exec(tabla); 
            }
            
        }
        
    }
}
