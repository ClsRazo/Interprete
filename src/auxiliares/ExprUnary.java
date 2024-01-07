package auxiliares;

import interprete.Token;

public class ExprUnary extends Expression{
    final Token operator;
    final Expression right;

    public ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

      @Override
    public Object solve(){
        switch (operator.tipo) {
            case BANG:
                return !isTruthy(right.solve());
            case MINUS:
                Object rightValue = right.solve();
                if(!(rightValue instanceof Double)){
                    throw new RuntimeException("El operando debe ser un número para el operador unario '" + operator.lexema + "'.");
                }
                return -((Double)rightValue);
            default:
                throw new RuntimeException("Operador no soportado: " + operator.lexema);
        }
    }

    //Función auxiliar para verificar si un valor es verdadero (truthy)
    private boolean isTruthy(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean) return (boolean) value;
        return true;
    }
}
