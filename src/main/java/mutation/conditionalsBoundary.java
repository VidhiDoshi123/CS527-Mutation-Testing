package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;

import static mutation.CodeModifierTest.writer;

public class conditionalsBoundary implements MutantCreator {
    public conditionalsBoundary() throws IOException {
    }
    public Object[] createMutant(CompilationUnit cu, int index) throws IOException {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.LESS){
                    if (count1[0] == index){
                        try {
                            writer.write("writing n: "+n+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                        n.setOperator(BinaryExpr.Operator.LESS_EQUALS);
                        System.out.println("From less count is : "+ count1[0] + " index is: "+index);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.LESS_EQUALS);
                    } else {
                        count1[0] +=1;
                    }
                }
                else if(n.getOperator()==BinaryExpr.Operator.LESS_EQUALS){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.LESS);
                        System.out.println("From Less equals count is : "+ count1[0] + " index is: "+index);
                        count1[0] +=1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.LESS);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator()==BinaryExpr.Operator.GREATER_EQUALS){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.GREATER);
                        System.out.println("From Less equals count is : "+ count1[0] + " index is: "+index);
                        count1[0] +=1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.GREATER);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator()==BinaryExpr.Operator.GREATER){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.GREATER_EQUALS);
                        System.out.println("From Less equals count is : "+ count1[0] + " index is: "+index);
                        count1[0] +=1;
                        fileMutationCount[0] = true;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.GREATER_EQUALS);
                    } else {
                        count1[0] +=1;
                    }
                }
                return super.visit(n,args);
            }
        };
        visitor.visit(cu,null);
        return new Object[] {cu, fileMutationCount[0]};
    }

    @Override
    public Object[] generateAllMutants(CompilationUnit cu) {
        final int[] mutantGenerated = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.LESS){
                    n.setOperator(BinaryExpr.Operator.LESS_EQUALS);
                    mutantGenerated[0] += 1;
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.LESS_EQUALS);
                }
                else if(n.getOperator()==BinaryExpr.Operator.LESS_EQUALS){
                    n.setOperator(BinaryExpr.Operator.LESS);
                    mutantGenerated[0] += 1;
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.LESS);
                }else if(n.getOperator()==BinaryExpr.Operator.GREATER_EQUALS){
                    n.setOperator(BinaryExpr.Operator.GREATER);
                    mutantGenerated[0] += 1;
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.GREATER);
                }else if(n.getOperator()==BinaryExpr.Operator.GREATER){
                    n.setOperator(BinaryExpr.Operator.GREATER_EQUALS);
                    mutantGenerated[0] += 1;
                    return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.GREATER_EQUALS);
                }
                return super.visit(n,args);
            }
        };
        visitor.visit(cu,null);
        return new Object[] {cu, mutantGenerated[0]};
    }
}