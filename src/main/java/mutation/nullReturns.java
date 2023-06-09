package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static mutation.CodeModifierTest.writer;
public class nullReturns implements MutantCreator {
    private static final Map<String, String> EMPTY_VALUES = new HashMap<>() {{
        put("Set", "java.util.Collections.emptySet()");
        put("int", String.valueOf(0));
        put("String", "\"\"");
        put("List", "java.util.Collections.emptyList()");
        put("char", String.valueOf(0));
        put("Optional", "Optional.empty()");
        put("Collection", "Collections.emptyList()");
        put("Short", String.valueOf(0));
        put("Long", String.valueOf(0));
        put("Float", String.valueOf(0));
        put("Double", String.valueOf(0));
    }};

    public nullReturns() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(MethodDeclaration n, Void arg) {
                Type returnType = n.getType();
                if(returnType != null &&!(returnType instanceof VoidType)){
                    String dataType = returnType.toString();
                    int position = dataType.indexOf('<');
                    if (position != -1) {
                        dataType = dataType.substring(0, position).trim().toString();
                    }
                    if (!EMPTY_VALUES.containsKey(dataType)) {
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
                                returnStmt.setExpression(new NullLiteralExpr(null));
                            }else{
                                count1[0]+=1;
                            }
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
                if(returnType != null &&!(returnType instanceof VoidType)){
                    String dataType = returnType.toString();
                    int position = dataType.indexOf('<');
                    if (position != -1) {
                        dataType = dataType.substring(0, position).trim().toString();
                    }
                    if (!EMPTY_VALUES.containsKey(dataType)) {
                        List<ReturnStmt> returnStmts = n.findAll(ReturnStmt.class);
                        for (ReturnStmt returnStmt : returnStmts) {
                                mutantGenerated[0] += 1;
                                returnStmt.setExpression(new NullLiteralExpr(null));
                        }
                    }
                }
                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, mutantGenerated[0]};
    }
}
