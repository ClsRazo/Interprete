/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interprete;

import java.util.HashMap;

/**
 *
 * @author pande
 */
public class TablaSimbolos {
    private HashMap<String, Object> tablasimb;
    //constructor
    public TablaSimbolos()
    {
        //aqui definimos a la tabla de simbolos como un hashmap
        this.tablasimb= new HashMap<>();
    } 
    public boolean yaExiste(String name)
    {
        if(tablasimb.containsKey(name))
        { //si ya está la variable en el mapa de memoria, retorna true
            return true;
        }else{
            return false;
        }
    }
    public void agregarSimbolo(String name, Object value)
    {
        tablasimb.put(name, value);
    }
    public Object obtenervalor(String name)
    {
        return tablasimb.get(name);
    }
    public void modificarvalor(String name, Object valor)
    {
        tablasimb.put(name, valor);
    }
    
    
}
