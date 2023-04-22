package mutation;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.*;
import java.util.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeModifierTest
{
	private static final String OUTPUT_DIRECTORY = "target/classes" ;
	private static final String OUTPUT_DIRECTORY2 = "target/classes/greater" ;
	public static int mutantsKilled = 0;
	public static final int numMutations = 15;

	public static void main(String args[]) throws IOException, InterruptedException {
		testJsoup();
		System.out.println("done mutating");

	}
	public static void testJsoup() throws IOException, InterruptedException {
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
			int k = 1;
			mutatedFilesCompiler mutcom = new mutatedFilesCompiler();
			List<CompilationUnit> mutatedFile = new ArrayList<>();

			if (k == 1) {
				for (CompilationUnit cu2 : parsedFile) {
					CompilationUnit obj1 = cmd1.createMutEquals(cu2);
//					CompilationUnit obj2 = cmd1.createMutMultiply(cu2);
					mutatedFile.add(obj1);
//					mutatedFile.add(obj2);
				}

				for (CompilationUnit cu : mutatedFile) {
					mutcom.mutatedJavaCompile(cu, OUTPUT_DIRECTORY);
				}
				try {
					runMaven();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				System.out.println("done running mvn command");

				createReports("MutEqualsReport");
				System.out.println("html report made");

				pdfCreator("MutEqualsReport");
				System.out.println("pdf made");
				k += 1;
			}

//			repeating the above lines
			if(k==100) {
				runMavenClean();

				try {
					runMavenTest();
				} catch (IOException e) {
					throw new RuntimeException(e);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				k+=1;
			}

			if(k==100) {
				mutatedFile.clear();
				for (CompilationUnit cu2 : parsedFile) {
					CompilationUnit obj2 = cmd1.createMutMultiply(cu2);
					mutatedFile.add(obj2);
				}
				for (CompilationUnit cu : mutatedFile) {
					mutcom.mutatedJavaCompile(cu, OUTPUT_DIRECTORY);
				}
				try {
					runMaven();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				System.out.println("done running mvn command");

				createReports("createMutMultiply");
				System.out.println("createMutMultiply made");
				pdfCreator("createMutMultiply");
				System.out.println("pdf made for createMutMultiply");
			}
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

	public static void runMavenTest() throws IOException, InterruptedException {

//		ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","cd D:\\UIUC 527\\mutation-testing\\jsoup && mvn surefire:test | mvn surefire-report:report -Dsurefire.report.title=report");
		ProcessBuilder pb = new ProcessBuilder("sh", "-c", " mvn test");
		pb.redirectErrorStream(true);
		Process process = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		System.out.println("reading line" + reader.readLine());

		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		process.waitFor();
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

	public static void runMaven() throws IOException, InterruptedException {

//		ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","cd D:\\UIUC 527\\mutation-testing\\jsoup && mvn surefire:test | mvn surefire-report:report -Dsurefire.report.title=report");
		ProcessBuilder pb = new ProcessBuilder("sh", "-c", " mvn surefire:test");
		pb.redirectErrorStream(true);
		Process process = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			Pattern pattern = Pattern.compile("Failures: (\\d+), Errors: (\\d+)");
		}
		process.waitFor();
	}

	public static void createReports(String reportType) {
//		ProcessBuilder processBuilder = new ProcessBuilder("mvn", "-Dmaven.surefire.report.outputDirectory=target/site/", "surefire-report:report-only", "-Dsurefire.report.title=" + reportType);

//		ProcessBuilder processBuilder = new ProcessBuilder("mvn", "surefire-report:report-only", "-Dsurefire.report.name=" + reportType, "-Dmaven.surefire.report.outputDirectory=target/site1");


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

	public static void pdfCreator(String reportName) {
		String htmlFile = "/Users/aparna/Desktop/527FINAL/Mid-termProject/jsoup/target/site/surefire-report.html";
		String pdfFile = reportName + "report.pdf";
		String[] command = {"wkhtmltopdf", "-s", "A4", htmlFile, pdfFile};
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File("."));
		processBuilder.redirectErrorStream(true);
		Process process = null;
		try {
			process = processBuilder.start();
			String output = readInputStream(process.getInputStream());
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				System.out.println("PDF report generated successfully");
			} else {
				System.out.println("Failed to generate PDF report: " + output);
			}
		} catch (IOException | InterruptedException e) {
			System.out.println("Failed to execute command: " + e.getMessage());
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
	}

}

