/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliares;

import interprete.TablaSimbolos;
import interprete.Token;

/**
 *
 * @author Carlo
 */
public class ExprAssign extends Expression{
    final Token name;
    final Expression value;

    public ExprAssign(Token name, Expression value) {
        this.name = name;//nombrevariable
        this.value = value; //asignación
    }

    @Override
    public Object solve(TablaSimbolos tabla){
        if(tabla.yaExiste(name.lexema)){
            tabla.modificarvalor(name.lexema, value.solve(tabla));
            return true;
        }else{
            throw new RuntimeException("La variable indicada no está declarada.");
        }
    }
}
