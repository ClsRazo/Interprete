package auxiliares;

import interprete.TablaSimbolos;
import java.util.HashMap;

public abstract class Statement {
    //este así se queda, para llevar a cabo polimorfismo
    public abstract void exec(TablaSimbolos tabla);
}
