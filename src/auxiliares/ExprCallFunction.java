package auxiliares;

import interprete.TablaSimbolos;
import interprete.Token;
import auxiliares.*;

import java.util.ArrayList;
import java.util.List;

public class ExprCallFunction extends Expression{
    final Expression callee;
    // final Token paren;
    final List<Expression> arguments;

    public ExprCallFunction(Expression callee, /*Token paren,*/ List<Expression> arguments) {
        this.callee = callee;
        // this.paren = paren;
        this.arguments = arguments;
    }

    @Override
    public Object solve(TablaSimbolos tabla){
        final ArrayList<Object> solvedExp=new ArrayList<>();
        //Que devuelve?
        for(Expression ex:arguments){
            solvedExp.add(ex.solve(tabla));
        }

        if(callee instanceof ExprVariable){
            ExprVariable functionVar = (ExprVariable) callee;
            Object Function = tabla.obtenervalor(functionVar.name.lexema);
            if(Function instanceof StmtFunction){
                StmtFunction newFun = (StmtFunction) Function;

                //Tabla de simbolos "local"
                TablaSimbolos functionTable = new TablaSimbolos(tabla);

                //Guardamos los valores de los parametros en la tabla
                for(int i = 0; i < newFun.params.size(); i++){
                    Token param = newFun.params.get(i);
                    Object valor = solvedExp.get(i);
                    functionTable.agregarSimbolo(param.lexema, valor);
                }

                //Ejecutamos la función
                newFun.body.exec(functionTable);
                //true para confirmar que se ejecutó?
                return true;
            }else{
                throw new RuntimeException("Error: El valor de la variable no es una función.");
            }
        }else{
            throw new RuntimeException("Error: Función no definida.");
        }
        //return solvedExp;
    }
}
