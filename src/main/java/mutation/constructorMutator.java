package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;
import java.util.List;

import static mutation.CodeModifierTest.writer;

public class constructorMutator implements MutantCreator {

    public constructorMutator() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(ObjectCreationExpr n, Void arg) {
                int lineNumber = n.getBegin().get().line;
                if (count1[0] == index){
                    n.replace(new NullLiteralExpr());
                    count1[0] +=1;
                    fileMutationCount[0] = true;
                    try {
                        writer.write("total executed mutants so far: "+count1[0]+"\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    count1[0] +=1;
                }


                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, fileMutationCount[0]};
    }
}
