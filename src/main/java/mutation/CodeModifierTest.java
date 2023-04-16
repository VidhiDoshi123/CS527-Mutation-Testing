package mutation;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.*;
import java.util.*;

import java.io.File;

public class CodeModifierTest
{
	private static final String OUTPUT_DIRECTORY = "target/classes" ;
	private static final String OUTPUT_DIRECTORY2 = "target/classes/greater" ;

	public static void main(String args[]) throws IOException {
		testJsoup();
		System.out.println("done mutating");

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
		List<String> functionNames = Arrays.asList("createMutEquals", "createMutGreat");

		CodeModifier cmd1 = new CodeModifier();
		for(int i = 0; i<1; i++) {
				List<CompilationUnit> mutatedFile = new ArrayList<>();
				for (CompilationUnit cu2 : parsedFile) {
					CompilationUnit obj1 = cmd1.createMutEquals(cu2);
//					CompilationUnit obj2 = cmd1.createMutMultiply(cu2);
					mutatedFile.add(obj1);
//					mutatedFile.add(obj2);
				}
				mutatedFilesCompiler mutcom = new mutatedFilesCompiler();
				for (CompilationUnit cu : mutatedFile) {
					mutcom.mutatedJavaCompile(cu, OUTPUT_DIRECTORY);
				}
			try {
				runMaven();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println("done running mvn command");
			createReports();
			System.out.println("reports made");
			}

//		 for(int i = 0; i<7; i++) {
//		 	List<CompilationUnit> mutatedFile = new ArrayList<>();
//			 mutatedFilesCompiler mutcom = new mutatedFilesCompiler();
//		 	for (CompilationUnit cu2 : parsedFile) {
//		 		CompilationUnit obj1 = cmd1.createMutEquals(cu2);
//		 		mutcom.mutatedJavaCompile(obj1, OUTPUT_DIRECTORY1);
//		 	}
//			 List<CompilationUnit> slicedParsedFile = parsedFile.subList(0, 3);
//
//			 for (CompilationUnit cu2 : parsedFile) {
//				 CompilationUnit obj2 = cmd1.createMutPlus(cu2);
//				 mutcom.mutatedJavaCompile(obj2, OUTPUT_DIRECTORY2);
//			 }
//		 }
		}

	public static void runMaven() throws IOException, InterruptedException {

//		ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","cd D:\\UIUC 527\\mutation-testing\\jsoup && mvn surefire:test | mvn surefire-report:report -Dsurefire.report.title=report");
		ProcessBuilder pb = new ProcessBuilder("sh", "-c", " mvn surefire:test");
		pb.redirectErrorStream(true);
		Process process = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		process.waitFor();
	}

	public static void createReports() {
		ProcessBuilder processBuilder = new ProcessBuilder("mvn", "surefire-report:report-only");
		processBuilder.directory(new File("."));
		processBuilder.redirectErrorStream(true);
		Process process = null;
		try {
			process = processBuilder.start();
			String output = readInputStream(process.getInputStream());
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				System.out.println("Surefire report generated successfully");
			} else {
				System.out.println("Failed to generate Surefire report: " + output);
			}
		} catch (IOException | InterruptedException e) {
			System.out.println("Failed to execute Maven command: " + e.getMessage());
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
	}

	private static String readInputStream(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line).append(System.lineSeparator());
		}
		return stringBuilder.toString();
	}
	}

