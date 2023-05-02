//package mutation;
//
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.NodeList;
//import com.github.javaparser.ast.body.MethodDeclaration;
//import com.github.javaparser.ast.expr.BooleanLiteralExpr;
//import com.github.javaparser.ast.expr.SwitchExpr;
//import com.github.javaparser.ast.stmt.ReturnStmt;
//import com.github.javaparser.ast.stmt.SwitchEntry;
//import com.github.javaparser.ast.stmt.SwitchStmt;
//import com.github.javaparser.ast.type.PrimitiveType;
//import com.github.javaparser.ast.type.Type;
//import com.github.javaparser.ast.visitor.ModifierVisitor;
//import com.github.javaparser.ast.visitor.Visitable;
//
//import java.io.IOException;
//import java.lang.invoke.SwitchPoint;
//import java.util.List;
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
//                NodeList<SwitchEntry> entries = n.getEntries();
//
//                String defaultLabel1 = null;
//                String nonDefaultLabel1 = null;
//                for (SwitchEntry entry : entries) {
//                    if (!entry.getLabels().isEmpty())  {
//                        nonDefaultLabel1 = entry.getLabels().get(0).toString();
//                        System.out.println("nonDefaultLabel: "+nonDefaultLabel1);
//                        break;
//                    }
//                }
//
//                for (SwitchEntry entry : entries) {
//                    if (entry.getLabels().isEmpty()) {
//                        defaultLabel1 = entry.toString();
//                        System.out.println("defaultLabel: "+defaultLabel1);
//                        break;
//                    }
//                }
//
//
//
//                fileMutationCount[0] = true;
//                int lineNumber = n.getBegin().get().line;
//                try {
//                    writer.write("total executed mutants so far: "+count1[0]+"\n");
//                    writer.write("line number : " + lineNumber + "\n");
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//                return super.visit(n, arg);
//            }
//        };
//        visitor.visit(cu, null);
//        return new Object[]{cu, fileMutationCount[0]};
//    }
//}
//
//
//
