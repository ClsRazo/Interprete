package auxiliares;

import interprete.TablaSimbolos;
import interprete.Token;

public class StmtVar extends Statement {
    final Token name;
    final Expression initializer;
    

    public StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }
    @Override
    public void exec(TablaSimbolos tabla){
        if(tabla.yaExiste(name.lexema)){
            throw new RuntimeException("Error: Variable ya declarada.");
        }else{
            //la llave es el nombre de la variable, al ser hashmap no puede haber variables duplicadas
            tabla.agregarSimbolo(name.lexema, initializer.solve(tabla));
        }
    }
}
