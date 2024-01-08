package auxiliares;

public class ExprGrouping extends Expression {
    final Expression expression;

    public ExprGrouping(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Object solve(){
        // las expresiones se retornan con su valor resuelto, ya sea boolean o numerico  
        return expression.solve();
    }
}
