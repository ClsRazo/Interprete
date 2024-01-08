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
        if(tabla.yaExiste(name.lexema)) //función encontrada
        {
            //ejecución del bloque, si no es nula, se ejecuta
            if(body!=null)
            {
                //ejecutar el bloque con la tabla de símbolos que se tiene hasta el momento
                //se le pasan los parámetros para guardarlos en la tabla de símbolos
                body.exec(tabla);

            }else{
                throw new RuntimeException("ERROR: Función sin cuerpo");
            }
        }else{
            //se agrega la funcion a la tabla de simbolos
            tabla.agregarSimbolo(name.lexema, this);
        }
    }
}
