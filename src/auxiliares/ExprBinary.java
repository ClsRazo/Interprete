package auxiliares;

import interprete.TablaSimbolos;
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
    public Object solve(TablaSimbolos tabla) {
        Object leftValue = left.solve(tabla);
        Object rightValue = right.solve(tabla);
        Double leftDouble, rightDouble;

        //Verifica el tipo de operador
        switch(operator.tipo){
            case BANG_EQUAL:
                return !leftValue.equals(rightValue);
            case EQUAL_EQUAL:
                return leftValue.equals(rightValue);
            case GREATER:
                //Asumimos que los datos son tipo double para la comparación
                if((leftValue instanceof Double || leftValue instanceof Integer) && (rightValue instanceof Double || rightValue instanceof Integer)){
                    leftDouble = (leftValue instanceof Integer) ? ((Integer)leftValue).doubleValue() : (Double) leftValue;
                    rightDouble = (rightValue instanceof Integer) ? ((Integer)rightValue).doubleValue() : (Double) rightValue;
                    return leftDouble > rightDouble;
                }else{
                    //Error en caso de que no sean double
                    throw new RuntimeException("Tipos no compatibles para la comparación: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case GREATER_EQUAL:
                //Asumimos que los datos son tipo double para la comparación
                if((leftValue instanceof Double || leftValue instanceof Integer) && (rightValue instanceof Double || rightValue instanceof Integer)){
                    leftDouble = (leftValue instanceof Integer) ? ((Integer)leftValue).doubleValue() : (Double) leftValue;
                    rightDouble = (rightValue instanceof Integer) ? ((Integer)rightValue).doubleValue() : (Double) rightValue;
                    return leftDouble >= rightDouble;
                }else{
                    //Error en caso de que no sean double
                    throw new RuntimeException("Tipos no compatibles para la comparación: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case LESS:
                //Asumimos que los datos son tipo double para la comparación
                if((leftValue instanceof Double || leftValue instanceof Integer) && (rightValue instanceof Double || rightValue instanceof Integer)){
                    leftDouble = (leftValue instanceof Integer) ? ((Integer)leftValue).doubleValue() : (Double) leftValue;
                    rightDouble = (rightValue instanceof Integer) ? ((Integer)rightValue).doubleValue() : (Double) rightValue;
                    return leftDouble < rightDouble;
                }else{
                    //Error en caso de que no sean double
                    throw new RuntimeException("Tipos no compatibles para la comparación: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case LESS_EQUAL:
                //Asumimos que los datos son tipo double para la comparación
                if((leftValue instanceof Double || leftValue instanceof Integer) && (rightValue instanceof Double || rightValue instanceof Integer)){
                    leftDouble = (leftValue instanceof Integer) ? ((Integer)leftValue).doubleValue() : (Double) leftValue;
                    rightDouble = (rightValue instanceof Integer) ? ((Integer)rightValue).doubleValue() : (Double) rightValue;
                    return leftDouble <= rightDouble;
                }else{
                    //Error en caso de que no sean double
                    throw new RuntimeException("Tipos no compatibles para la comparación: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case MINUS:
                if((leftValue instanceof Double || leftValue instanceof Integer) && (rightValue instanceof Double || rightValue instanceof Integer)){
                    //Realiza la resta (asumiendo que leftValue y rightValue son números o variables numericas)
                    leftDouble = (leftValue instanceof Integer) ? ((Integer)leftValue).doubleValue() : (Double) leftValue;
                    rightDouble = (rightValue instanceof Integer) ? ((Integer)rightValue).doubleValue() : (Double) rightValue;
                    return leftDouble - rightDouble;
                }else{
                    throw new RuntimeException("Tipos no compatibles para la resta: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case PLUS:
                if((leftValue instanceof Double || leftValue instanceof Integer) && (rightValue instanceof Double || rightValue instanceof Integer)){
                    //Realiza la suma (asumiendo que leftValue y rightValue son números o variables numericas)
                    leftDouble = (leftValue instanceof Integer) ? ((Integer)leftValue).doubleValue() : (Double) leftValue;
                    rightDouble = (rightValue instanceof Integer) ? ((Integer)rightValue).doubleValue() : (Double) rightValue;
                    return leftDouble + rightDouble;
                }else if(leftValue instanceof String || rightValue instanceof String){
                    //Concatenación si al menos uno de los operandos es una cadena
                    return leftValue.toString() + rightValue.toString();
                }else{
                    //Error, no se pueden sumar el valor izquierdo y el valor derecho
                    throw new RuntimeException("Tipos no compatibles para la suma: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case SLASH:
                if((leftValue instanceof Double || leftValue instanceof Integer) && (rightValue instanceof Double || rightValue instanceof Integer)){
                    leftDouble = (leftValue instanceof Integer) ? ((Integer)leftValue).doubleValue() : (Double) leftValue;
                    rightDouble = (rightValue instanceof Integer) ? ((Integer)rightValue).doubleValue() : (Double) rightValue;
                    if(rightDouble != 0.0){
                        //Realiza la división (asumiendo que leftValue y rightValue son números o variables numericas)
                        return leftDouble / rightDouble;
                    }else{
                        throw new RuntimeException("Error de división por cero.");
                    }
                }else{
                    throw new RuntimeException("Tipos no compatibles para la división: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            case STAR:
                if((leftValue instanceof Double || leftValue instanceof Integer) && (rightValue instanceof Double || rightValue instanceof Integer)){
                    //Realiza la multiplicación (asumiendo que leftValue y rightValue son números o variables numericas)
                    leftDouble = (leftValue instanceof Integer) ? ((Integer)leftValue).doubleValue() : (Double) leftValue;
                    rightDouble = (rightValue instanceof Integer) ? ((Integer)rightValue).doubleValue() : (Double) rightValue;
                    return leftDouble * rightDouble;
                }else{
                    throw new RuntimeException("Tipos no compatibles para la multiplicación: " + leftValue.getClass() + " y " + rightValue.getClass());
                }
            default:
                throw new RuntimeException("Operador no soportado: " + operator.lexema);
        }
    }

}
