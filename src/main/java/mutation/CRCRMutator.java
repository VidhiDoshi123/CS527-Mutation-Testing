package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;

import static mutation.CodeModifierTest.writer;

public class CRCRMutator implements MutantCreator {
    public CRCRMutator() throws IOException {
    }
    public Object[] createMutant(CompilationUnit cu, int index) throws IOException {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>(){

            @Override
            public Visitable visit(IntegerLiteralExpr n, Void args){

                    if (count1[0] == index){
                        System.out.println("THIS IS VALUE OF N before "+n);
                        int value = (int) n.asNumber();

                        Expression replacement = new DoubleLiteralExpr(value + 1.0);

                        // Replace the constant expression with the new expression
                        n.replace(replacement);
                        System.out.println("THIS IS VALUE OF N after"+n);
                        count1[0] += 1;
                        fileMutationCount[0] = true;
                        int lineNumber = n.getBegin().get().line;
                        try {
                            writer.write("total executed mutants so far: "+count1[0]+"\n");

                            writer.write("line number : " + lineNumber + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        count1[0] +=1;
                    }


                return super.visit(n,args);
            }
            @Override
            public Visitable visit(LongLiteralExpr n, Void args){

                if (count1[0] == index){
                    n.setValue("1");
                    count1[0] += 1;
                    fileMutationCount[0] = true;
                    int lineNumber = n.getBegin().get().line;
                    try {
                        writer.write("total executed mutants so far: "+count1[0]+"\n");

                        writer.write("line number : " + lineNumber + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    count1[0] +=1;
                }


                return super.visit(n,args);
            }
            @Override
            public Visitable visit(DoubleLiteralExpr n, Void args){

                if (count1[0] == index){
                    n.setValue("1.0");
                    count1[0] += 1;
                    fileMutationCount[0] = true;
                    int lineNumber = n.getBegin().get().line;
                    try {
                        writer.write("total executed mutants so far: "+count1[0]+"\n");

                        writer.write("line number : " + lineNumber + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    count1[0] +=1;
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
            public Visitable visit(IntegerLiteralExpr n, Void args){
                    n.setValue("1");
                    mutantGenerated[0] +=1;
                    return super.visit(n,args);
            }
            @Override
            public Visitable visit(LongLiteralExpr n, Void args){
                n.setValue("1L");
                mutantGenerated[0] +=1;
                return super.visit(n,args);
            }
            @Override
            public Visitable visit(DoubleLiteralExpr n, Void args){
                n.setValue("1.0");
                mutantGenerated[0] +=1;
                return super.visit(n,args);
            }
        };
        visitor.visit(cu,null);
        return new Object[] {cu, mutantGenerated[0]};
    }
}
