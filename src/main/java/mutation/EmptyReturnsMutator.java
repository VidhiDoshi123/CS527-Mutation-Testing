//package mutation;
//
//import com.github.javaparser.ParseException;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.expr.BinaryExpr;
//import com.github.javaparser.ast.expr.Expression;
//import com.github.javaparser.ast.stmt.ReturnStmt;
//import com.github.javaparser.ast.visitor.ModifierVisitor;
//import com.github.javaparser.ast.visitor.Visitable;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
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
//                if (count1[0] == index) {
//                    count1[0] += 1;
//                    fileMutationCount[0] = true;
//                    if (n.getExpression() != null) {
//                        System.out.println("n.getExpression() : "+n.getExpression());
////                        String exprType = n.getExpression().isPresent().describe();
////                        String emptyValue = EMPTY_VALUES.get(exprType);
////                        if (emptyValue != null) {
////                            Expression emptyExpr = StaticJavaParser.parseExpression(emptyValue);
////                            return new ReturnStmt(emptyExpr);
////                        }
//                    }
//                    return new ReturnStmt();
//                } else {
//                    count1[0] += 1;
//                    return super.visit(n, arg);
//                }
//            }
//        };
//
//        visitor.visit(cu, null);
//        return new Object[]{cu, fileMutationCount[0]};
//    }
//
//}
