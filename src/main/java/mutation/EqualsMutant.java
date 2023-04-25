package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;


public class EqualsMutant implements MutantGeneratorClass{
    public CompilationUnit createMutant(CompilationUnit cu){
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.EQUALS){
                    n.setOperator(BinaryExpr.Operator.NOT_EQUALS);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.NOT_EQUALS);
                }
                else if(n.getOperator()==BinaryExpr.Operator.NOT_EQUALS){
                    n.setOperator(BinaryExpr.Operator.EQUALS);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.EQUALS);
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
