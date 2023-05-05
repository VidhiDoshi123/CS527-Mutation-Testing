package mutation;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;
import static mutation.CodeModifierTest.writer;
public class inlineConstantMutator implements MutantCreator {
    public inlineConstantMutator() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(VariableDeclarationExpr n, Void arg) {
                int lineNumber = n.getBegin().get().line;

                n.getVariables().forEach(var -> {
                    if (var.getInitializer().isPresent()) {
                        var.getInitializer().get().findAll(BooleanLiteralExpr.class).forEach(b -> {
                            if (count1[0] == index){
                                if (b.getValue()) {
                                    b.setValue(false);
                                } else {
                                    b.setValue(true);
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
                        });

                        Expression init = var.getInitializer().get();
                        if (init.isIntegerLiteralExpr()) {
                            int value = init.asIntegerLiteralExpr().asNumber().intValue();
                            if (count1[0] == index){
                                if (value == 1) {
                                    var.setInitializer("0");
                                } else if (value == -1) {
                                    var.setInitializer("1");
                                } else if (value == 5) {
                                    var.setInitializer("-1");
                                } else {
                                    var.setInitializer(Integer.toString(value + 1));
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
                        }
                        if (init.isLongLiteralExpr()) {
                            long value = init.asLongLiteralExpr().asNumber().longValue();
                            if (count1[0] == index){
                                if (value == 1L) {
                                    var.setInitializer("0L");
                                } else  {
                                    var.setInitializer(Long.toString(value + 1L) + "L");
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
                        }
                        if (init.isDoubleLiteralExpr()) {
                            double value = init.asDoubleLiteralExpr().asDouble();                            if (count1[0] == index){
                                if (value == 1.0) {
                                    var.setInitializer(Double.toString(0.0));
                                } else  {
                                    var.setInitializer(Double.toString(1.0));
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
                        }

                    }
                });

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
            public Visitable visit(VariableDeclarationExpr n, Void arg) {
                int lineNumber = n.getBegin().get().line;

                n.getVariables().forEach(var -> {
                    if (var.getInitializer().isPresent()) {
                        var.getInitializer().get().findAll(BooleanLiteralExpr.class).forEach(b -> {

                            if (b.getValue()) {
                                b.setValue(false);
                            } else {
                                b.setValue(true);
                            }
                            mutantGenerated[0] += 1;
                        });

                        Expression init = var.getInitializer().get();
                        if (init.isIntegerLiteralExpr()) {
                            int value = init.asIntegerLiteralExpr().asNumber().intValue();

                            if (value == 1) {
                                var.setInitializer("0");
                            } else if (value == -1) {
                                var.setInitializer("1");
                            } else if (value == 5) {
                                var.setInitializer("-1");
                            } else {
                                var.setInitializer(Integer.toString(value + 1));
                            }

                            mutantGenerated[0] += 1;


                        }
                        if (init.isLongLiteralExpr()) {
                            long value = init.asLongLiteralExpr().asNumber().longValue();

                            if (value == 1L) {
                                var.setInitializer("0L");
                            } else {
                                var.setInitializer(Long.toString(value + 1L) + "L");
                            }

                            mutantGenerated[0] += 1;


                        }
                        if (init.isDoubleLiteralExpr()) {
                            double value = init.asDoubleLiteralExpr().asDouble();

                                if (value == 1.0) {
                                    var.setInitializer(Double.toString(0.0));
                                } else {
                                    var.setInitializer(Double.toString(1.0));
                                }
                                mutantGenerated[0] += 1;



                        }
                    }
                });

                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, mutantGenerated[0]};
    }
}
