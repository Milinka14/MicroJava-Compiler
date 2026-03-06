package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn);
	}

	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline + 1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%


" "     { }
"\t"    { }
"\r\n"  { }
"\n"    { }
"\f"    { }


"//"            { yybegin(COMMENT); }
<COMMENT> .     { }
<COMMENT> "\r\n"  { yybegin(YYINITIAL); }


"program"   { return new_symbol(sym.PROG); }
"const"     { return new_symbol(sym.CONST); }
"enum"      { return new_symbol(sym.ENUM); }
"void"      { return new_symbol(sym.VOID); }
"while"      { return new_symbol(sym.WHILE); }

"read"      { return new_symbol(sym.READ); }
"print"     { return new_symbol(sym.PRINT); }
"new"       { return new_symbol(sym.NEW); }
"length"    { return new_symbol(sym.LENGTH); }

"if"        { return new_symbol(sym.IF); }
"else"      { return new_symbol(sym.ELSE); }
"for"       { return new_symbol(sym.FOR); }

"break"     { return new_symbol(sym.BREAK); }
"continue"  { return new_symbol(sym.CONTINUE); }
"return"    { return new_symbol(sym.RETURN); }

"switch"    { return new_symbol(sym.SWITCH); }
"case"      { return new_symbol(sym.CASE); }


"true"		{ return new_symbol(sym.BOOL, 1); }
"false"     { return new_symbol(sym.BOOL, 0); }

[0-9]+      { return new_symbol(sym.NUMBER, new Integer(yytext())); }

"'"."'"   { return new_symbol(sym.CHARACTER, new Character(yytext().charAt(1))); 	}

([a-z]|[A-Z])[a-z|A-Z|0-9|_]* {
	return new_symbol(sym.IDENT, yytext());
}

"+"     { return new_symbol(sym.PLUS); }
"*"     { return new_symbol(sym.MUL); }
"/"     { return new_symbol(sym.DIV); }
"%"     { return new_symbol(sym.MOD); }

"="     { return new_symbol(sym.ASSIGN); }
"++"    { return new_symbol(sym.INC); }
"--"    { return new_symbol(sym.DEC); }
"-"     { return new_symbol(sym.MINUS); }

"=="    { return new_symbol(sym.EQ); }
"!="    { return new_symbol(sym.NE); }
">="    { return new_symbol(sym.GE); }
">"     { return new_symbol(sym.GT); }
"<="    { return new_symbol(sym.LE); }
"<"     { return new_symbol(sym.LT); }

"&&"    { return new_symbol(sym.AND); }
"||"    { return new_symbol(sym.OR); }

"?"     { return new_symbol(sym.QUESTION); }
":"     { return new_symbol(sym.COLON); }

";"     { return new_symbol(sym.SEMI,';'); }
","     { return new_symbol(sym.COMMA); }
"."     { return new_symbol(sym.DOT); }

"("     { return new_symbol(sym.LPAREN); }
")"     { return new_symbol(sym.RPAREN); }
"["     { return new_symbol(sym.LBRACKET); }
"]"     { return new_symbol(sym.RBRACKET); }
"{"     { return new_symbol(sym.LBRACE); }
"}"     { return new_symbol(sym.RBRACE); }


. {
	System.err.println(
		"Leksicka greska (" + yytext() + 
		") u liniji " + (yyline + 1)
	);
}
