package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class mathMutator implements MutantGeneratorClass{
    public CompilationUnit createMutant(CompilationUnit cu){
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.MULTIPLY){
                    n.setOperator(BinaryExpr.Operator.DIVIDE);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.DIVIDE);
                }
                else if(n.getOperator()==BinaryExpr.Operator.DIVIDE){
                    n.setOperator(BinaryExpr.Operator.MULTIPLY);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.MULTIPLY);
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
