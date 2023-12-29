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

    @Override
    public Object solve() {
        Object leftValue = left.solve();
        Object rightValue = right.solve();

        //Verifica el tipo de operador
        switch(operator.tipo){
            case BANG_EQUAL:
                return !leftValue.equals(rightValue);
            case EQUAL_EQUAL:
                return leftValue.equals(rightValue);
            case GREATER:
                //Asumimos que los datos son tipo double para la comparación
                if(leftValue instanceof Double && rightValue instanceof Double){
                    return (Double) leftValue > (Double) rightValue;
                }else{
                    //Error en caso de que no sean double
                    throw new RuntimeException("Tipos no compatibles para la comparación: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case GREATER_EQUAL:
                //Asumimos que los datos son tipo double para la comparación
                if(leftValue instanceof Double && rightValue instanceof Double){
                    return (Double)leftValue >= (Double)rightValue;
                }else{
                    //Error en caso de que no sean double
                    throw new RuntimeException("Tipos no compatibles para la comparación: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case LESS:
                //Asumimos que los datos son tipo double para la comparación
                if(leftValue instanceof Double && rightValue instanceof Double){
                    return (Double)leftValue < (Double)rightValue;
                }else{
                    //Error en caso de que no sean double
                    throw new RuntimeException("Tipos no compatibles para la comparación: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case LESS_EQUAL:
                //Asumimos que los datos son tipo double para la comparación
                if(leftValue instanceof Double && rightValue instanceof Double){
                    return (Double)leftValue <= (Double)rightValue;
                }else{
                    //Error en caso de que no sean double
                    throw new RuntimeException("Tipos no compatibles para la comparación: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case MINUS:
                if(leftValue instanceof Double && rightValue instanceof Double){
                    //Realiza la resta (asumiendo que leftValue y rightValue son números o variables numericas)
                    return (Double)leftValue - (Double)rightValue;
                }else{
                    throw new RuntimeException("Tipos no compatibles para la resta: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case PLUS:
                if(leftValue instanceof Double && rightValue instanceof Double){
                    //Realiza la suma (asumiendo que leftValue y rightValue son números o variables numericas)
                    return (Double)leftValue + (Double)rightValue;
                }else if(leftValue instanceof String || rightValue instanceof String){
                    //Concatenación si al menos uno de los operandos es una cadena
                    return leftValue.toString() + rightValue.toString();
                }else{
                    //Error, no se pueden sumar el valor izquierdo y el valor derecho
                    throw new RuntimeException("Tipos no compatibles para la suma: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case SLASH:
                if(leftValue instanceof Double && rightValue instanceof Double){
                    if((Double)rightValue != 0.0){
                        //Realiza la división (asumiendo que leftValue y rightValue son números o variables numericas)
                        return (Double)leftValue / (Double)rightValue;
                    }else{
                        throw new RuntimeException("Error de división por cero.");
                    }
                }else{
                    throw new RuntimeException("Tipos no compatibles para la división: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case STAR:
                if(leftValue instanceof Double && rightValue instanceof Double){
                    //Realiza la multiplicación (asumiendo que leftValue y rightValue son números o variables numericas)
                    return (Double)leftValue * (Double)rightValue;
                }else{
                    throw new RuntimeException("Tipos no compatibles para la multiplicación: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            default:
                throw new RuntimeException("Operador no soportado: " + operator.lexema);
        }
    }

}
