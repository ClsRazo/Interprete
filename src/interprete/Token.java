/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interprete;

public class Token {

    public final TipoToken tipo;
    public final String lexema;
    public final Object literal;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
    }

    public String toString() {
        return "<" + tipo + " " + lexema + " " + literal + ">";
    }

    public Object getLiteral(){
        return literal;
    }
}

