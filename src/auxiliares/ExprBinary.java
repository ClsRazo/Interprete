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

    public Object solve(ExprBinary expb){
        switch (expb.operator.tipo) {
            case BANG_EQUAL:
                
                break;
            case EQUAL_EQUAL:
                
                break;
            case GREATER:
                
                break;
            case GREATER_EQUAL:
                
                break;
            case LESS:
                
                break;
            case LESS_EQUAL:
                
                break;
            case MINUS:
                
                break;
            case PLUS:
                
                break;
            case SLASH:
                
                break;
            case STAR:
                
                break;
            default:
                break;
        }
        return expb;
    }

}
