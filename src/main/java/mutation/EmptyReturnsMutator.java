//package mutation;
//
//import com.github.javaparser.ParseException;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.NodeList;
//import com.github.javaparser.ast.expr.BinaryExpr;
//import com.github.javaparser.ast.expr.Expression;
//import com.github.javaparser.ast.stmt.ReturnStmt;
//import com.github.javaparser.ast.visitor.ModifierVisitor;
//import com.github.javaparser.ast.visitor.Visitable;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//import com.github.javaparser.*;
//import com.github.javaparser.ast.expr.*;
//import com.github.javaparser.resolution.types.ResolvedType;
//
//import static mutation.CodeModifierTest.writer;
//public class EmptyReturnsMutator implements MutantCreator {
//    private static final Map<String, String> EMPTY_VALUES = new HashMap<String, String>() {{
//        put("java.lang.String", "\"\"");
//        put("java.util.Optional", "Optional.empty()");
//        put("java.util.List", "Collections.emptyList()");
//        put("java.util.Collection", "Collections.emptyList()");
//        put("java.util.Set", "Collections.emptySet()");
//        put("java.lang.Integer", "0");
//        put("java.lang.Short", "0");
//        put("java.lang.Long", "0L");
//        put("java.lang.Character", "'\\u0000'");
//        put("java.lang.Float", "0.0f");
//        put("java.lang.Double", "0.0d");
//    }};
//
//    public EmptyReturnsMutator() throws IOException {
//    }
//
//    public Object[] createMutant(CompilationUnit cu, int index) {
//        final boolean[] fileMutationCount = {false};
//        final int[] count1 = {0};
//        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
//            @Override
//            public Visitable visit(ReturnStmt n, Void arg) {
//                    Optional<Expression> expr = n.getExpression();
//                    expr.ifPresent(expression -> {
//                        if (expression instanceof NameExpr) {
//                            NameExpr nameExpr = (NameExpr) expression;
//                            System.out.println("Variable name: " + nameExpr);
//                        }
//                    });
//                    return new ReturnStmt();
//            }
//        };
//
//        visitor.visit(cu, null);
//        return new Object[]{cu, fileMutationCount[0]};
//    }
//
//}
