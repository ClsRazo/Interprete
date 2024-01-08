package interprete;

import auxiliares.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ASDR{

    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;
    public final TablaSimbolos tabla = new TablaSimbolos();
    public ASDR(List<Token> tokens){
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
    }

    //@Override
    public boolean parse() {

        if(!(preanalisis.tipo == TipoToken.EOF) && !hayErrores){
            List<Statement> statements = PROGRAM();
            if(preanalisis.tipo == TipoToken.EOF && !hayErrores){
                System.out.println("Consulta valida");
                return  true;
            }else {
                System.out.println("Consulta no valida");
                return false;
            }
        }

        return false;
        
    }

    //PROGRAM -> DECLARATION
    private List<Statement> PROGRAM(){
        List<Statement> statements = new ArrayList<>();
        DECLARATION(statements);
        return statements;
    }
    
    //DECLARATION -> FUN_DECL DECLARATION | VAR_DECL DECLARATION | STATEMENT DECLARATION | Ɛ
    private void DECLARATION(List<Statement> statements){
        if(hayErrores)
            return;
        
        if(preanalisis.tipo == TipoToken.FUN){
            Statement stmtF = FUN_DECL();
            statements.add(stmtF);
            DECLARATION(statements);
        }else if(preanalisis.tipo == TipoToken.VAR){
            Statement stmtV = VAR_DECL();
            statements.add(stmtV);
            DECLARATION(statements);
        }else if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER || preanalisis.tipo == TipoToken.LEFT_PAREN || preanalisis.tipo == TipoToken.FOR || preanalisis.tipo == TipoToken.IF || preanalisis.tipo == TipoToken.PRINT || preanalisis.tipo == TipoToken.RETURN || preanalisis.tipo == TipoToken.WHILE || preanalisis.tipo == TipoToken.LEFT_BRACE){
            Statement stmtS = STATEMENT();
            statements.add(stmtS);
            DECLARATION(statements);
        }
    }

    //FUN_DECL -> fun FUNCTION
    private Statement FUN_DECL(){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.FUN){
            match(TipoToken.FUN);
            return FUNCTION();
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'fun'");
            return null;
        }
        
    }

    //VAR_DECL -> var id VAR_INIT ;
    private Statement VAR_DECL(){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.VAR){
            match(TipoToken.VAR);
            if(preanalisis.tipo == TipoToken.IDENTIFIER){
                match(TipoToken.IDENTIFIER);
                Token nombre = previous();
                Expression exprV = VAR_INIT();
                if(preanalisis.tipo == TipoToken.SEMICOLON){
                    match(TipoToken.SEMICOLON);
                    //tabla.agregarSimbolo(nombre, (double)exprV.solve());
                    return new StmtVar(nombre, exprV);
                    
                }else{
                    hayErrores = true;
                    System.out.println("Se esperaba un ';'");
                    return null;
                }
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un 'id'");
                return null;
            }
            
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'var'");
            return null;
        }
        
    }

    //STATEMENT -> EXPR_STMT | FOR_STMT | IF_STMT | PRINT_STMT | RETURN_STMT | WHILE_STMT | BLOCK
    private Statement STATEMENT(){
        if(hayErrores)
            return null;
        if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER || preanalisis.tipo == TipoToken.LEFT_PAREN){
            return EXPR_STMT();
        }else if(preanalisis.tipo == TipoToken.FOR){
            return FOR_STMT();
        }else if(preanalisis.tipo == TipoToken.IF){
            return IF_STMT();
        }else if(preanalisis.tipo == TipoToken.PRINT){
            return PRINT_STMT();
        }else if(preanalisis.tipo == TipoToken.RETURN){
            return RETURN_STMT();
        }else if(preanalisis.tipo == TipoToken.WHILE){
            return WHILE_STMT();
        }else if(preanalisis.tipo == TipoToken.LEFT_BRACE){
            return BLOCK();
        }else{
            return null;
        }
    }

    //VAR_INIT -> = EXPRESSION | Ɛ
    private Expression VAR_INIT(){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            return EXPRESSION();
        }
        return null;
    }

    //EXPR_STMT -> EXPRESSION ;
    private Statement EXPR_STMT(){
        if(hayErrores)
            return null;

        Expression expr = EXPRESSION();
        if(preanalisis.tipo == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
            return new StmtExpression(expr);
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un ';'");
            return null;
        }
    }

    //FOR_STMT -> for ( FOR_STMT_1 FOR_STMT_2 FOR_STMT_3 ) STATEMENT
    private Statement FOR_STMT(){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.FOR){
            match(TipoToken.FOR);
            if(preanalisis.tipo == TipoToken.LEFT_PAREN){
                match(TipoToken.LEFT_PAREN);
                Statement inicial = FOR_STMT_1();
                Expression condicion = FOR_STMT_2();
                Expression incre = FOR_STMT_3();
                if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                    match(TipoToken.RIGHT_PAREN);
                    Statement body = STATEMENT();

                    if(incre != null){
                        body = new StmtBlock(Arrays.asList(body, new StmtExpression(incre)));
                    }
                    if(condicion == null){
                        condicion = new ExprLiteral(true);
                    }

                    body = new StmtLoop(condicion, body);

                    //i=0
                    if(inicial != null){
                        body = new StmtBlock(Arrays.asList(inicial, body));
                    }
                    return body;

                }else{
                    hayErrores = true;
                    System.out.println("Se esperaba un ')'");
                    return null;
                }
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un '('");
                return null;
            }
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'for'");
            return null;
        }
    }

    //FOR_STMT_1 -> VAR_DECL | EXPR_STMT | ;
    private Statement FOR_STMT_1(){
        if(hayErrores)
            return null;
        
        if(preanalisis.tipo == TipoToken.VAR){
            return VAR_DECL();
        }else if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER || preanalisis.tipo == TipoToken.LEFT_PAREN){
            return EXPR_STMT();    
        }else if(preanalisis.tipo == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
            return null;
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'var, !, -, true, false, null, number, string, id, ( o ;'");
            return null;
        }
    }

    //FOR_STMT_2 -> EXPRESSION; | ;
    private Expression FOR_STMT_2(){
        if(hayErrores)
            return null;
        
        if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER || preanalisis.tipo == TipoToken.LEFT_PAREN){
            Expression expr = EXPRESSION();
            if(preanalisis.tipo == TipoToken.SEMICOLON){
                match(TipoToken.SEMICOLON);
                return expr;
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un ';'");
                return null;
            }
        }else if(preanalisis.tipo == TipoToken.SEMICOLON){
            match(TipoToken.SEMICOLON);
            return null;
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'var, !, -, true, false, null, number, string, id, ( o ;'");
            return null;
        }
    }

    //FOR_STMT_3 -> EXPRESSION | Ɛ
    private Expression FOR_STMT_3(){
        if(hayErrores)
            return null;
        
        if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER || preanalisis.tipo == TipoToken.LEFT_PAREN){
            return EXPRESSION();
        }
        return null;
    }

    //IF_STMT -> if (EXPRESSION) STATEMENT ELSE_STATEMENT
    private Statement IF_STMT(){
        if(hayErrores)
            return null;
        
        if(preanalisis.tipo == TipoToken.IF){
            match(TipoToken.IF);
            if(preanalisis.tipo == TipoToken.LEFT_PAREN){
                match(TipoToken.LEFT_PAREN);
                Expression expr = EXPRESSION();
                if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                    match(TipoToken.RIGHT_PAREN);
                    Statement stmtI = STATEMENT();
                    Statement stmtE = ELSE_STATEMENT();
                    return new StmtIf(expr, stmtI, stmtE);
                }else{
                    hayErrores = true;
                    System.out.println("Se esperaba un ')'");
                    return null;
                }
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un '('");
                return null;
            }
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'if'");
            return null;
        }
    }

    //ELSE_STATEMENT -> else STATEMENT | Ɛ
    private Statement ELSE_STATEMENT(){
        if(hayErrores)
            return null;
        
        if(preanalisis.tipo == TipoToken.ELSE){
            match(TipoToken.ELSE);
            Statement stmtE = STATEMENT();
            return stmtE;
        }
        return null;
    }

    //PRINT_STMT -> print EXPRESSION ;
    private Statement PRINT_STMT(){
        if(hayErrores)
            return null;
        
        if(preanalisis.tipo == TipoToken.PRINT){
            match(TipoToken.PRINT);
            Expression expr = EXPRESSION();
            if(preanalisis.tipo == TipoToken.SEMICOLON){
                match(TipoToken.SEMICOLON);
                return new StmtPrint(expr);
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un ';'");
                return null;
            }
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'print'");
            return null;
        }
    }

    //RETURN_STMT -> return RETURN_EXP_OPC
    private Statement RETURN_STMT(){
        if(hayErrores)
            return null;
        
        if(preanalisis.tipo == TipoToken.RETURN){
            match(TipoToken.RETURN);
            Expression expr = RETURN_EXP_OPC();
            if(preanalisis.tipo == TipoToken.SEMICOLON){
                match(TipoToken.SEMICOLON);
                return new StmtReturn(expr);
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un ';'");
                return null;
            }
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'return'");
            return null;
        }
        
    }

    //RETURN_EXP_OPC -> EXPRESSION | Ɛ
    private Expression RETURN_EXP_OPC(){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER || preanalisis.tipo == TipoToken.LEFT_PAREN){
            Expression expr = EXPRESSION();
            return expr;
        }
        return null;
    }

    //WHILE_STMT -> while ( EXPRESSION ) STATEMENT
    private Statement WHILE_STMT(){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.WHILE){
            match(TipoToken.WHILE); 
            if(preanalisis.tipo == TipoToken.LEFT_PAREN){
                match(TipoToken.LEFT_PAREN);
                Expression expr = EXPRESSION();
                if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                    match(TipoToken.RIGHT_PAREN);
                    expr =  new ExprGrouping(expr);
                    Statement stmtW = STATEMENT();
                    return new StmtLoop(expr, stmtW);
                }else{
                    hayErrores = true;
                    System.out.println("Se esperaba un ')'");
                    return null;
                }
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un '('");
                return null;
            }
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'while'");
            return null;
        }
    }

    //BLOCK -> { DECLARATION }
    private Statement BLOCK(){
        if(hayErrores)
            return null;

        List <Statement> statements =new ArrayList<>();
        if(preanalisis.tipo == TipoToken.LEFT_BRACE){
            match(TipoToken.LEFT_BRACE);
            DECLARATION(statements);
            if(preanalisis.tipo == TipoToken.RIGHT_BRACE){
                match(TipoToken.RIGHT_BRACE);
                return new StmtBlock(statements);
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un '}'");
                return null;
            }
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un '{'");
            return null;
        }
    }

    //EXPRESSION -> ASSIGNMENT
    private Expression EXPRESSION(){
        if(hayErrores)
            return null;
        
        return ASSIGNMENT();
    }

    //ASSIGNMENT -> LOGIC_OR ASSIGNMENT_OPC
    private Expression ASSIGNMENT(){
        if(hayErrores)
            return null;

        Expression expr = LOGIC_OR();
        expr = ASSIGNMENT_OPC(expr);
        return expr;
    }

    //ASSIGNMENT_OPC -> = EXPRESSION | Ɛ
    private Expression ASSIGNMENT_OPC(Expression expr){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.EQUAL){
            match(TipoToken.EQUAL);
            Expression expr2 = EXPRESSION();
            if(expr instanceof ExprVariable){
                Token t = ((ExprVariable)expr).name;
                return new ExprAssign(t, expr2);
            }
            else{
                return null;
            }
        }
        return expr;
    }

    //LOGIC_OR -> LOGIC_AND LOGIC_OR_2
    private Expression LOGIC_OR(){
        if(hayErrores)
            return null;

        Expression expr = LOGIC_AND();
        expr = LOGIC_OR_2(expr);
        return expr;
    }

    //LOGIC_OR_2 -> or LOGIC_AND LOGIC_OR_2 | Ɛ
    private Expression LOGIC_OR_2(Expression expr){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.OR){
            match(TipoToken.OR);
            Token operador = previous();
            Expression expr2 = LOGIC_AND();
            ExprLogical expl = new ExprLogical(expr, operador, expr2);
            return LOGIC_OR_2(expl);
        }
        return expr;
    }

    //LOGIC_AND -> EQUALITY LOGIC_AND_2
    private Expression LOGIC_AND(){
        if(hayErrores)
            return null;

        Expression expr = EQUALITY();
        expr = LOGIC_AND_2(expr);
        return expr;

    }

    //LOGIC_AND_2 -> and EQUALITY LOGIC_AND_2 | Ɛ
    private Expression LOGIC_AND_2(Expression expr){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.AND){
            match(TipoToken.AND);
            Token operador = previous();
            Expression expr2 = EQUALITY();
            ExprLogical expl = new ExprLogical(expr, operador, expr2);
            return LOGIC_AND_2(expl);
        }
        return expr;
    }

    //Determinar si existe
    //Obtener valor
    //Asignar valor
    //.containskey
    //Ligar los hashmaps de hijos con padres

    //EQUALITY -> COMPARISON EQUALITY_2
    private Expression EQUALITY(){
        if(hayErrores)
            return null;

        Expression expr = COMPARISON();
        expr = EQUALITY_2(expr);
        return expr;
    }

    //EQUALITY_2 -> != COMPARISON EQUALITY_2 | == COMPARISON EQUALITY_2 | Ɛ
    private Expression EQUALITY_2(Expression expr){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.BANG_EQUAL){
            match(TipoToken.BANG_EQUAL);
            Token operador = previous();
            Expression expr2 = COMPARISON();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return EQUALITY_2(expb);
        }else if(preanalisis.tipo == TipoToken.EQUAL_EQUAL){
            match(TipoToken.EQUAL_EQUAL);
            Token operador = previous();
            Expression expr2 = COMPARISON();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return EQUALITY_2(expb);
        }
        return expr;
    }

    //COMPARISON -> TERM COMPARISON_2
    private Expression COMPARISON(){
        if(hayErrores)
            return null;

        Expression expr = TERM();
        expr = COMPARISON_2(expr);
        return expr;
        
    }

    //COMPARISON_2 -> > TERM COMPARISON_2 | >= TERM COMPARISON_2 | < TERM COMPARISON_2 | <= TERM COMPARISON_2 | Ɛ
    private Expression COMPARISON_2(Expression expr){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.GREATER){
            match(TipoToken.GREATER);
            Token operador = previous();
            Expression expr2 = TERM();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return COMPARISON_2(expb);
        }else if(preanalisis.tipo == TipoToken.GREATER_EQUAL){
            match(TipoToken.GREATER_EQUAL);
            Token operador = previous();
            Expression expr2 = TERM();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return COMPARISON_2(expb);
        }else if(preanalisis.tipo == TipoToken.LESS){
            match(TipoToken.LESS);
            Token operador = previous();
            Expression expr2 = TERM();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return COMPARISON_2(expb);
        }else if(preanalisis.tipo == TipoToken.LESS_EQUAL){
            match(TipoToken.LESS_EQUAL);
            Token operador = previous();
            Expression expr2 = TERM();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return COMPARISON_2(expb);
        }
        return expr;
    }

    //TERM -> FACTOR TERM_2
    private Expression TERM(){
        if(hayErrores)
            return null;

        Expression expr = FACTOR();
        expr = TERM_2(expr);
        return expr;
    }

    //TERM_2 -> - FACTOR TERM_2 | + FACTOR TERM_2 | Ɛ
    private Expression TERM_2(Expression expr){
        if(hayErrores)
            return null;
        
        if(preanalisis.tipo == TipoToken.MINUS){
            match(TipoToken.MINUS);
            Token operador = previous();
            Expression expr2 = FACTOR();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return TERM_2(expb);
        }else if(preanalisis.tipo == TipoToken.PLUS){
            match(TipoToken.PLUS);
            Token operador = previous();
            Expression expr2 = FACTOR();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return TERM_2(expb);
        }
        return expr;
    }

    //FACTOR -> UNARY FACTOR_2
    private Expression FACTOR(){
        if(hayErrores)
        // throw new Exception("Error");
            return null;

        Expression expr = UNARY();
        if(preanalisis.tipo == TipoToken.SLASH || preanalisis.tipo == TipoToken.STAR){
            expr = FACTOR_2(expr);
        }
        return expr;
    }

    //FACTOR_2 -> / UNARY FACTOR_2 | * UNARY FACTOR_2 | Ɛ
    private Expression FACTOR_2(Expression expr){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.SLASH){
            match(TipoToken.SLASH);
            Token operador = previous();
            Expression expr2 = UNARY();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return FACTOR_2(expb);
        }else if(preanalisis.tipo == TipoToken.STAR){
            match(TipoToken.STAR);
            Token operador = previous();
            Expression expr2 = UNARY();
            ExprBinary expb = new ExprBinary(expr, operador, expr2);
            return FACTOR_2(expb);
        }
        return expr;
    }

    //UNARY -> ! UNARY | - UNARY | CALL
    private Expression UNARY(){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.BANG){
            match(TipoToken.BANG);
            Token operador = previous();
            Expression expr = UNARY();
            return new ExprUnary(operador, expr);
        }else if(preanalisis.tipo == TipoToken.MINUS){
            match(TipoToken.MINUS);
            Token operador = previous();
            Expression expr = UNARY();
            return new ExprUnary(operador, expr);
        }else if(preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER || preanalisis.tipo == TipoToken.LEFT_PAREN){
            return CALL();
        }else{
            return null;
        }
        
        // switch (preanalisis.tipo) {
        //     case BANG:
        //         match(TipoToken.BANG);
        //         Token operador = previous();
        //         Expression expr = UNARY();
        //         return new ExprUnary(operador, expr);
        //     case MINUS:
        //         match(TipoToken.MINUS);
        //         operador = previous();
        //         expr = UNARY();
        //         return new ExprUnary(operador, expr);
        //     case TRUE:
        //     case FALSE:
        //     case NULL:
        //     case NUMBER:
        //     case STRING:
        //     case IDENTIFIER:
        //     case LEFT_PAREN:
        //         return CALL();
        //     default:
        //         return null;
        // }
    }

    //CALL -> PRIMARY CALL_2
    private Expression CALL(){
        if(hayErrores)
            return null;

        Expression expr = PRIMARY();
        if(preanalisis.tipo == TipoToken.LEFT_PAREN){
            expr = CALL_2(expr);
        }
        return expr;
    }

    //CALL_2 -> ( ARGUMENTS_OPC ) | Ɛ
    private Expression CALL_2(Expression expr){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            List<Expression> lstArguments = ARGUMENTS_OPC();
            if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                match(TipoToken.RIGHT_PAREN);
                ExprCallFunction ecf = new ExprCallFunction(expr, lstArguments);
                return ecf;
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un ')'");
                return null;
            }
        }
        return expr;
    }

    //PRIMARY -> true | false | null | number | string | id | ( EXPRESSION )
    private Expression PRIMARY(){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.TRUE){
            match(TipoToken.TRUE);
            return new ExprLiteral(true);
        }else if(preanalisis.tipo == TipoToken.FALSE){
            match(TipoToken.FALSE);
            return new ExprLiteral(false);
        }else if(preanalisis.tipo == TipoToken.NULL){
            match(TipoToken.NULL);
            return new ExprLiteral(null);
        }else if(preanalisis.tipo == TipoToken.NUMBER){
            match(TipoToken.NUMBER);
            Token numero = previous();
            return new ExprLiteral(numero.getLiteral());
        }else if(preanalisis.tipo == TipoToken.STRING){
            match(TipoToken.STRING);
            Token cadena = previous();
            return new ExprLiteral(cadena.getLiteral());
        }else if(preanalisis.tipo == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
            Token id = previous();
            return new ExprVariable(id);
        }else if(preanalisis.tipo == TipoToken.LEFT_PAREN){
            match(TipoToken.LEFT_PAREN);
            Expression expr = EXPRESSION();
            if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                match(TipoToken.RIGHT_PAREN);
                return new ExprGrouping(expr);
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un ')'");
                return null;
            }
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'true, false, null, number, string, identifier, o )'");
            return null;
        }
    }

    //FUNCTION -> id ( PARAMETERS_OPC ) BLOCK
    private Statement FUNCTION(){
        if(hayErrores)
            return null;

        if(preanalisis.tipo == TipoToken.IDENTIFIER){
            match(TipoToken.IDENTIFIER);
            Token name = previous();
            if(preanalisis.tipo == TipoToken.LEFT_PAREN){
                match(TipoToken.LEFT_PAREN);
                List <Token> parameters = PARAMETERS_OPC();
                if(preanalisis.tipo == TipoToken.RIGHT_PAREN){
                    match(TipoToken.RIGHT_PAREN);
                    Statement body = BLOCK();
                    return new StmtFunction(name, parameters, (StmtBlock) body);
                }else{
                    hayErrores = true;
                    System.out.println("Se esperaba un ')'");
                    return null;
                }
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un '('");
                return null;
            }
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'id'");
            return null;
        }
    }

   //PARAMETERS_OPC -> PARAMETERS | Ɛ
   private List<Token> PARAMETERS_OPC(){
        if(hayErrores)
            return null;

        List<Token> parameters = new ArrayList<>();
        if(preanalisis.tipo == TipoToken.IDENTIFIER){
            PARAMETERS(parameters);
            return parameters;
        }
        return null;
   }

   //PARAMETERS -> id PARAMETERS_2
   private void PARAMETERS(List<Token> parameters){
        if(hayErrores)
            return;

        if(preanalisis.tipo == TipoToken.IDENTIFIER){
            Token param = preanalisis;
            match(TipoToken.IDENTIFIER);
            parameters.add(param);
            if(preanalisis.tipo == TipoToken.COMMA){
                PARAMETERS_2(parameters);
            }
        }else{
            hayErrores = true;
            System.out.println("Se esperaba un 'id'");
        }
   }

   //PARAMETERS_2 -> , id PARAMETERS_2 | Ɛ
   private void PARAMETERS_2(List<Token> parameters){
        if(hayErrores)
            return;

        if(preanalisis.tipo == TipoToken.COMMA){
            match(TipoToken.COMMA);
            if(preanalisis.tipo == TipoToken.IDENTIFIER){
                Token param = preanalisis;
                match(TipoToken.IDENTIFIER);
                parameters.add(param);
                if(preanalisis.tipo == TipoToken.COMMA){
                    PARAMETERS_2(parameters);
                }
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un 'id'");
            }
        }
   }

   //ARGUMENTS_OPC -> EXPRESSION ARGUMENTS | Ɛ
   private List<Expression> ARGUMENTS_OPC(){
        if(hayErrores)
            return null;

        List <Expression> arguments = new ArrayList<>();
        if(preanalisis.tipo == TipoToken.BANG || preanalisis.tipo == TipoToken.MINUS || preanalisis.tipo == TipoToken.TRUE || preanalisis.tipo == TipoToken.FALSE || preanalisis.tipo == TipoToken.NULL || preanalisis.tipo == TipoToken.NUMBER || preanalisis.tipo == TipoToken.STRING || preanalisis.tipo == TipoToken.IDENTIFIER || preanalisis.tipo == TipoToken.LEFT_PAREN){
            arguments.add(EXPRESSION());
            if(preanalisis.tipo == TipoToken.COMMA){
                ARGUMENTS(arguments);
                return arguments;
            }
        }
        return null;
   }

   //ARGUMENTS -> , EXPRESSION ARGUMENTS | Ɛ
   private void ARGUMENTS(List <Expression> arguments){
        if(hayErrores)
            return;

        if(preanalisis.tipo == TipoToken.COMMA){
            match(TipoToken.COMMA);
            arguments.add(EXPRESSION());
            if(preanalisis.tipo == TipoToken.COMMA){
                ARGUMENTS(arguments);
            }
        }
   }

    //Función match
    private void match(TipoToken tt){
        if(preanalisis.tipo == tt){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error encontrado");
            // String message = "Error en la línea " +
            //         preanalisis.getPosition().getLine() +
            //         ". Se esperaba " + preanalisis.getTipo() +
            //         " pero se encontró " + tt;
            // throw new ParserException(message);
        }

    }

    private Token previous(){
        return this.tokens.get(i-1);
    }
}
