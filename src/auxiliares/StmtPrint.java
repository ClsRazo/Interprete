package auxiliares;

public class StmtPrint extends Statement {
    final Expression expression;

    public StmtPrint(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void exec(){
        
    }
}
