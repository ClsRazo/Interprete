package auxiliares;

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
    public Object solve(){
        Object leftValue= left.solve();
        Object rightValue = right.solve();

        switch (operator.tipo) {
            case AND:
                return isTruthy(leftValue) && isTruthy(rightValue);
            case OR:
                return isTruthy(leftValue) || isTruthy(rightValue);
            default:
            throw new RuntimeException("Operador lógico no soportado: " + operator.lexema);
        }
    }

    //Función auxiliar para verificar si un valor es verdadero (truthy)
    private boolean isTruthy(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean) return (boolean) value;
        return true;
    }
}

