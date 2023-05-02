package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;

import static mutation.CodeModifierTest.writer;

public class mathMutator implements MutantCreator {
    public mathMutator() throws IOException {
    }
    public Object[] createMutant(CompilationUnit cu, int index) throws IOException {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(BinaryExpr n, Void args){
                if(n.getOperator() == BinaryExpr.Operator.MULTIPLY){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.DIVIDE);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write("* became /: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.DIVIDE);
                    } else {
                        count1[0] +=1;
                    }
                }
                else if(n.getOperator()==BinaryExpr.Operator.DIVIDE){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.MULTIPLY);
                        System.out.println("From DIVIDE count is : "+ count1[0] + " index is: "+index);
                        count1[0] +=1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write("/ became *: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.MULTIPLY);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator() == BinaryExpr.Operator.PLUS){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.MINUS);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write("+ became -: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.MINUS);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator() == BinaryExpr.Operator.MINUS){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.PLUS);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write("- became +: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.PLUS);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator() == BinaryExpr.Operator.LEFT_SHIFT){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.SIGNED_RIGHT_SHIFT);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write("<< became >>: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.SIGNED_RIGHT_SHIFT);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator() == BinaryExpr.Operator.SIGNED_RIGHT_SHIFT){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.LEFT_SHIFT);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write(">> became <<: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.LEFT_SHIFT);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator() == BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.LEFT_SHIFT);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write(">>> became <<: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.LEFT_SHIFT);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator() == BinaryExpr.Operator.REMAINDER){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.MULTIPLY);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write(">>> became <<: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.MULTIPLY);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator() == BinaryExpr.Operator.BINARY_AND){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.BINARY_OR);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write(">>> became <<: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.BINARY_OR);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator() == BinaryExpr.Operator.BINARY_OR){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.BINARY_AND);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write(">>> became <<: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.BINARY_AND);
                    } else {
                        count1[0] +=1;
                    }
                }else if(n.getOperator() == BinaryExpr.Operator.XOR){
                    if (count1[0] == index){
                        n.setOperator(BinaryExpr.Operator.BINARY_AND);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");
                            writer.write(">>> became <<: "+count1[0]+"\n");
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return new BinaryExpr(n.getLeft(),n.getRight(),BinaryExpr.Operator.BINARY_AND);
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
}
