//package mutation;
//
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.expr.*;
//import com.github.javaparser.ast.visitor.ModifierVisitor;
//import com.github.javaparser.ast.visitor.Visitable;
//
//
//import java.io.IOException;
//import static mutation.CodeModifierTest.writer;
//public class uoiOperator implements MutantCreator {
//
//    public uoiOperator() throws IOException {
//    }
//    public Object[] createMutant(CompilationUnit cu, int index) throws IOException {
//        final boolean[] fileMutationCount = {false};
//        final int[] count1 = {0};
//
//        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
//
//            @Override
//            public Visitable visit(VariableDeclarationExpr n, Void args){
//
//
//                    if (count1[0] == index){
//
//                            Expression variable = n.getVariables();
//                            UnaryExpr.Operator operator;
//
//                            // Decide the operator based on a random coin toss
//                            if (Math.random() < 0.5) {
//                                operator = UnaryExpr.Operator.PREFIX_INCREMENT;
//                            } else {
//                                operator = UnaryExpr.Operator.PREFIX_DECREMENT;
//                            }
//
//                            // Create a new expression with the unary operator and the variable call
//                            UnaryExpr newExpression = new UnaryExpr(variable, operator);
//
//                            // Replace the old expression with the new expression
//                            n.replace(newExpression);
//
//
//                    } else {
//                        count1[0] +=1;
//                    }
//
//
//                return super.visit(n,args);
//            }
//
//        };
//        visitor.visit(cu,null);
//        return new Object[] {cu, fileMutationCount[0]};
//    }
//
//    public Object[] generateAllMutants(CompilationUnit cu) {
//        final int[] mutantGenerated = {0};
//        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
//            @Override
//            public Visitable visit(VariableDeclarationExpr n, Void args){
//                n.getVariables().forEach(var -> {
//                    var.setInitializer(new UnaryExpr(var.getInitializer().get(), UnaryExpr.Operator.MINUS));
//                    mutantGenerated[0]+=1;
//                });
//
//                return super.visit(n,args);
//            }
//
//        };
//        visitor.visit(cu,null);
//        return new Object[] {cu, mutantGenerated[0]};
//    }
//}