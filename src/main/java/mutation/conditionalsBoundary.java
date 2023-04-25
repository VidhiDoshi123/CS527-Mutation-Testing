package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class conditionalsBoundary implements MutantGeneratorClass{
    public CompilationUnit createMutant(CompilationUnit cu){
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.LESS){
                    n.setOperator(BinaryExpr.Operator.LESS_EQUALS);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.LESS_EQUALS);
                }
                else if(n.getOperator()==BinaryExpr.Operator.LESS_EQUALS){
                    n.setOperator(BinaryExpr.Operator.LESS);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.LESS);
                }
                else{
                    return super.visit(n,args);
                }
            }
        };

        visitor.visit(cu,null);
        return cu;
    }
}
