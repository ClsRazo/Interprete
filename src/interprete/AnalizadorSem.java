/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interprete;
import auxiliares.*;

/*import java.util.ArrayList;
import java.util.Arrays;*/
import auxiliares.Statement;
//import java.util.HashMap;
import java.util.List;

/**
 *
 * @author pande
 */
public class AnalizadorSem {
    private final List<Statement> list;

    public AnalizadorSem(List<Statement> lista){
        this.list = lista;
    }

    public void Analizador(){
        //tabla "principal"
        TablaSimbolos tabla=new TablaSimbolos();

        for(Statement st:list){
            st.exec(tabla);
        }
    }
}



