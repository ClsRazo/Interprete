package auxiliares;

import interprete.TablaSimbolos;
import interprete.Token;

public class ExprUnary extends Expression{
    final Token operator;
    final Expression right;

    public ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

      @Override
    public Object solve(TablaSimbolos tabla){
        switch (operator.tipo) {
            case BANG:
                Object rightValue = right.solve(tabla);
                if(!isBoolean(rightValue)){
                        throw new RuntimeException("El operando debe ser un booleano para el operador unario '" + operator.lexema + "'.");
                    }
                return !((boolean)rightValue);
            case MINUS:
                rightValue = right.solve(tabla);
                if(!(rightValue instanceof Double || rightValue instanceof Integer)){
                    throw new RuntimeException("El operando debe ser un número para el operador unario '" + operator.lexema + "'.");
                }
                return -((Double)rightValue);
            default:
                throw new RuntimeException("Operador no soportado: " + operator.lexema);
        }
    }

    //Función auxiliar para verificar si es un booleano
    private boolean isBoolean(Object value) {
        return value instanceof Boolean;
    }
}
