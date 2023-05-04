package mutation;

import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;

public interface MutantCreator {
    Object[] createMutant(CompilationUnit cu2, int i) throws IOException;

    Object[] generateAllMutants(CompilationUnit cu1);
}
