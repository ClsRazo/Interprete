import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    public Scanner(String source){
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        int estado = 0, nLinea=0;
        String lexema = "";
        char c;
        

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);
            //Para el contador de lineas para errores
            if(c == '\n'){
                nLinea++;
            }
            //deteccion de tokens de un caracter
        
            switch (estado){
                case 0:
                    if(Character.isLetter(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;

                        /*while(Character.isDigit(c)){
                            lexema += c;
                            i++;
                            c = source.charAt(i);
                        }
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        lexema = "";
                        estado = 0;
                        tokens.add(t);
                        */
                    }
                    else if(c == '>'){
                        estado = 1;
                        lexema += c;
                    }
                    else if(c == '<'){
                        estado = 4;
                        lexema += c;
                    }
                    else if(c == '='){
                        estado = 7;
                        lexema += c;
                    }
                    else if(c == '!'){
                        estado = 10;
                        lexema += c;
                    }
                    else if(c=='/')
                    {
                        estado=26;
                        lexema+=c;
                    }
                    else if(c=='"')
                    {
                        estado=24;
                        lexema+=c;
                    }
                    else if(c==';')
                    {
                        lexema+=c;
                        tokens.add(new Token(TipoToken.SEMICOLON, lexema, null));
                        estado=0;
                        lexema="";
                    }
                    else if(c=='+')
                    { 
                        lexema+=c;
                        tokens.add(new Token(TipoToken.PLUS, lexema,null) );
                        lexema="";
                        estado=0; 
                    }
                    else if(c=='-')
                    {
                        lexema+=c;
                        tokens.add(new Token(TipoToken.MINUS, lexema,null) );
                        lexema="";
                        estado=0; 
                    }
                    else if(c=='*')
                    {
                        lexema+=c;
                        tokens.add(new Token(TipoToken.STAR, lexema,null) );
                        lexema="";
                        estado=0; 
                    }
                    
                    
                    else if(c=='.')
                    {
                        lexema+=c;
                        tokens.add(new Token(TipoToken.DOT, lexema,null) );
                        lexema="";
                        estado=0; 
                    }
                    else if(c==',')
                    {
                        lexema+=c;
                        tokens.add(new Token(TipoToken.COMMA, lexema,null) );
                        lexema="";
                        estado=0; 
                    }
                    else if(c=='(')
                    {
                        lexema+=c;  
                        tokens.add(new Token(TipoToken.LEFT_PAREN, lexema,null) );
                        lexema="";
                        estado=0; 

                    }
                    else if(c==')')
                    {
                        lexema+=c;  
                        tokens.add(new Token(TipoToken.RIGHT_PAREN, lexema,null) );
                        lexema="";
                        estado=0; 

                    }
                    else if(c=='{')
                    {
                        lexema+=c;
                        tokens.add(new Token(TipoToken.LEFT_BRACE, lexema,null) );
                        lexema="";
                        estado=0; 
                    }
                    else if(c=='}')
                    {
                        lexema+=c;
                        tokens.add(new Token(TipoToken.RIGHT_BRACE, lexema,null) );
                        lexema="";
                        estado=0; 
                    }else if(c == '[' || c == ']' || c == '$' || c == '%' || c == '&' || c == '_' || c == '#'){    
                        i = source.length();                      
                        Interprete.error(nLinea, "Caracter no definido en el lenguaje.");                                              
                    }
                    break;
            
                case 1:
                    if(c == '='){
                        lexema += c;
                        Token t = new Token(TipoToken.GREATER_EQUAL,lexema);
                        tokens.add(t);
                        lexema = "";
                        estado = 0;
                    }else{
                        Token t = new Token(TipoToken.GREATER,lexema);
                        tokens.add(t);
                        lexema = "";
                        estado = 0;
                        i--;
                    }
                    break;
                case 4:
                    if(c == '='){
                        lexema += c;
                        Token t = new Token(TipoToken.LESS_EQUAL,lexema);
                        tokens.add(t);
                        lexema = "";
                        estado = 0;
                    }else{
                        Token t = new Token(TipoToken.LESS,lexema);
                        tokens.add(t);
                        lexema = "";
                        estado = 0;
                        i--;
                    }
                    break;
                case 7:
                    if(c == '='){
                        lexema += c;
                        Token t = new Token(TipoToken.EQUAL_EQUAL,lexema);
                        tokens.add(t);
                        lexema = "";
                        estado = 0;
                    }else{
                        Token t = new Token(TipoToken.EQUAL,lexema);
                        tokens.add(t);
                        lexema = "";
                        estado = 0;
                        i--;
                    }
                    break;
                case 10:
                    if(c == '='){
                        lexema += c;
                        Token t = new Token(TipoToken.BANG_EQUAL,lexema);
                        tokens.add(t);
                        lexema = "";
                        estado = 0;
                    }else{
                        Token t = new Token(TipoToken.BANG,lexema);
                        tokens.add(t);
                        lexema = "";
                        estado = 0;
                        i--;
                    }
                    break;
                case 13:
                    if(Character.isLetterOrDigit(c)){
                        estado = 13;
                        lexema += c;
                    }
                    else{
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if(tt == null){
                            Token t = new Token(TipoToken.IDENTIFIER, lexema);
                            tokens.add(t);
                        }
                        else{
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                        }

                        estado = 0;
                        lexema = "";
                        i--;

                    }
                    break;

                case 15:
                    if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;
                    }
                    else if(c == '.'){
                        estado = 16;
                        lexema += c;
                    }
                    else if(c == 'E'){
                        estado = 18;
                        lexema += c;
                    }
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 16:
                    if(Character.isDigit(c)){
                        estado = 17;
                        lexema += c;
                    }else{
                        i = source.length();
                        Interprete.error(nLinea, "Se esperaba un digito.");
                    }
                    break;
                case 17:
                    if(Character.isDigit(c)){
                        estado = 17;
                        lexema += c;
                    }else if(c == 'E' || c == 'e'){
                        estado = 18;
                        lexema += c;
                    }else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.parseDouble(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 18:
                    if(c == '+' || c == '-'){
                        estado = 19;
                        lexema += c;
                    }else if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }else{
                        i = source.length();
                        Interprete.error(nLinea, "Se esperaba un signo (+,-) o un digito.");
                    }
                    break;
                case 19:
                    if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }else{
                        i = source.length();
                        Interprete.error(nLinea, "Se esperaba un digito.");
                    }
                    break;
                case 20:
                    if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.parseDouble(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 24:
                    if(c=='\n')
                    {
                        i = source.length();
                    }
                    else if(c=='"')
                    {
                        lexema+=c;
                        Token t= new Token(TipoToken.STRING, lexema, lexema.substring(1, lexema.length()-1));
                        tokens.add(t);
                        estado=0;
                        lexema="";
                    }
                    else
                    {
                        estado=24;
                        lexema+=c;
                    }
                    
                    
                    break;
                case 26:
                    if(c=='*')
                    {
                        estado=27;
                    }
                    else if(c=='/')
                    {
                        estado=30;
                    }
                    else{
                        Token t=new Token(TipoToken.SLASH, lexema, null);
                        tokens.add(t);
                        i--;
                        estado=0;
                        lexema="";
                    }
                    break;
                case 27:
                    if(c=='*')
                    {
                        estado=28;
                    }
                    else
                    {
                        estado=27;
                    }
                    
                    break;
                case 28:
                    if(c=='/')
                    {
                        estado=29;
                    }
                    else if(c == '*')
                    {
                        estado=28;
                    }
                    else {
                        estado = 27;
                    }
                    break;
                case 29:
                    estado=0;
                    lexema="";
                    i--;
                    break;
                case 30:
                    if(c=='\n')
                    {
                        estado=31;
                    }
                    else
                    {
                        estado=30;
                    }
                    break;
                case 31:
                    lexema="";
                    estado=0;
                    i--;
                    break;
            }

        }
        if(estado == 24)
            Interprete.error(nLinea, "Se esperaban unas comillas de cierre.");
        return tokens;

    }
}
