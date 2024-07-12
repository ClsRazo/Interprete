package auxiliares;

import interprete.TablaSimbolos;
import interprete.Token;

public class ExprLogical extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    public ExprLogical(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object solve(TablaSimbolos tabla){
        Object leftValue= left.solve(tabla);
        Object rightValue = right.solve(tabla);

        if(leftValue instanceof Boolean && rightValue instanceof Boolean){
            boolean leftBoolean = (boolean) leftValue;
            boolean rightBoolean = (boolean) rightValue;
        switch (operator.tipo) {
            case AND:
                return leftBoolean && rightBoolean;
            case OR:
                return leftBoolean ||  rightBoolean;
            default:
            throw new RuntimeException("Operador lógico no soportado: " + operator.lexema);
        }
    } else {
            //mensaje de error en el caso que leftValue o rightValue no sean booleans
            throw new RuntimeException("Los valores no son booleanos");
        }
    }

    //Función auxiliar para verificar si un valor es verdadero 
    private boolean isBoolean(Object value) {
       return value instanceof Boolean;
    }
}