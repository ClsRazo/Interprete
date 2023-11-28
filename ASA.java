import java.util.List;
import java.util.Stack;

public class ASA implements Parser {
    private int i = 0;
    private Token preanalisis;
    private TipoToken tt;
    private final List<Token> tokens;
    private Stack<Integer> pila=new Stack<>();
    private boolean hayErrores = false;
    private boolean salir=false;

    public ASA(List<Token> tokens) {
        this.tokens = tokens;
        preanalisis=this.tokens.get(i);
        this.pila.push(0);
    }

    @Override
    public boolean parse() {

        do {
            tt=preanalisis.tipo;
            switch (pila.peek()) {
                case 0:
                    if (tt == TipoToken.SELECT) {
                        pila.push(2);
                        i++;
                        preanalisis = tokens.get(i);
                    }
                    else
                        hayErrores=true;
                break;
                case 1:
                    if (tt == TipoToken.EOF)
                        salir= true;
                break;
                case 2:
                    switch (tt) {
                        case IDENTIFICADOR:
                            pila.push(9);
                        break;
                    
                        case ASTERISCO:
                            pila.push(6);
                        break;
                    
                        case DISTINCT:
                            pila.push(4);
                        break;
                    
                        default:
                            System.out.println("\033[91mse esperaba 'id', '*' o 'distinct ' antes de \033[0m"+preanalisis.lexema);
                            hayErrores = true;
                        break;
                    }
                    if(!hayErrores){
                        i++;
                        preanalisis = tokens.get(i);
                    } 
                break;
                case 3:
                    if(tt==TipoToken.FROM)
                        pila.push(10);
                    else{
                        System.out.println("\033[91mse esperaba un 'from' antes de \033[0m"+preanalisis.lexema);
                        hayErrores=true;
                        }
                    i++;
                    preanalisis = tokens.get(i);
                break;
                case 4:
                    if (tt == TipoToken.IDENTIFICADOR)
                        pila.push(9);
                    else if (tt == TipoToken.ASTERISCO)
                        pila.push(6);
                    else{
                        System.out.println("\033[91mse esperaba un 'id' o '*' antes de\033[0m "+preanalisis.lexema);
                        hayErrores=true;
                    }
                    i++;
                    preanalisis = tokens.get(i);
                break;
                case 5:
                    if(tt==TipoToken.FROM) {//reduccion de 3 (D->P)
                        pila.pop();
                        if (pila.peek() == 2)
                            pila.push(3);
                        else{
                            System.out.println("\033[91mse esperaba un 'id' o '*' antes de\033[0m"+preanalisis.lexema);
                            hayErrores = true;
                        }
                    }
                    else{
                        System.out.println("\033[91mse esperaba un 'from' antes de \033[0m"+preanalisis.lexema);
                        hayErrores=true;
                    }
                break;
                case 6:
                    if(tt==TipoToken.FROM) {//reduccion de 4 (P->*)
                        pila.pop();
                        if (pila.peek() == 2)
                            pila.push(5);
                        else if (pila.peek() == 4)
                            pila.push(18);
                        else
                            hayErrores= true;
                    }
                    else{
                        System.out.println("se esperaba un 'from' antes de "+preanalisis.lexema);
                        hayErrores=true;
                    }
                break;
                case 7:
                    if(tt==TipoToken.COMA) {
                        pila.push(22);
                        i++;
                        preanalisis = tokens.get(i);
                    }
                    else if(tt==TipoToken.FROM){//reduccion de 5 (P->A)
                        pila.pop();
                        if (pila.peek() == 2)
                            pila.push(5);
                        else if (pila.peek() == 4)
                            pila.push(18);
                        else
                            hayErrores= true;
                    }
                    else{
                        System.out.println("se esperaba un 'from' o ',' antes de "+preanalisis.lexema);
                        hayErrores=true;
                    }
                break;
                case 8:
                    if(tt==TipoToken.COMA||tt==TipoToken.FROM) {//reduccion de 7 (A->A1)
                        pila.pop();
                        if (pila.peek() == 2 || pila.peek()==4)
                            pila.push(7);
                        else
                            hayErrores= true;
                    }
                    else{
                        System.out.println("se esperaba un 'from' o ',' antes de "+preanalisis.lexema);
                        hayErrores=true;
                    }
                break;
                case 9:
                    if(tt==TipoToken.PUNTO) {
                        pila.push(20);
                        i++;
                        preanalisis = tokens.get(i);
                    }
                    else if(tt==TipoToken.COMA||tt==TipoToken.FROM){//reduccion de 10 (A2->Epsilon)
                        pila.push(19);
                    }
                    else{
                        System.out.println("se esperaba un '.' antes de "+preanalisis.lexema);
                        hayErrores=true;
                    }
                break;
                case 10, 14:
                    if(tt==TipoToken.IDENTIFICADOR) {
                        pila.push(13);
                        i++;
                        preanalisis = tokens.get(i);
                    }
                    else{
                        if(tt==TipoToken.EOF)
                            System.out.println("\033[91mse esperaba un 'id' antes del fin de cadena\033[0m");
                        else
                            System.out.println("\033[91mse esperaba un 'id' antes de \033[0m"+preanalisis.lexema);
                        hayErrores=true;
                    }
                break;
                case 11:
                    if(tt==TipoToken.COMA) {
                        pila.push(14);
                        i++;
                        preanalisis = tokens.get(i);
                    }
                    else if(tt==TipoToken.EOF){//reduccion de 1 (Q->select D from T)
                        pila.pop();
                        pila.pop();
                        pila.pop();
                        pila.pop();
                        if (pila.peek() == 0)
                            pila.push(1);
                        else
                            hayErrores= true;
                    }
                    else
                        hayErrores=true;
                break;
                case 12:
                    if(tt==TipoToken.COMA||tt==TipoToken.EOF) {//reduccion de 12 (T->T1)
                        pila.pop();
                        if (pila.peek() == 10)
                            pila.push(11);
                        else
                            hayErrores= true;
                    }
                    else
                        hayErrores=true;
                break;
                case 13:
                    if(tt==TipoToken.IDENTIFICADOR) {
                        pila.push(17);
                        i++;
                        preanalisis = tokens.get(i);
                    }
                    else if(tt==TipoToken.COMA||tt==TipoToken.EOF){//reduccion de 15 (T2->Epsilon)
                        pila.push(16);
                    }
                    else{
                        if(tt==TipoToken.EOF)
                            System.out.println("\033[91mse esperaba un 'id' antes del fin de cadena\033[0m");
                        else
                            System.out.println("\033[91mse esperaba un 'id' antes de \033[0m"+preanalisis.lexema);
                        hayErrores=true;
                    }
                break;
                case 15:
                    if(tt==TipoToken.COMA||tt==TipoToken.EOF) {//reduccion de 11 (T->T,T1)
                        pila.pop();
                        pila.pop();
                        pila.pop();
                        if (pila.peek() == 10)
                            pila.push(11);
                        else
                            hayErrores= true;
                    }
                    else
                        hayErrores=true;
                break;
                case 16:
                    if(tt==TipoToken.COMA||tt==TipoToken.EOF) {//reduccion de 13 (T1->idT2)
                        pila.pop();
                        pila.pop();
                        if (pila.peek() == 10)
                            pila.push(12);
                        else if(pila.peek() == 14)
                            pila.push(15);
                        else
                            hayErrores= true;
                    }
                    else
                        hayErrores=true;
                break;
                case 17:
                    if(tt==TipoToken.COMA||tt==TipoToken.EOF) {//reduccion de 14 (T2->id)
                        pila.pop();
                        if (pila.peek() == 13)
                            pila.push(16);
                        else
                            hayErrores= true;
                    }
                    else
                        hayErrores=true;
                break;
                case 18:
                    if(tt==TipoToken.FROM) {//reduccion de 2 (D->distinct P)
                        pila.pop();
                        pila.pop();
                        if (pila.peek() == 2)
                            pila.push(3);
                        else
                            hayErrores= true;
                    }
                    else
                        hayErrores=true;
                break;
                case 19:
                    if(tt==TipoToken.COMA||tt==TipoToken.FROM) {//reduccion de 8 (A1->idA2)
                        pila.pop();
                        pila.pop();
                        if (pila.peek() == 2||pila.peek() == 4)
                            pila.push(8);
                        else if(pila.peek()==22)
                            pila.push(23);
                        else
                            hayErrores= true;
                    }
                    else
                        hayErrores=true;
                break;
                case 20:
                    if(tt==TipoToken.IDENTIFICADOR) {
                        pila.push(21);
                        i++;
                        preanalisis = tokens.get(i);
                    }
                    else{
                        if(tt==TipoToken.EOF)
                            System.out.println("\033[91mse esperaba un 'id' antes del fin de cadena\033[0m");
                        else
                            System.out.println("\033[91mse esperaba un 'id' antes de \033[0m"+preanalisis.lexema);
                        hayErrores=true;
                    }
                break;
                case 21:
                    if(tt==TipoToken.COMA||tt==TipoToken.FROM) {//reduccion de 9 (A2->.id)
                        pila.pop();
                        pila.pop();
                        if (pila.peek() == 9)
                            pila.push(19);
                        else
                            hayErrores= true;
                    }
                    else
                        hayErrores=true;
                break;
                case 22:
                    if(tt==TipoToken.IDENTIFICADOR) {
                        pila.push(9);
                        i++;
                        preanalisis = tokens.get(i);
                    }
                    else{
                        if(tt==TipoToken.EOF)
                            System.out.println("\033[91mse esperaba un 'id' antes del fin de cadena\033[0m");
                        else
                            System.out.println("\033[91mse esperaba un 'id' antes de \033[0m"+preanalisis.lexema);
                        hayErrores=true;
                    }
                break;
                case 23:
                    if(tt==TipoToken.COMA||tt==TipoToken.FROM) {//reduccion de 6 (A->A,A1)
                        pila.pop();
                        pila.pop();
                        pila.pop();
                        if (pila.peek() == 2||pila.peek() == 4)
                            pila.push(7);
                        else
                            hayErrores= true;
                    }
                    else
                        hayErrores=true;
                break;
            }//FIN DE SWITCH

        }while (!hayErrores && !salir);


        if(hayErrores){
            System.out.println("\033[93mConsulta invalida\033[0m");
            return false;
        }
        else{
            System.out.println("\033[92mConsulta correcta\033[0m");
            return true;
        }
    }
}

