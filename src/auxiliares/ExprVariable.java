package auxiliares;

import javax.management.RuntimeErrorException;

import interprete.TablaSimbolos;
import interprete.Token;

public class ExprVariable extends Expression {
    public final Token name;

    public ExprVariable(Token name) {
        this.name = name;
    }

    @Override
    public Object solve(TablaSimbolos tabla){
        if(tabla.yaExiste(name.lexema)){
            return tabla.obtenervalor(name.lexema);
        }else{
            throw new RuntimeException("La variable indicada no está declarada.");
        }
    }
}