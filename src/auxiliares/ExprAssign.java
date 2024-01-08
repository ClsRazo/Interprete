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
    public Object solve(){
        //Solo devuelve la expresión resuelta o debe checar y modificar la variable en la tabla de simbolos?
        return value.solve();
    }
}
