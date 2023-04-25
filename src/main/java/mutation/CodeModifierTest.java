package mutation;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.*;
import java.util.*;

import java.io.File;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class CodeModifierTest
{
	private static final String OUTPUT_DIRECTORY = "target/classes" ;
	private static final String OUTPUT_DIRECTORY2 = "target/classes/greater" ;

	public static void main(String args[]) throws IOException {
		List<MutantGeneratorClass> mutations = new ArrayList<>();
		mutations.add(new EqualsMutant());
		mutations.add(new MultiplyMutant());
		for (MutantGeneratorClass mutant : mutations) {
			String nameOfClass = mutant.getClass().getSimpleName();
			testJsoup(mutant,nameOfClass);
		}
		System.out.println("done mutating");
	}
	public static void testJsoup(MutantGeneratorClass mutant, String nameOfClass) throws IOException {

		//pre run
		try{
			runMavenClean();
			runMavenTest();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		//mutation started
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
//		CodeModifier cmd1 = new CodeModifier();
		for(int i = 0; i<1; i++) {
			List<CompilationUnit> mutatedFile = new ArrayList<>();
//			for (CompilationUnit cu2 : parsedFile) {
//				CompilationUnit obj1 = cmd1.createMutEquals(cu2);
////					CompilationUnit obj2 = cmd1.createMutMultiply(cu2);
//				mutatedFile.add(obj1);
////					mutatedFile.add(obj2);
//			}
//			mutatedFilesCompiler mutcom = new mutatedFilesCompiler();
//			for (CompilationUnit cu : mutatedFile) {
//				mutcom.mutatedJavaCompile(cu, OUTPUT_DIRECTORY);
//			}

			for (CompilationUnit cu2 : parsedFile) {
				CompilationUnit obj1 = mutant.createMutant(cu2);
//					CompilationUnit obj2 = cmd1.createMutMultiply(cu2);
				mutatedFile.add(obj1);
//					mutatedFile.add(obj2);
			}
			mutatedFilesCompiler mutcom = new mutatedFilesCompiler();
			for (CompilationUnit cu : mutatedFile) {
				mutcom.mutatedJavaCompile(cu, OUTPUT_DIRECTORY);
			}

			try {
				System.out.println("pre run complete");
				runMavenSureFireTest();
				createReports(nameOfClass);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println("report generated.");
			copyHtmlFile(nameOfClass);
			System.out.println("copied reports generated.");
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

	public static void runMavenClean() throws IOException, InterruptedException {

//		ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","cd D:\\UIUC 527\\mutation-testing\\jsoup && mvn surefire:test | mvn surefire-report:report -Dsurefire.report.title=report");
		ProcessBuilder pb = new ProcessBuilder("sh", "-c", " mvn clean");
		pb.redirectErrorStream(true);
		Process process = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		process.waitFor();
	}

	public static void runMavenTest() throws IOException, InterruptedException {

//		ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","cd D:\\UIUC 527\\mutation-testing\\jsoup && mvn surefire:test | mvn surefire-report:report -Dsurefire.report.title=report");
		ProcessBuilder pb = new ProcessBuilder("sh", "-c", " mvn test");
		pb.redirectErrorStream(true);
		Process process = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		process.waitFor();
	}

	public static void runMavenSureFireTest() throws IOException, InterruptedException {

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

	public static void createReports(String nameOfClass) {
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
				String sourceFilePath = "target/site/surefire-report.html";
				File sourceFile1 = new File(sourceFilePath);
				sourceFile1.renameTo(new File("target/site/" + nameOfClass + ".html"));

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

		public static void copyHtmlFile(String nameOfClass) {
			String sourceFilePath = "target/site/" + nameOfClass + ".html";
			String destinationDirPath = "htmlMutatedReports/";

			// Create file object
			File sourceFile = new File(sourceFilePath);
			File destinationDir = new File(destinationDirPath);

			// Check if the source file exists
			if (sourceFile.exists()) {
//				sourceFile.renameTo(new File("target/site/" + nameOfClass + ".html"));
				// Check if the destination directory exists
				if (!destinationDir.exists()) {
					// Create the destination directory if it doesn't exist
					destinationDir.mkdirs();
				}

				try {
					// Use the Files.copy() method to copy the file
					Path sourcePath = sourceFile.toPath();
					Path destinationPath = new File(destinationDir, sourceFile.getName()).toPath();
					Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("File copied successfully.");
				} catch (IOException e) {
					System.err.println("Error copying file: " + e.getMessage());
				}
			} else {
				System.err.println("Source file does not exist.");
			}
		}
}
