package mutation;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
public class returnValuesMutator implements MutantGeneratorClass {
    public CompilationUnit createMutant(CompilationUnit cu){
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(BooleanLiteralExpr n, Void arg) {
                if (n.getValue()) {
                    return new BooleanLiteralExpr(false);
                } else {
                    return new BooleanLiteralExpr(true);
                }
            }
        };

        visitor.visit(cu, null);
        return cu;
    }
}