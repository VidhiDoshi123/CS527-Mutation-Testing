package mutation;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;
import java.util.*;

import com.github.javaparser.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.resolution.types.ResolvedType;

import static com.github.javaparser.StaticJavaParser.parseExpression;
import static mutation.CodeModifierTest.writer;
public class EmptyReturnsMutator implements MutantCreator {
    private static final Map<String, String> EMPTY_VALUES = new HashMap<>() {{
        put("Set", "java.util.Collections.emptySet()");
        put("int", String.valueOf(0));
        put("String", "\"\"");
        put("List", "java.util.Collections.emptyList()");
        put("char", String.valueOf(0));
        put("Optional", "Optional.empty()");
        put("Collection", "Collections.emptyList()");
        put("Short", String.valueOf(0));
        put("Long", String.valueOf(0));
        put("Float", String.valueOf(0));
        put("Double", String.valueOf(0));
    }};

    /*
    our objective is
    1) search for return -> easy
    2) extract data type of variable associated with return
    3) use hashMap to return equivalent -> easy
     */
    public EmptyReturnsMutator() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(MethodDeclaration n, Void arg) {
                Type returnType = n.getType();
                if(returnType != null &&!(returnType instanceof VoidType)){
                    String dataType = returnType.toString();
                    int index = dataType.indexOf('<');
                    if (index != -1) {
                        dataType = dataType.substring(0, index).trim().toString();
                    }
                    if (EMPTY_VALUES.containsKey(dataType)) {
                        Object emptyValue = EMPTY_VALUES.get(dataType);
                        System.out.println(" for the dataType: " + dataType +" emptyValue is: "+emptyValue);
                        List<ReturnStmt> returnStmts = n.findAll(ReturnStmt.class);
                        for (ReturnStmt returnStmt : returnStmts) {
                            fileMutationCount[0] = true;
                            returnStmt.setExpression(parseExpression(emptyValue.toString()));
                        }
                    }
                }
                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, fileMutationCount[0]};
    }
}
