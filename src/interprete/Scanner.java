/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interprete;

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
        int estado = 0, nLinea=1;
        String lexema = "";
        char c;
        
        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);
            //Para el contador de lineas para errores
            if(c == '\n'){
                nLinea++;
            }
            
            switch (estado){
                case 0:
                    //Para detectar identificadores y palabras reservadas
                    if(Character.isLetter(c)){
                        estado = 13;
                        lexema += c;
                    }
                    //Para identificar numeros
                    else if(Character.isDigit(c)){
                        estado = 15;
                        lexema += c;
                    }
                    //Para operadores relacionales
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
                    //Para comentarios
                    else if(c=='/')
                    {
                        estado=26;
                        lexema+=c;
                    }
                    //Para cadenasentre comillas
                    else if(c=='"')
                    {
                        estado=24;
                        lexema+=c;
                    }
                    //Para tokens de un solo caracter
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
                    }
                    //Para caracteres no definidos en el lenguaje
                    else if(c == '[' || c == ']' || c == '$' || c == '%' || c == '&' || c == '_' || c == '#'){    
                        i = source.length();                      
                        Interprete.error(nLinea, "Caracter no definido en el lenguaje.");
                                                                      
                    }
                    break;
                //Para > y sus combinaciones
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
                //Para < y sus combinaciones
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
                //Para = y sus combinaciones
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
                //Para ! y sus combinaciones
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
                //Para identificadores y palabras reservadas
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
                //Para numeros
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
                //Para numeros flotantes
                case 16:
                    if(Character.isDigit(c)){
                        estado = 17;
                        lexema += c;
                    }else{
                        i = source.length();
                        Interprete.error(nLinea, "Se esperaba un digito.");
                    }
                    break;
                //Para numeros flotantes con E
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
                //Para numeros flotantes con E
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
                //Para numeros
                case 19:
                    if(Character.isDigit(c)){
                        estado = 20;
                        lexema += c;
                    }else{
                        i = source.length();
                        Interprete.error(nLinea, "Se esperaba un digito.");
                    }
                    break;
                //Para generar token de numeros con E
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
                //Para Strings
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
                //Para comentarios multilinea o de una sola linea
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
                //Continuacion de comentarios
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
                //Continuacion de comentarios
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
                //Continuacion de comentarios
                case 29:
                    estado=0;
                    lexema="";
                    i--;
                    break;
                //Continuacion de comentarios
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
                //Continuacion de comentarios
                case 31:
                    lexema="";
                    estado=0;
                    i--;
                    break;
            }

        }
        tokens.add(new Token(TipoToken.EOF, "", null));
        //Para error en caso de no cerrar comillas
        if(estado == 24)
            Interprete.error(nLinea-1, "Se esperaban unas comillas de cierre.");
        return tokens;
    }
}

