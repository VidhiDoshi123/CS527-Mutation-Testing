package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;
import java.util.List;

import static mutation.CodeModifierTest.writer;
public class falseReturns implements MutantCreator {
    public falseReturns() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(MethodDeclaration n, Void arg) {
                Type returnType = n.getType();
                Boolean primitive = returnType.isPrimitiveType() && returnType.asPrimitiveType().getType() == PrimitiveType.Primitive.BOOLEAN;
                Boolean boxed = returnType.isClassOrInterfaceType() && returnType.asClassOrInterfaceType().getNameAsString().equals("Boolean");
                if(primitive || boxed){
                    List<ReturnStmt> returnStmts = n.findAll(ReturnStmt.class);
                    for (ReturnStmt returnStmt : returnStmts) {
                        if (count1[0] == index){
                            count1[0] += 1;
                            fileMutationCount[0] = true;
                            try {
                                writer.write("total executed mutants so far: "+count1[0]+"\n");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            returnStmt.setExpression(new BooleanLiteralExpr(false));
                        }else{
                            count1[0]+=1;
                        }
                    }
                }
                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, fileMutationCount[0]};
    }

    @Override
    public Object[] generateAllMutants(CompilationUnit cu) {
        final int[] mutantGenerated = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(MethodDeclaration n, Void arg) {
                Type returnType = n.getType();
                Boolean primitive = returnType.isPrimitiveType() && returnType.asPrimitiveType().getType() == PrimitiveType.Primitive.BOOLEAN;
                Boolean boxed = returnType.isClassOrInterfaceType() && returnType.asClassOrInterfaceType().getNameAsString().equals("Boolean");
                if(primitive || boxed){
                    List<ReturnStmt> returnStmts = n.findAll(ReturnStmt.class);
                    for (ReturnStmt returnStmt : returnStmts) {
                            mutantGenerated[0] +=  1;
                            returnStmt.setExpression(new BooleanLiteralExpr(false));
                    }
                }
                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, mutantGenerated[0]};
    }
}
