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
    public void AnalizadorSem(List<Statement> lista, TablaSimbolos tabla)
    {
        for(Statement st:lista)
        {
            st.exec(tabla); 
        }
    }
    

    
}



