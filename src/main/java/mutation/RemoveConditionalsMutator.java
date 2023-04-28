package mutation;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class RemoveConditionalsMutator implements MutantCreator {
    public Object[] createMutant(CompilationUnit cu, int i) {
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(IfStmt n, Void arg) {
                n.setCondition(new BooleanLiteralExpr(true));
                return super.visit(n, arg);
            }
        };

        visitor.visit(cu, null);
        return new CompilationUnit[]{cu};
    }
}
