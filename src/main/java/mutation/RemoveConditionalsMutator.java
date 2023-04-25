package mutation;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class RemoveConditionalsMutator implements MutantGeneratorClass {
    public CompilationUnit createMutant(CompilationUnit cu) {
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(IfStmt n, Void arg) {
                //return new IfStmt(new BooleanLiteralExpr(true), n.getThenStmt(), null);
                n.setCondition(new BooleanLiteralExpr(true));
                return super.visit(n, arg);
            }
        };

        visitor.visit(cu, null);
        return cu;
    }
}
