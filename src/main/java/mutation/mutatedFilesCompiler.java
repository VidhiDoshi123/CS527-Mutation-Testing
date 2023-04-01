package mutation;
import com.github.javaparser.ast.CompilationUnit;
import javax.tools.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;


public class mutatedFilesCompiler {
    public void mutatedJavaCompile(CompilationUnit cu, String outputDirectory) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null,null,null);
        int lengthArray = cu.getTypes().size();

        //if files exist then start compiling
        if (lengthArray > 0) {
            String className = cu.getTypes().get(0).getNameAsString() + ".java";
            JavaFileObject fileObject = new StringJavaFileObject(className,cu.toString());

            Iterable<?extends JavaFileObject> compilationItems = Arrays.asList(fileObject);

            List<String> optionsVal;
            optionsVal = Arrays.asList("-d",outputDirectory);

            //compiling to generate classes
            compiler.getTask(null,fileManager,null,optionsVal,null,compilationItems).call();
            fileManager.close();
        }
    }

    private static class StringJavaFileObject extends SimpleJavaFileObject{
        private final String code;

        protected StringJavaFileObject(String uri, String code) {
            super(URI.create("string:///"+uri),Kind.SOURCE);
            this.code = code;
        }
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors){
            return code;}
    }
}
