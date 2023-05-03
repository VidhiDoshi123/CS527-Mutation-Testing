package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;


import java.io.IOException;
import static mutation.CodeModifierTest.writer;
public class negateConditionals implements MutantCreator {

    public negateConditionals() throws IOException {
    }
    public Object[] createMutant(CompilationUnit cu, int index) throws IOException {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};

        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.EQUALS){

                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.NOT_EQUALS);
                        System.out.println("From equals count is : "+ count1[0] + " index is: "+index);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;

                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.NOT_EQUALS);
                    } else {
                        count1[0] +=1;
                    }
                }
                else if(n.getOperator()==BinaryExpr.Operator.NOT_EQUALS){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.EQUALS);
                        System.out.println("From not equals count is : "+ count1[0] + " index is: "+index);
                        count1[0] +=1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.EQUALS);
                    } else {
                        count1[0] +=1;
                    }
                }
                return super.visit(n,args);
            }

        };
        visitor.visit(cu,null);
        return new Object[] {cu, fileMutationCount[0]};
    }

    public Object[] generateAllMutants(CompilationUnit cu) {
        final int[] mutantGenerated = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.EQUALS){
                        n.setOperator(BinaryExpr.Operator.NOT_EQUALS);
                    mutantGenerated[0] +=1;
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.NOT_EQUALS);
                }
                else if(n.getOperator()==BinaryExpr.Operator.NOT_EQUALS){
                        n.setOperator(BinaryExpr.Operator.EQUALS);
                    mutantGenerated[0] +=1;
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.EQUALS);
                }
                return super.visit(n,args);
            }

        };
        visitor.visit(cu,null);
        return new Object[] {cu, mutantGenerated[0]};
    }
}