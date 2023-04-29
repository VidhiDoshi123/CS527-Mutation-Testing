//package mutation;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.expr.BinaryExpr;
//import com.github.javaparser.ast.expr.BooleanLiteralExpr;
//import com.github.javaparser.ast.visitor.ModifierVisitor;
//import com.github.javaparser.ast.visitor.Visitable;
//
//import java.io.IOException;
//import static mutation.CodeModifierTest.writer;
//
//
//public class returnValuesMutator implements MutantCreator {
//    public returnValuesMutator() throws IOException {
//    }
//    public Object[] createMutant(CompilationUnit cu, int index){
//        final boolean[] fileMutationCount = {false};
//        final int[] count1 = {0};
//        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
//            @Override
//            public Visitable visit(BooleanLiteralExpr n, Void args) {
//                if (n.getValue()) {
//                    if (count1[0] == index){
//                        count1[0] += 1;
//                        fileMutationCount[0] = true;
//                        try {
//                            writer.write("total executed mutants so far: "+count1[0]+"\n");
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        n.setValue(false);
////                        return new BooleanLiteralExpr(false);
//                    }
//                    else {
//                        count1[0] +=1;
//                    }
//                } else if (!n.getValue()){
//                    if (count1[0] == index){
//                        count1[0] +=1;
//                        fileMutationCount[0] = true;
//                        try {
//                            writer.write("total executed mutants so far: "+count1[0]+"\n");
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        n.setValue(true);
////                        return new BooleanLiteralExpr(true);
//                    } else {
//                        count1[0] +=1;
//                    }
//                }
//                return super.visit(n,args);
//            }
//        };
//        visitor.visit(cu, null);
//        return new Object[] {cu, fileMutationCount[0]};
//    }
//}