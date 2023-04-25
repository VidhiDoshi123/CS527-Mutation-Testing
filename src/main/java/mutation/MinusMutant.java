package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class MinusMutant {
    public CompilationUnit createMutGreat(CompilationUnit cu){
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.GREATER){
                    n.setOperator(BinaryExpr.Operator.GREATER_EQUALS);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.GREATER_EQUALS);
                }
                else if(n.getOperator()==BinaryExpr.Operator.GREATER_EQUALS){
                    n.setOperator(BinaryExpr.Operator.GREATER);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.GREATER);
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
