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
    private TablaSimbolos texterna;
    //constructor
    public TablaSimbolos()
    {
        //aqui definimos a la tabla de simbolos como un hashmap
        this.texterna=null;
        this.tablasimb= new HashMap<>();
    } 
    
    //segundo constructor para ligar tablas
    public TablaSimbolos(TablaSimbolos tablaexterna)
    {
        this.texterna=tablaexterna;
        this.tablasimb= new HashMap<>();
    }
    
    public boolean yaExiste(String name)
    {
        //si la tabla actual no la tiene, entonces verifica que la tabla externa no esté vacía
        return tablasimb.containsKey(name)||(texterna!=null && texterna.yaExiste(name));
    }
    
    public Object obtenervalor(String name)
    {
        if(tablasimb.containsKey(name))
            return tablasimb.get(name);
        //si la tabla externa no está vacía, vuelve a llamar a obtener valor de manera recursiva, entonces con cada llamada, la tabla externa
        //pasa a ser la tabla "actual"
        else if(texterna!=null)
        {
                return texterna.obtenervalor(name);
        }else{
            throw new RuntimeException("Variable no definida");
        }
    }
    
    public void agregarSimbolo(String name, Object value)
    {
        if(texterna!=null)
        {
            if(texterna.yaExiste(name))
            {
                throw new RuntimeException("ERROR: Variable ya declarada");
            }
        }else{
            tablasimb.put(name, value);
        }
    }
    
    /*
    public void modificarvalor(String name, Object valor)
    {
        tablasimb.put(name, valor);
    }
    */
}
