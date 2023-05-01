//package mutation;
//
//import com.github.javaparser.ParserConfiguration;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.Node;
//import com.github.javaparser.ast.body.CallableDeclaration;
//import com.github.javaparser.ast.body.ConstructorDeclaration;
//import com.github.javaparser.ast.body.MethodDeclaration;
//import com.github.javaparser.ast.expr.MethodCallExpr;
//import com.github.javaparser.ast.type.Type;
//import com.github.javaparser.ast.visitor.ModifierVisitor;
//import com.github.javaparser.ast.visitor.Visitable;
//import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static mutation.CodeModifierTest.writer;
//
//
//public class VoidMethodCallMutator implements MutantCreator {
//
//    public VoidMethodCallMutator() throws IOException {
//    }
//
//    public Object[] createMutant(CompilationUnit cu, int index) {
//        final boolean[] fileMutationCount = {false};
//        final int[] count1 = {0};
//
//        ModifierVisitor<Void> visitor = new ModifierVisitor<Void>() {
//            Map<String, String> methodMap = new HashMap<>();
//
//            @Override
//            public Visitable visit(MethodDeclaration n, Void arg) {
//                System.out.println("n: "+n);
//                String methodName = n.getNameAsString();
//                String returnType = n.getTypeAsString();
//
//                // Put the method name and return type into the HashMap
//                methodMap.put(methodName, returnType);
//                System.out.println("methodMap: : "+methodMap);
//                // Call the super method to continue visiting the AST
//                super.visit(n, arg);
////                MethodDeclaration method = cu.getClassByName("Attributes").get().getMethodsByName(n.getName().asString()).get(0);
//
//// Assuming you have a ReturnStmt object called returnStmt
////                Type returnType = n.getType();
////                Node parentNode = n.getParentNode().orElse(null);
////                while (parentNode != null && !(parentNode instanceof MethodDeclaration) && !(parentNode instanceof ConstructorDeclaration)) {
////                    parentNode = parentNode.getParentNode().orElse(null);
////                }
////                System.out.println("METHOD: "+ method);
//                // If the method call is to a void method, remove it
////                MethodDeclaration methodDecl = (MethodDeclaration) n.resolve().asMethod();
////                if (methodDecl.getType().isVoidType()) {
////                    if (count1[0] == index) {
////                        int lineNumber = n.getBegin().get().line;
////                        count1[0] += 1;
////                        fileMutationCount[0] = true;
////                        try {
////                            writer.write("total executed mutants so far: " + count1[0] + "\n");
////                            writer.write("line number : " + lineNumber + "\n");
////                        } catch (IOException e) {
////                            throw new RuntimeException(e);
////                        }
////                        return null;
////                    } else {
////                        count1[0] += 1;
////                    }
////                }
//
////                return super.visit(n, arg);
//                return null;
//            }
//        };
//
//        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
//        for (MethodDeclaration method : methods) {
//            method.accept(visitor, null);
//        }
//
//        return new Object[]{cu, fileMutationCount[0]};
//    }
//}
