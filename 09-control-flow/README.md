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


**9.5 For Loops**

**


