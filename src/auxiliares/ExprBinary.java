package auxiliares;

import interprete.TipoToken;
import interprete.Token;

public class ExprBinary extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    public ExprBinary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    // public Object solve(ExprBinary expb){
    //     switch (expb.operator.tipo) {
    //         case BANG_EQUAL:
                
    //             break;
    //         case EQUAL_EQUAL:
                
    //             break;
    //         case GREATER:
                
    //             break;
    //         case GREATER_EQUAL:
                
    //             break;
    //         case LESS:
                
    //             break;
    //         case LESS_EQUAL:
                
    //             break;
    //         case MINUS:
                
    //             break;
    //         case PLUS:
                
    //             break;
    //         case SLASH:
                
    //             break;
    //         case STAR:
                
    //             break;
    //         default:
    //             break;
    //     }
    //     return expb;
    // }

    @Override
    public Object solve() {
        Object leftValue = left.solve();
        Object rightValue = right.solve();

        // Verifica el tipo de operador
        switch (operator.tipo) {
            case PLUS:
                // Realiza la suma (asumiendo que leftValue y rightValue son números)
                if (leftValue instanceof Double && rightValue instanceof Double) {
                    return (Double) leftValue + (Double) rightValue;
                } else if (leftValue instanceof String || rightValue instanceof String) {
                    // Concatenación si al menos uno de los operandos es una cadena
                    return leftValue.toString() + rightValue.toString();
                } else {
                    // Maneja un error, ya que no puedes sumar estos tipos de operandos
                    throw new RuntimeException("Error semántico: No se puede sumar " + leftValue + " y " + rightValue);
                }
            // Agrega casos para otros operadores aquí

            default:
                throw new RuntimeException("Operador no soportado: " + operator.lexema);
        }
    }

}
