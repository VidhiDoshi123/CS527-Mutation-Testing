package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;


import java.io.IOException;
import static mutation.CodeModifierTest.writer;
public class bitwiseMutator implements MutantCreator {

    public bitwiseMutator() throws IOException {
    }
    public Object[] createMutant(CompilationUnit cu, int index) throws IOException {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};

        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.BINARY_AND){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.BINARY_OR);
                        fileMutationCount[0] = true;
                        count1[0]+=1;
                        int lineNumber = n.getBegin().get().line;

                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

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
                if(n.getOperator() == BinaryExpr.Operator.BINARY_AND){
                    n.setOperator(BinaryExpr.Operator.BINARY_OR);
                    mutantGenerated[0] +=1;
                }

                return super.visit(n,args);
            }

        };
        visitor.visit(cu,null);
        return new Object[] {cu, mutantGenerated[0]};
    }
}