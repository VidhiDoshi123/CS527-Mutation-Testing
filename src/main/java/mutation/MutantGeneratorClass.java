package mutation;

import com.github.javaparser.ast.CompilationUnit;

public interface MutantGeneratorClass {
    CompilationUnit createMutant(CompilationUnit cu2);
}
