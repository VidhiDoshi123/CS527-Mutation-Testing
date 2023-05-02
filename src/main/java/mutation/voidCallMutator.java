package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static mutation.CodeModifierTest.writer;

public class voidCallMutator implements MutantCreator{
    public voidCallMutator() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        Set<String> mySet = new HashSet<>();

        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(MethodDeclaration n, Void arg) {
                List<MethodCallExpr> methodCalls = n.findAll(MethodCallExpr.class);
                if (!methodCalls.isEmpty()) {
                    //find all method declarations with void type
                    for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
                        if(method.getType().isVoidType()) mySet.add(method.getNameAsString());
                    }

                    //iterate over the method calls list now
                    for(MethodCallExpr method:methodCalls){
                        String name = method.getNameAsString();
                        if(mySet.contains(name)){
                            if(count1[0]==index) {
                                count1[0]+=1;
                                fileMutationCount[0] = true;
                                for (Statement statement : n.getBody().get().getStatements()) {
                                    if (statement.toString().contains(name)) {
                                        statement.remove();
                                        break;
                                    }
                                }
                                try {
                                    writer.write("total executed mutants so far: " + count1[0] + "\n");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }else{
                                count1[0]+=1;
                            }
                        }
                    }
                }
                return super.visit(n, arg);
            }
        };
        visitor.visit(cu,null);
        return new Object[]{cu, fileMutationCount[0]};
    }
}
