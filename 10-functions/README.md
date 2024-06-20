# Chapter 09: Control Flow
_This chapter is a work in progress_

## Notes
**10.1: Function Calls**
Function calls are another type of expression. They are a syntactic structure that will evaluate to a new value.
We introduce them into our syntax and our abstract syntax tree in the following way.

```
New syntax grammar constructs
call           → primary ( "(" arguments? ")" )*
arguments      → expression ( "," expression )*

New AST Expression Node
Call     : Expr callee, Token paren, List<Expr> arguments
```

Interestingly, the new AST node also stores a parenthesis from the input syntax. I was confused at first when I saw this. After introducing this new node, the book does explain that this is the closing paren of the call, and
it is used so that we can report runtime errors.

In our code to evaluate the call, we cast the expression for our `callee` into a new `LoxCallable` object, then we call
`loxCallable.call(Interpreter interpreter, arguments)` on the object. The lox callable object also contains information about the function's arity, so we can throw an error if too many or too few arguments are passed.


**10.2: Native Functions**
Many programming languages introduce a handful of "native" functions to their language. These native functions are implemented in the "native" language of the interpreter. So in our case, these functions are implemented in java.

These functions are sometimes called primitives or built-ins.

Note: native functions are not the only types of functions that can be implemented in another programming language.
Many programming languages offer some form of Foreign Function Interface (FFI) that allows users to implement functions in another language.

We decide to add a `clock()` built-in to our language that will print the current time in micro-seconds.

Doing so is as simply as adding a new lox-callable function to our global scope that implements the class methods on lox callable (`arity()`, `call()`, `toString()`).

After adding that new function to the global scope, our program can now evaluate `clock()` by evaluating the new `Call` expression type.

**10.3: Function Declarations**


**10.4: Function Objects**


**10.5: Return Statements**


**10.6: Local Functions and Closures**


## Reflections