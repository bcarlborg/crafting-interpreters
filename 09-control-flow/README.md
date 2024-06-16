# Chapter 09: Control Flow
__This chapter is still a work in progress__

## Notes on implementation
**9.2 Conditional Execution**
- we add an if statement
    - this makes use of of the `statement` syntax rule rather than the declaration syntax rule that we created before
    - this ensure that the only way we can add variable declarations into our if statement is by adding a block statement as the
    arm of our if statement
- we create a syntactic if statement rule and we also create a new node type for our if statement and we include and optional if branch.
    - the syntactic node is composed of an expression condition along with a Stmt thenBranch and a Stmt elseBranch

**9.3 Logical Operators**
- add logical `and` and logical `or` operators to our language.
- These are grouped in with control flow because depending on the return value of the left hand side operator in the expression, the right hand side
may not evaluate.
  - for example, if your expression is `true or 2 + 2`, the expression will simply evaluate to `true` without ever processing `2 + 2` because we do not need
  to calculate that in order to evaluate the expression.

**9.4 While loops**
- Add a while looping construct to the language by creating a new while loop syntactic structure and abstract syntax tree node.
- The while loop abstract syntax tree node is simply a statement and a condition. We execute the condition until the condition is false.

**9.5 For Loops**
- We create a for loop syntax construct. The for loop (syntactically) is composed of 3 interior expressions in the loop and a statement body.
    - I sort of assumed that the interior of the for loop would be statements rather than expressions with `';'`s on the end.
- Instead of creating a new syntax tree node for for loops though, we translate the syntax of our for loop into while loop AST nodes.
- so, in effect, a for loop gets translated to:
```
{
    for loop initializer statement
    while (for loop condition) {
        for loop body
        for loop increment
    }
}
```
- notice that we need a wrapping scope around the whole while loop to ensure that the initializer statement is scoped only to the while loop.

## Notes
- How do iterators work?