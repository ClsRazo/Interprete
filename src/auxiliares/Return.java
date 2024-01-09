/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auxiliares;

/**
 *
 * @author pande
 */
public class Return extends RuntimeException{
    final Object valor;

    Return(Object valor){
        super(null, null, false, false);
        this.valor=valor;
    }
}
