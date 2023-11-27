
import java.util.*;

public class ASDI implements Parser{
    private int i = 0;
    Stack<String>pila = new Stack<>();
    public ArrayList<String>terminales=new ArrayList<>();
    public ArrayList<String>nterminales=new ArrayList<>();
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;
    private String[][] tabla=new String[11][9];

    public ASDI(List<Token> tokens){
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
    }

    @Override

    public boolean parse() {
        //terminales
        nterminales.add("Q");
        nterminales.add("D");
        nterminales.add("P");
        nterminales.add("A");
        nterminales.add("A1");
        nterminales.add("A2");
        nterminales.add("A3");
        nterminales.add("T");
        nterminales.add("T1");
        nterminales.add("T2");
        nterminales.add("T3");
        //no terminales
        terminales.add("SELECT");
        terminales.add("DISTINCT");
        terminales.add("FROM");
        terminales.add("ASTERISCO");
        terminales.add("PUNTO");
        terminales.add("COMA");
        terminales.add("IDENTIFICADOR");
        terminales.add("EOF");
        //producciones
        tabla[0][0]="SELECT D FROM T";
        tabla[1][1]="DISTINCT P";
        tabla[1][3]="P";
        tabla[1][6]="P";
        tabla[2][3]="ASTERISCO";
        tabla[2][6]="A";
        tabla[3][6]="A2 A1";
        tabla[4][2]="EPSILON";
        tabla[4][5]="COMA A";
        tabla[5][6]="IDENTIFICADOR A3";
        tabla[6][2]="EPSILON";
        tabla[6][4]="PUNTO IDENTIFICADOR";
        tabla[6][5]="EPSILON";
        tabla[7][6]="T2 T1";
        tabla[8][5]="COMA T";
        tabla[8][7]="EPSILON";
        tabla[9][6]="IDENTIFICADOR T3";
        tabla[10][5]="EPSILON";
        tabla[10][6]="IDENTIFICADOR";
        tabla[10][7]="EPSILON";

        //llenando la pila con EOF y el lexema
        pila.push("EOF");
        pila.push("Q");
        //variables auxiliares sin inicializar ya que se irán actualizando durante el análisis
        String tope; //almacena el tope de la pila
        int indice ; //almacena el indice del token analizado para ubicarlo en la lista y posteriormente en la tabla
        String produccion;//almacena la produccion que se encuentra en tabla[][]
        boolean pilavacia=false;//bandera que indica si la pila está vacía
        while(pilavacia==false)
        {
            //al detectar errores se detiene
            if (hayErrores)
            {
                break;
            }
            //caso base, si la pila está vacía se activa la bandera y se sale del ciclo
            if (pila.isEmpty())
            {
                System.out.println("Pila vacia");
                pilavacia=true;
                hayErrores=false;
                break;
            }
            //inicializacion de las variables con los valores que se tienen al momento
            indice= terminales.indexOf(preanalisis.tipo.toString());
            tope=pila.peek().toString();
            //System.out.println("Token analizado: "+preanalisis.tipo.toString()+" Tope de la pila:"+pila.peek());

            //primer caso, cuando el tope de la pila es no terminal
            if(isnTerminal(tope))
            {
               /* System.out.println("Tope no terminal");
                System.out.println("Produccion en ["+ nterminales.indexOf(tope)+"]["+terminales.indexOf(preanalisis.tipo.toString())+"]");*/
                //si no hay produccion en las intersecciones del token con el tope significa error
                if(Objects.isNull(tabla[nterminales.indexOf(tope)][indice]))
                {
                    hayErrores=true;
                }else{
                        //caso EPSILON, se hace pop
                        if(tabla[nterminales.indexOf(tope)][indice].equals("EPSILON")){
                            pila.pop();
                        }else{
                            //produccion toma el valor de la produccion que esté en tabla[][]
                            produccion = tabla[nterminales.indexOf(tope)][indice];
                            //rellena la pila
                            splitnfill(produccion);
                        }

                }
            }
            //caso 2, el tope de la pila es terminal
            if (isTerminal(tope))
            {
                //si el tope de la pila es terminal y coincide con el token analizado, se hace pop y se recorren ambos "apuntadores" (preanalisis y toé)
                if(tope.equals(preanalisis.tipo.toString())){
                    i++;
                    //System.out.println("Coincidieron");
                    pila.pop();
                    if (tope.equals("EOF"))
                        hayErrores=false;
                    else
                        preanalisis=tokens.get(i);
                }else{
                    //si no coinciden, hay error
                    //System.out.println("No coinciden el tope de la pila y el token analizado");
                    hayErrores=true;
                }
            }
        }
        //chequeo de la bandera, pila vacia indica analisis completado sin errores
        if(pilavacia&&hayErrores==false){
            System.out.println("Consulta correcta");
        }else {
            System.out.println("Se encontraron errores");
        }
        return false;

    }
    //métodos auxiliares

    //busca al tope de la pila en la lista de terminales y devuelve true si está
    private boolean isTerminal(String tt)
    {
        if(terminales.contains(tt))
        {
            return true;
        }else{
            return false;
        }
    }
    //busca al tope de la pila en la lista de no terminales y devuelve true si está
    private boolean isnTerminal(String tt)
    {
        if(nterminales.contains(tt))
        {
            return true;
        }else{
            return false;
        }
    }
    //metodo para llenar la pila con las producciones
    //rellena la pila con la producción "invertida"
    private void splitnfill(String prod){
        pila.pop();
        String[] split=prod.split(" ");//fragmenta cada espacio
        for (int i=split.length-1;i>=0;i--) {
            pila.push(split[i]);
        }
    }
    private void splitnfill(String p1, String p2){
        pila.pop();
        pila.push(p1);
        pila.push(p2);
    }
}
