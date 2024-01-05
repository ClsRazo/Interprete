/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interprete;
import auxiliares.*;

import java.util.ArrayList;
import java.util.Arrays;
import auxiliares.Statement;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author pande
 */
public class AnalizadorSem {
    public void AnalizadorSem(List<Statement> lista)
    {
        //tabla "principal"
        TablaSimbolos tabla=new TablaSimbolos();
        
        for(Statement st:lista)
        {
            if(st instanceof StmtIf || st instanceof StmtLoop)
            {
                //cada que se cree una nueva tabla, la tabla principal pasa como externa (bloque superior)
                TablaSimbolos tactual=new TablaSimbolos(tabla);
                st.exec(tactual);
            }else{
                st.exec(tabla); 
            }
            
        }
    }
    

    
}



