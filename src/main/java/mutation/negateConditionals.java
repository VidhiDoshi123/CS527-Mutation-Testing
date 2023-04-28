package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;


import java.io.FileWriter;
import java.io.IOException;

import static mutation.CodeModifierTest.writer;



public class negateConditionals implements MutantCreator {
    public negateConditionals() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) throws IOException {
        final int[] maxi = {0};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.EQUALS){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.NOT_EQUALS);
                        System.out.println("From equals count is : "+ count1[0] + " index is: "+index);
                        count1[0] += 1;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        maxi[0] = Math.max(maxi[0],count1[0]);
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
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        maxi[0] = Math.max(maxi[0],count1[0]);

                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.EQUALS);
                    } else {
                        count1[0] +=1;
                    }
                }
                return super.visit(n,args);
            }
        };
        visitor.visit(cu,null);
        writer.write("maxi value is: "+maxi[0]+"\n");
        return new Object[] {cu, maxi[0]};
    }
}
