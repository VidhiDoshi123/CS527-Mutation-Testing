package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

//this code does not work
public class increment implements MutantCreator {

    public Object[] createMutant(CompilationUnit cu, int i) {
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(UnaryExpr n, Void arg) {
                if (n.getOperator() == UnaryExpr.Operator.POSTFIX_INCREMENT) {
                    return new UnaryExpr(n.getExpression(), UnaryExpr.Operator.POSTFIX_DECREMENT);
                } else if (n.getOperator() == UnaryExpr.Operator.POSTFIX_DECREMENT) {
                    return new UnaryExpr(n.getExpression(), UnaryExpr.Operator.POSTFIX_INCREMENT);
                }
                else if (n.getOperator() == UnaryExpr.Operator.PREFIX_INCREMENT) {
                    return new UnaryExpr(n.getExpression(), UnaryExpr.Operator.PREFIX_DECREMENT);
                } else if (n.getOperator() == UnaryExpr.Operator.PREFIX_DECREMENT) {
                    return new UnaryExpr(n.getExpression(), UnaryExpr.Operator.PREFIX_INCREMENT);
                }
                else {
                    return super.visit(n, arg);
                }
            }
        };

        visitor.visit(cu,null);
        return new CompilationUnit[]{cu};
    }
}
