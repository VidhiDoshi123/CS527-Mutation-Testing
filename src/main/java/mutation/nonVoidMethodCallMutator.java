package mutation;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;

import static com.github.javaparser.StaticJavaParser.parseExpression;
import static mutation.CodeModifierTest.writer;
public class nonVoidMethodCallMutator implements MutantCreator {
    public nonVoidMethodCallMutator() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(MethodDeclaration n, Void arg) {
                Type returnType = n.getType();

                int lineNumber = n.getBegin().get().line;
                if (count1[0] == index) {
                    System.out.println("--THIS IS RETURN TYPE"+returnType);
                    if (returnType.isPrimitiveType()) {
                        PrimitiveType.Primitive primitiveType = ((PrimitiveType) returnType).getType();
                        System.out.println("--THIS IS PRIMITIVE RETURN TYPE"+primitiveType);
                        switch (primitiveType) {
                            case BOOLEAN:
                                for (ReturnStmt returnStmt : n.findAll(ReturnStmt.class)) {
                                    returnStmt.setExpression(new BooleanLiteralExpr(false));
                                }

                            case BYTE:
                            case SHORT:
                            case INT:
                                for (ReturnStmt returnStmt : n.findAll(ReturnStmt.class)) {
                                    returnStmt.setExpression(new IntegerLiteralExpr("0"));
                                }


                            case LONG:
                                for (ReturnStmt returnStmt : n.findAll(ReturnStmt.class)) {
                                    returnStmt.setExpression(new IntegerLiteralExpr("0"));
                                }
                            case FLOAT:
                                for (ReturnStmt returnStmt : n.findAll(ReturnStmt.class)) {
                                    returnStmt.setExpression(new DoubleLiteralExpr("0.0f"));
                                }

                            case DOUBLE:
                                for (ReturnStmt returnStmt : n.findAll(ReturnStmt.class)) {
                                    returnStmt.setExpression(new DoubleLiteralExpr("0.0"));
                                }

                            case CHAR:
                                for (ReturnStmt returnStmt : n.findAll(ReturnStmt.class)) {
                                    returnStmt.setExpression(new CharLiteralExpr('\u0000'));
                                }


                        }
                    }

                    count1[0] += 1;
                    fileMutationCount[0] = true;
                    try {
                        writer.write("total executed mutants so far: "+count1[0]+"\n");
                        writer.write("line number : " + lineNumber + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    count1[0]+=1;
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
                int lineNumber = n.getBegin().get().line;

                if (returnType.isVoidType()) {
                    return null;
                } else if (returnType.isPrimitiveType()) {
                    PrimitiveType.Primitive primitiveType = ((PrimitiveType) returnType).getType();
                    switch (primitiveType) {
                        case BOOLEAN:
                            n.replace(new BooleanLiteralExpr(false));
                        case BYTE:
                        case SHORT:
                        case INT:
                        case LONG:
                            n.replace(new IntegerLiteralExpr("0"));
                        case FLOAT:
                            n.replace(new DoubleLiteralExpr("0.0f"));
                        case DOUBLE:
                            n.replace(new DoubleLiteralExpr("0.0"));
                        case CHAR:
                            n.replace(new CharLiteralExpr('\u0000'));
                    }
                } else {
                    return parseExpression("null");
                }

                mutantGenerated[0] += 1;

                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, mutantGenerated[0]};
    }
}

