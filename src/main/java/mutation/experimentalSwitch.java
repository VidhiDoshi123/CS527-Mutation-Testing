//package mutation;
//
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.NodeList;
//import com.github.javaparser.ast.body.MethodDeclaration;
//import com.github.javaparser.ast.expr.NullLiteralExpr;
//import com.github.javaparser.ast.stmt.ReturnStmt;
//import com.github.javaparser.ast.stmt.SwitchEntry;
//import com.github.javaparser.ast.type.Type;
//import com.github.javaparser.ast.type.VoidType;
//import com.github.javaparser.ast.visitor.ModifierVisitor;
//import com.github.javaparser.ast.visitor.Visitable;
//import com.github.javaparser.ast.stmt.SwitchStmt;
//
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.github.javaparser.StaticJavaParser.parseStatement;
//import static mutation.CodeModifierTest.writer;
//public class experimentalSwitch implements MutantCreator {
//    public experimentalSwitch() throws IOException {
//    }
//
//    public Object[] createMutant(CompilationUnit cu, int index) {
//        final boolean[] fileMutationCount = {false};
//        final int[] count1 = {0};
//        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
//            @Override
//            public Visitable visit(SwitchStmt n, Void arg) {
//                    NodeList<SwitchEntry> entries = n.getEntries();
//                    String defaultLabel = null;
//                    String nonDefaultLabel = null;
//
//                    // Find the first non-default label
//                    for (SwitchEntry entry : entries) {
//                        if (entry.getLabels().isEmpty()) {
//                            System.out.println("before 1");
//                            defaultLabel = entry.toString();
//                            System.out.println("problem 1");
//                            System.out.println("defaultLabel: "+defaultLabel);
//                        } else {
//                            nonDefaultLabel = entry.getLabels().get(0).toString();
//                            System.out.println("nonDefaultLabel: "+nonDefaultLabel);
//                            break;
//                        }
//                    }
//                System.out.println("before 2");
//                String switchStmtString = n.toString();
//                System.out.println("problem 2");
//                String mutatedSwitchStmtString = switchStmtString.replace(defaultLabel, nonDefaultLabel);
//                return parseStatement(mutatedSwitchStmtString);
//            }
//        };
//        visitor.visit(cu, null);
//        return new Object[]{cu, fileMutationCount[0]};
//    }
//}
