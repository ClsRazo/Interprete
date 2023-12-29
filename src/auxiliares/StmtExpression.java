package auxiliares;

public class StmtExpression extends Statement {
    final Expression expression;

    public StmtExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void exec(){
        
    }
}
