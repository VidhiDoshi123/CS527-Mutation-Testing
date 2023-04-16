package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
@SuppressWarnings("deprecation")
public class CodeModifier {
    int totalMutations = 0;
    public CompilationUnit createMutEquals(CompilationUnit cu){
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.EQUALS){
                    totalMutations +=1;
                    n.setOperator(BinaryExpr.Operator.NOT_EQUALS);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.NOT_EQUALS);
                }
                else if(n.getOperator()==BinaryExpr.Operator.NOT_EQUALS){
                    totalMutations += 1;
                    n.setOperator(BinaryExpr.Operator.EQUALS);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.EQUALS);
                }
                else{
                    return super.visit(n,args);
                }
            }
        };

        visitor.visit(cu,null);
//        System.out.println("#mutations "+totalMutations);
        return cu;
    }

    public CompilationUnit createMutGreat(CompilationUnit cu){
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.GREATER){
                    totalMutations +=1;
                    n.setOperator(BinaryExpr.Operator.GREATER_EQUALS);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.GREATER_EQUALS);
                }
                else if(n.getOperator()==BinaryExpr.Operator.GREATER_EQUALS){
                    totalMutations += 1;
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

    public CompilationUnit createMutMultiply(CompilationUnit cu){
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.MULTIPLY){
                    totalMutations +=1;
                    n.setOperator(BinaryExpr.Operator.DIVIDE);
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.DIVIDE);
                }
                else if(n.getOperator()==BinaryExpr.Operator.DIVIDE){
                    totalMutations += 1;
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
