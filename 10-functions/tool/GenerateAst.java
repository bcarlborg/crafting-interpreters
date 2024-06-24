package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output_directory>");
            System.exit(64);
        }

        String outputDir = args[0];

        defineAst(outputDir, "Expr", Arrays.asList(
            "Assign   : Token name, Expr value",
            "Binary   : Expr left, Token operator, Expr right",
            "Call     : Expr callee, Token paren, List<Expr> arguments",
            "Grouping : Expr expression",
            "Literal  : Object value",
            "Logical  : Expr left, Token operator, Expr right",
            "Unary    : Token operator, Expr right",
            "Variable : Token name"
        ));

        defineAst(outputDir, "Stmt", Arrays.asList(
            "Block      : List<Stmt> statements",
            "Expression : Expr expression",
            "Function   : Token name, List<Token> params, List<Stmt> body",
            "If         : Expr condition, Stmt thenBranch, Stmt elseBranch",
            "Print      : Expr expression",
            "Var        : Token name, Expr initializer",
            "While      : Expr condition, Stmt body"
        ));
    }

    private static void defineAst(
        String outputDir,
        String baseName,
        List<String> types
    ) throws IOException {
        String path = outputDir + '/' + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package jlox_src;");
        writer.println();
        writer.println("// ====================================================================");
        writer.println("// GENERATED FILE -- DO NOT MODIFY DIRECTLY");
        writer.println("// This file was generated by tool/GenerateAst.java");
        writer.println("// ====================================================================");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + " {");

        // define the abstract visitor interface each subclass implements
        defineVisitor(writer, baseName, types);

        // The AST classes
        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        }

        // FIRST VISITOR -- accept()
        writer.println();
        writer.println("    abstract <R> R accept(Visitor<R> visitor);");

        writer.println("}");
        writer.close();
    }

    private static void defineType(
        PrintWriter writer, String baseName, String className, String fieldList
    ) {
        // Class declaration -- one indent level
        writer.println("    static class " + className + " extends " + baseName + " {");

        // Constructor declaration -- two indent levels
        writer.println("        " + className + "(" + fieldList + ") {");

        // Constructor body -- three indent levels -- Store parameters in fields.
        String[] fields = fieldList.split(", ");
        for (String field: fields) {
            String name = field.split(" ")[1];
            writer.println("            this." + name + " = " + name + ";");
        }

        // Constructor closing curly -- two indent levels
        writer.println("        }");

        // visitor function -- two indent levels
        writer.println();
        writer.println("        @Override");
        writer.println("        <R> R accept(Visitor<R> visitor) {");
        writer.println("            return visitor.visit" + className + baseName + "(this);");
        writer.println("        }");

        // Field declarations -- two indent levels
        writer.println();
        for (String field : fields) {
            writer.println("        final " + field + ";");
        }

        // Class closing curly -- one indent level
        writer.println("    }");
    }

    public static void defineVisitor(
        PrintWriter writer, String baseName, List<String> types
    ) {
        // visitor declaration -- one indent level
        writer.println("    // The interface for a class that operates on every " + baseName + " node");
        writer.println("    interface Visitor<R> {");

        // visitor visit function declaration -- two indent level
        for (String type: types) {
            String typeName = type.split(":")[0].trim();
            writer.println("        R visit" + typeName + baseName + "(" +
                typeName + " " + baseName.toLowerCase() + ");");
        }

        // visitor declaration closing curly -- one indent level
        writer.println("    }");
        writer.println("");

    }
}
