package mutation;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.*;
import java.util.*;

public class CodeModifierTest
{
	private static final String OUTPUT_DIRECTORY = "target/classes" ;

	public static void main(String args[]) throws IOException {
		testJsoup();
	}
	public static void testJsoup() throws IOException {
		String path = "src/main/java/org/jsoup";
		File mainFolder = new File(path);
		List<File> subFolderJavaFiles = new ArrayList<>();

		//1) checking if mainFolder exists then traversing the subfolder to extract javaFiles
		if (mainFolder.exists() && mainFolder.isDirectory())
			for (File subFile : mainFolder.listFiles()) {
				if (subFile.isDirectory())
					for (File file : subFile.listFiles())
                        if (file.isFile())
							if (file.getName().endsWith(".java"))
								subFolderJavaFiles.add(file);

				else if (subFile.isFile())
					if (subFile.getName().endsWith(".java"))
						subFolderJavaFiles.add(subFile);
			}


		//2) parsing the collected java files
		List<CompilationUnit> parsedFile = new ArrayList<>();
		for (File file : subFolderJavaFiles) {
			try{
				CompilationUnit cu1 = StaticJavaParser.parse(file);
				parsedFile.add(cu1);
				}
			catch(Exception e){
//				System.out.println(e);
			}
        }

		//3) compiling the mutants generated and adding it to target/classes
		CodeModifier cmd1 = new CodeModifier();
		for(int i = 0; i<7; i++) {
				List<CompilationUnit> mutatedFile = new ArrayList<>();
				for (CompilationUnit cu2 : parsedFile) {
					CompilationUnit obj1 = cmd1.createMutEquals(cu2);
					CompilationUnit obj2 = cmd1.createMutGreat(cu2);
					mutatedFile.add(obj1);
					mutatedFile.add(obj2);
				}
				mutatedFilesCompiler mutcom = new mutatedFilesCompiler();
				for (CompilationUnit cu : mutatedFile) {
					mutcom.mutatedJavaCompile(cu, OUTPUT_DIRECTORY);
				}
			}
		}
	}

