package mutation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SwitchExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.util.List;

import static com.github.javaparser.StaticJavaParser.parseStatement;
import static mutation.CodeModifierTest.writer;
public class experimentalSwitch implements MutantCreator {
    public experimentalSwitch() throws IOException {
    }

    public Object[] createMutant(CompilationUnit cu, int index) {
        final boolean[] fileMutationCount = {false};
        final int[] count1 = {0};
        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(SwitchStmt n, Void arg) {
                NodeList<SwitchEntry> entries = n.getEntries();

                String defaultLabel1 = null;
                String nonDefaultLabel1 = null;
                for (SwitchEntry entry : entries) {
                    if (!entry.getLabels().isEmpty())  {
                        nonDefaultLabel1 = entry.getLabels().get(0).toString();
                        break;
                    }
                }

                for (SwitchEntry entry : entries) {
                    if (entry.getLabels().isEmpty()) {
                        defaultLabel1 = entry.getStatements().get(0).toString();
                        System.out.println("defaultLabel: "+defaultLabel1);
                        break;
                    }
                }
                //replace default label with non default label
                //find the remaining non default variables and replace it with default one.
                for (SwitchEntry entry : entries) {
                    if(count1[0] == index) {
                        NodeList<Expression> expressions = new NodeList<>();
                        //replacing non default labels with default label
                        if (!entry.getLabels().isEmpty()) {
                            expressions.add(new StringLiteralExpr(defaultLabel1));
                            entry.setLabels(expressions);
                            fileMutationCount[0] = true;
                        } else if (entry.getLabels().isEmpty()) {
                            //replacing default label with non default label
                            expressions.add(new StringLiteralExpr(nonDefaultLabel1));
                            entry.setLabels(expressions);
                            fileMutationCount[0] = true;
                        }
                    }else{
                        count1[0]+=1;
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
            public Visitable visit(SwitchStmt n, Void arg) {
                NodeList<SwitchEntry> entries = n.getEntries();

                String defaultLabel1 = null;
                String nonDefaultLabel1 = null;
                for (SwitchEntry entry : entries) {
                    if (!entry.getLabels().isEmpty())  {
                        nonDefaultLabel1 = entry.getLabels().get(0).toString();
                        break;
                    }
                }

                for (SwitchEntry entry : entries) {
                    if (entry.getLabels().isEmpty()) {
                        defaultLabel1 = entry.getStatements().get(0).toString();
                        break;
                    }
                }

                for (SwitchEntry entry : entries) {
                    NodeList<Expression> expressions = new NodeList<>();
                    //replacing non default labels with default label
                    if (!entry.getLabels().isEmpty()) {
                        expressions.add(new StringLiteralExpr(defaultLabel1));
                        entry.setLabels(expressions);
                        mutantGenerated[0] += 1;
                    }
                    else if (entry.getLabels().isEmpty()) {
                        //replacing default label with non default label
                        expressions.add(new StringLiteralExpr(nonDefaultLabel1));
                        entry.setLabels(expressions);
                        mutantGenerated[0] += 1;
                    }
                }
                return super.visit(n, arg);
            }
        };
        visitor.visit(cu, null);
        return new Object[]{cu, mutantGenerated[0]};
    }
}



