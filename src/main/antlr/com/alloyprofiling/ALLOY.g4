grammar ALLOY;
@header {
 package com.alloyprofiling;
}

specification : module? open* module? paragraph* EOF | EOF;

module : 'module' name  ( '[' 'exactly'? name  (',' 'exactly'? name)*  ']' )?;

names_opt: names?;

as_name_opt: ('as' name)?;

para_open: ('[' names_opt ']')?;

open : priv 'open' name para_open as_name_opt ;

macro_expr: '='? '{' (expr* | decls)'}' | '=' (expr | decls);

macro: 'let' name ('[' names ']')? macro_expr;

paragraph : factDecl | assertDecl | funDecl | cmdDecl | enumDecl | sigDecl | predDecl | macro;

factDecl : 'fact' name? block;

name_opt : name?;

assertDecl : 'assert' name_opt block;

nameID: name '.' ID | ID;

decls_f: '{' decls '}';

funExpr: expr;

funDecl : priv 'fun' nameID paraDecls_opt ':' expr '{' funExpr '}';

nameOrBlock: (name|block)? | (nameID block)?;

name_cmd_opt : (name ':')?;

scope_opt: scope?;

run_or_check: 'run'|'check';

cmdDecl : name_cmd_opt run_or_check nameOrBlock scope_opt;

paraDecls: '(' (decl (','decl)*)? ')' | '[' (decl (','decl)*)?']';

paraDecls_opt: (paraDecls)?;

predDecl: 'private'? 'pred' nameID paraDecls_opt block;

typescopes: typescope (',' typescope)*;

but_typescopes: ('but' typescopes)?;

expect_digit: ('expect' DIGIT)?;

scope : 'for' number but_typescopes expect_digit | 'for' typescopes expect_digit | 'expect' DIGIT;

exactly_opt: 'exactly'?;

typescope : exactly_opt number (name | 'seq' | 'int');

decls : (','? decl (','decl)* )?;

multiplicity: mult?;

abs: 'abstract'?;

priv: 'private'?;

abs_multiplicity: abs multiplicity | multiplicity abs;

sigDecl : priv abs_multiplicity 'sig' names sigExtension '{' decls '}' block_opt;

names: name (',' name)*;

enumDecl : 'enum' name '{' names '}';

mult : 'lone' | 'one' | 'some';

union: name ('+' name)*?;

superSet: name | union;

sigExt : 'extends' name | 'in' superSet ;

sigExtension: sigExt?;

exprs : (expr (',' expr)*)?;

notOp: ('!' | 'not')?;

decls_e : decl (',' decl)*;

expr : 'let' letDecl (',' letDecl)* blockOrBar
       | quant decls_e blockOrBar
       | unOp expr | expr binOp expr | expr arrowOp expr
       | expr notOp compareOp expr
       | expr ('=>'|'implies') expr 'else' expr
       | 'sum' '['? exprs']'? | expr '[' exprs']' | disjFunc
       | constant  |'int' | 'seq/Int' | '(' expr ')'| name | '@'name | 'this'
       | block | quant expr binOp expr
       | '{' decl (',' decl)* blockOrBar '}' | decls_f |STRING;

num: '-'? number;

const_sets: 'none' | 'univ' | 'iden';

constant : num | const_sets;

disjFunc : 'disj' '[' (expr (',' expr)*)? ']';

disjoint: ('disj' | 'disjoint')?;

disj: 'disj'?;

comma_opt: ','?;

decl : priv disjoint names ':' disj expr comma_opt | name '=' expr;

letDecl : name '=' expr;

quant : 'all' | 'no' | 'some' | 'lone' | 'one' | 'sum';

setCard: '#';

tcOp :  '*' | '^' ;

unOp : '!' | 'not' | 'no' | mult | 'set' | setCard | '~' | tcOp | 'seq';

bit_shifter_operators: '<<' | '>>' | '>>>';

dotOp: '.';

add: '+';

sub: '-';

binOp : '||' | 'or' | '&&' | 'and' | '<=>' | 'iff' | '=>' | 'implies' |
        '&' | add | sub | '++' | '<:' | ':>' | dotOp | bit_shifter_operators;

mult_or_set: (mult |'set')?;

arrowOp : mult_or_set '->' mult_or_set;

rel_operators: '=' | '<' | '>' | '=<' | '>='| '<=';

compareOp : rel_operators | 'in';

block : '{' expr* '}';

block_opt: block?;

blockOrBar : block | bar expr;

bar : '|';

name : ('this/')? (ID '/')* ID;

DIGIT : [0-9] ;
number: DIGIT+;
ID : ALPHA ( ALPHA | DIGIT )* | STRING;
ALPHA: [a-zA-Z'"\u0080-\uFFFF_]+;
WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
COMMENT: '/*' .*? '*/' -> skip;
LINE_COMMENT: ('//' | '--') ~[\r\n]* -> skip;
STRING: '"' (~'"'|'/"')* '"';

