package auxiliares;

import interprete.TablaSimbolos;
import interprete.Token;

import java.util.List;

import javax.management.RuntimeErrorException;

public class StmtFunction extends Statement {
    final Token name;
    final List<Token> params;
    final StmtBlock body;

    public StmtFunction(Token name, List<Token> params, StmtBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    @Override
    public void exec(TablaSimbolos tabla){
        if(tabla.yaExiste(name.lexema)){ //función encontrada
            throw new RuntimeException("Error: Función ya declarada.");
        }else{
            tabla.agregarSimbolo(name.lexema, this);
            TablaSimbolos tabFun = new TablaSimbolos(tabla);
            for (Token tk: params){
                tabFun.agregarSimbolo(tk.lexema, null);
            }
        }
    }
}
