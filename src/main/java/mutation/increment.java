package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;

import static mutation.CodeModifierTest.writer;

//concerns with this file includes the following:
//1) in case of attributes.java index = 0, i++ becomes --i.
//2) in case of httpconnection.java index = 66, ++i remains ++i. confirm with prof.
//3) mutation is correctly happening as seen in the print statements
//4) once it is returned, the changes are not what is expected.
public class increment implements MutantCreator {
    public increment() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(UnaryExpr n, Void arg) {
                if (n.getOperator() == UnaryExpr.Operator.PREFIX_INCREMENT) {
                    if (count1[0] == index) {
                        n.setOperator(UnaryExpr.Operator.PREFIX_DECREMENT);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new UnaryExpr(n.getExpression(),UnaryExpr.Operator.PREFIX_DECREMENT);
                    }else{
                        count1[0] +=1;
                    }
                } else if (n.getOperator() == UnaryExpr.Operator.PREFIX_DECREMENT) {
                    if (count1[0] == index) {
                        n.setOperator(UnaryExpr.Operator.PREFIX_INCREMENT);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new UnaryExpr(n.getExpression(),UnaryExpr.Operator.PREFIX_INCREMENT);

                    }else{
                        count1[0] +=1;
                    }
                } else if (n.getOperator() == UnaryExpr.Operator.POSTFIX_INCREMENT) {
                    if (count1[0] == index) {
                        n.setOperator(UnaryExpr.Operator.POSTFIX_DECREMENT);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new UnaryExpr(n.getExpression(),UnaryExpr.Operator.POSTFIX_DECREMENT);

                    }else{
                        count1[0] +=1;
                    }
                } else if (n.getOperator() == UnaryExpr.Operator.POSTFIX_DECREMENT) {
                    if (count1[0] == index) {
                        n.setOperator(UnaryExpr.Operator.POSTFIX_INCREMENT);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new UnaryExpr(n.getExpression(),UnaryExpr.Operator.POSTFIX_INCREMENT);
                    }else{
                        count1[0] +=1;
                    }
                }
                return super.visit(n,arg);
            }
        };

        visitor.visit(cu,null);
        return new Object[] {cu, fileMutationCount[0]};
    }
}
