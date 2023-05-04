package mutation;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;
import static mutation.CodeModifierTest.writer;


public class returnValuesMutator implements MutantCreator {
    public returnValuesMutator() throws IOException {
    }
    public Object[] createMutant(CompilationUnit cu, int index){
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(BooleanLiteralExpr n, Void args) {
                if (n.getParentNode().isPresent() && n.getParentNode().get() instanceof ReturnStmt) {
                    if (n.getValue()) {
                        if (count1[0] == index) {
//                            int lineNumber = n.getBegin().get().line;
                            count1[0] += 1;
                            fileMutationCount[0] = true;
                            try {
                                writer.write("total executed mutants so far: " + count1[0] + "\n");
//                                writer.write("line number : " + lineNumber + "\n");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            n.setValue(false);
                            return n;
                        } else {
                            count1[0] += 1;
                        }
                    } else {
                        if (count1[0] == index) {
                            int lineNumber = n.getBegin().get().line;
                            count1[0] += 1;
                            fileMutationCount[0] = true;
                            try {
                                writer.write("total executed mutants so far: " + count1[0] + "\n");
//                                writer.write("line number : " + lineNumber + "\n");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            n.setValue(true);
                            return n;
                        } else {
                            count1[0] += 1;
                        }
                    }
                }
                return super.visit(n,args);
            }
        };
        visitor.visit(cu, null);
        return new Object[] {cu, fileMutationCount[0]};
    }

    @Override
    public Object[] generateAllMutants(CompilationUnit cu) {
        final int[] mutantGenerated = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(BooleanLiteralExpr n, Void args) {
                if (n.getParentNode().isPresent() && n.getParentNode().get() instanceof ReturnStmt) {
                    if (n.getValue()) {
                            mutantGenerated[0] +=1;
                            n.setValue(false);
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return n;
                    } else {
                            mutantGenerated[0] +=1;
                            n.setValue(true);
                            int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return n;
                    }
                }
                return super.visit(n,args);
            }
        };
        visitor.visit(cu, null);
        return new Object[] {cu, mutantGenerated[0]};
    }
}