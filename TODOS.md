# TODOs
This file is an informal list of the current TODO's I am working through. This list is not guaranteed to be accurate, specific, or up-to-date.

## Current TODOs
- 07 video
    - re-read the chapter one more time to see what the important big picture details are again
    - show an AST, show that we are producing values for the ast by doing a depth first traversal of the nodes
    - focus on the values of the nodes (double, boolean, string) but emphasize that the types for nodes only specify type object, so we need to verify them and downcast at runtime.
        - emphasize that our grammar allows invalid types for the operators. There is nothing stopping you from creating a valid expression in the language from the string `"true + true"`.
    - create a simple table showing all the operators and their allowed types