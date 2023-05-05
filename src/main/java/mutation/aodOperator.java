package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;

import static mutation.CodeModifierTest.writer;

public class aodOperator implements MutantCreator {
    public aodOperator() throws IOException {
    }
    public Object[] createMutant(CompilationUnit cu, int index) throws IOException {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.PLUS ||
                        n.getOperator() == BinaryExpr.Operator.MINUS ||
                        n.getOperator() == BinaryExpr.Operator.MULTIPLY ||
                        n.getOperator() == BinaryExpr.Operator.DIVIDE){
                    if (count1[0] == index){
                        Expression left = n.getLeft();
                        n.remove();

                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");

//                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return left;
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

    @Override
    public Object[] generateAllMutants(CompilationUnit cu) {
        final int[] mutantGenerated = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.PLUS ||
                        n.getOperator() == BinaryExpr.Operator.MINUS ||
                        n.getOperator() == BinaryExpr.Operator.MULTIPLY ||
                        n.getOperator() == BinaryExpr.Operator.DIVIDE){
                    Expression left = n.getLeft();
                    n.remove();


                    mutantGenerated[0] +=1;
                    System.out.println("line number is (* -> /) "+n.getBegin().get().line);
                    return left;
                }

                return super.visit(n,args);
            }
        };
        visitor.visit(cu,null);
        return new Object[] {cu, mutantGenerated[0]};
    }
}
