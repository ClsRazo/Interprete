package auxiliares;

import interprete.TablaSimbolos;

public class StmtReturn extends Statement {
    final Expression value;

    public StmtReturn(Expression value) {
        this.value = value;
    }

    @Override
    public void exec(TablaSimbolos tabla){
        //si no hay un retorno, retorna nulo
        if(value.solve(tabla)!=null)
        {
            throw new Return(null);
        }else{ //al haber un tipo de retorno, usa la clase retorno para lanzar una excepción con el valor de retorno.
            throw new Return(value.solve(tabla));
        }
            
    }
}
