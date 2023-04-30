//package mutation;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.body.VariableDeclarator;
//import com.github.javaparser.ast.expr.BinaryExpr;
//import com.github.javaparser.ast.expr.BooleanLiteralExpr;
//import com.github.javaparser.ast.expr.NameExpr;
//import com.github.javaparser.ast.expr.UnaryExpr;
//import com.github.javaparser.ast.stmt.IfStmt;
//import com.github.javaparser.ast.visitor.ModifierVisitor;
//import com.github.javaparser.ast.visitor.Visitable;
//
//import java.io.IOException;
//import static mutation.CodeModifierTest.writer;
//public class invertNegative implements MutantCreator {
//    public invertNegative() throws IOException {
//    }
//    public Object[] createMutant(CompilationUnit cu, int index) {
//        final boolean[] fileMutationCount = {false};
//        final int[] count1 = {0};
//        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
//            @Override
//            public Visitable visit(UnaryExpr n, Void arg) {
//                if(n.getOperator() == UnaryExpr.Operator.MINUS){
//                    return new UnaryExpr(n.getExpression(),UnaryExpr.Operator.PLUS);
//                }
//                return super.visit(n, arg);
//            }
//        };
//        visitor.visit(cu, null);
//        return new Object[] {cu, fileMutationCount[0]};
//    }
//}
