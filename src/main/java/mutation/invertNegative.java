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
public class invertNegative implements MutantCreator {
    public invertNegative() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(UnaryExpr n, Void arg) {
                if ((n.getOperator() == UnaryExpr.Operator.MINUS)) {
                    if (count1[0] == index) {
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: " + count1[0] + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new UnaryExpr(n.getExpression(), UnaryExpr.Operator.PLUS);
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
                if ((n.getOperator() == UnaryExpr.Operator.MINUS)) {
                    mutantGenerated[0] += 1;
                    return new UnaryExpr(n.getExpression(), UnaryExpr.Operator.PLUS);
                }
                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, mutantGenerated[0]};
    }
}
