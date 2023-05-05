package mutation;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;
import static mutation.CodeModifierTest.writer;
public class removeIncrements implements MutantCreator {
    public removeIncrements() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(UnaryExpr n, Void arg) {

                if (n.getOperator() == UnaryExpr.Operator.POSTFIX_INCREMENT || n.getOperator() == UnaryExpr.Operator.PREFIX_INCREMENT) {
                    int lineNumber = n.getBegin().get().line;
                    if (count1[0] == index) {
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: " + count1[0] + "\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return  n.getExpression();
                    } else {
                        count1[0] += 1;
                    }
                }
                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, fileMutationCount[0]};
    }

    @Override
    public Object[] generateAllMutants(CompilationUnit cu) {
        final int[] mutantGenerated = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(UnaryExpr n, Void arg) {
                if (n.getOperator() == UnaryExpr.Operator.PREFIX_INCREMENT) {
                    mutantGenerated[0] += 1;
                    return n.getExpression();
                }
                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, mutantGenerated[0]};
    }
}
