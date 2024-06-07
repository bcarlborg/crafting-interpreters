# Chapter 06: Parsing Expressions
_NOTE: this writeup is still a work in progress!_

## Grammars
In the previous chapter, we created our grammar for an abstract syntax tree. This will be the intermediate representation that we actually traverse in order to actually execute the code that we interpret in our programming language.

Abstract syntax tree grammar
```
expression     → literal
               | unary
               | binary
               | grouping ;

literal        → NUMBER | STRING | "true" | "false" | "nil" ;
grouping       → "(" expression ")" ;
unary          → ( "-" | "!" ) expression ;
binary         → expression operator expression ;
operator       → "==" | "!=" | "<" | "<=" | ">" | ">="
               | "+"  | "-"  | "*" | "/" ;
```

Now that we need to parse our input tokens into a structured form though, this abstract syntax tree grammar will not work as well. The grammar is ambiguous, and it does not encode the levels of precedence for the operators into its structure.

Or said another way, there is nothing to indicate that `*` is a higher precedence operator that `+` or that `+` and `-` are left associative and so on.

For parsing, we will be better served by having a grammar that is more complex and encodes more of the semantics of our programming language in it's structure. So, we introduce this grammar for parsing expressions.

Concrete syntax tree grammar:
```
expression     → equality ;
equality       → comparison ( ( "!=" | "==" ) comparison )* ;
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term           → factor ( ( "-" | "+" ) factor )* ;
factor         → unary ( ( "/" | "*" ) unary )* ;
unary          → ( "!" | "-" ) unary
               | primary ;
primary        → NUMBER | STRING | "true" | "false" | "nil"
               | "(" expression ")" ;
```

This grammar encodes the different precedence levels of each operation

## Parser Class Code Pointers
In this chapter, we build out the Parser class

### Fields and methods for interacting with tokens
- the class has a `current` field which is an index pointer into a list of tokens.
- the class has private base methods for reading from the token stream:
  - `peek()` which returns the token pointed to by current
  - `check()` which returns a boolean indicating if the current token matches current
  - `advance()` which returns the value at the `current` pointer, then moves current forward.
  - `previous()` which returns the last character that was advanced.
  - `isAtEnd()` which returns true if the current token is EOF
- Helpful methods which wrap those primitives are:
  - `match()` which will advance if the current character matches one or more of the available token types
  - `consume()` which advances and returns if the token type is matched

### Syntax recognizing methods
Each of the non-terminals in the grammar gets its own parsing function. The parsing function will try to find any of the appropriate non-terminals by calling the function for whatever non-terminals appear on the right hand side of the production. So for example, the function for the `term` non-terminal is as follows.

```java
private Expr term() {
    Expr expr = factor();               // <----- right hand side non-terminal

    while (match(MINUS, PLUS)) {
        Token operator = previous();
        Expr right = factor();          // <----- right hand side non-terminal

        // construct the abstract syntax tree node
        expr = new Expr.Binary(expr, operator, right);
    }

    return expr;
}

```

One observation about this recursive descent parser that took me a while to realize is that the functions themselves are created based on the concrete syntax tree grammar and the return values of the functions are from the abstract syntax tree grammar.

### Follow up questions for myself
**How would this grammar encode right associative operators?**
This grammar sort of skirts around the associativity distinction at the grammar level and instead encodes that into the parser itself. For example, looking at the grammar rule for the `equality` production, we see:

```
equality       → comparison ( ( "!=" | "==" ) comparison )* ;
```

And when we look at the parsing function that implements this expression parsing we see:

```
private Expr equality() {
    Expr expr = comparison();

    while (match(BANG_EQUAL, EQUAL_EQUAL)) {
        Token operator = previous();
        Expr right = comparison();
        expr = new Expr.Binary(expr, operator, right);
    }

    return expr;
}
```

You can see here that we implement a small while loop within the recursive descent parser to iterate forward over the successive equality operators. Then we construct each new `expr` expression we can encode the associativity there.

If we wanted to encode right associativity, we would need to make the the while loop in the parsing function self recursive rather than a while loop.
