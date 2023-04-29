package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;

import static mutation.CodeModifierTest.writer;

public class mathMutator implements MutantCreator {
    public mathMutator() throws IOException {
    }
    public Object[] createMutant(CompilationUnit cu, int index) throws IOException {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.MULTIPLY){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.DIVIDE);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.DIVIDE);
                    } else {
                        count1[0] +=1;
                    }
                }
                else if(n.getOperator()==BinaryExpr.Operator.DIVIDE){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.MULTIPLY);
                        System.out.println("From DIVIDE count is : "+ count1[0] + " index is: "+index);
                        count1[0] +=1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.MULTIPLY);
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
}
