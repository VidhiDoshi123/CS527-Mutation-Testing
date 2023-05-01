package mutation;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CodeModifierTest
{
	private static final String OUTPUT_DIRECTORY = "target/classes" ;
	static FileWriter writer;
	static int totalMutantsKilled = 0;
	static int totalMutantsExecuted = 0;

	static {
		try {
			writer = new FileWriter("mutationScores.txt", false);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public CodeModifierTest() throws IOException {
	}

	public static void main(String args[]) throws IOException {
		//iterate over the list of mutations a.k.a operators
		//for each operator do steps inside the testJsoup function
		List<MutantCreator> mutations = new ArrayList<>();
//		mutations.add(new negateConditionals());
//		mutations.add(new conditionalsBoundary());
//		mutations.add(new mathMutator());
//		mutations.add(new RemoveConditionalsMutator());
//		mutations.add(new increment());
//		mutations.add(new returnValuesMutator());

		//exploring the below three
//		mutations.add(new invertNegative());
//		mutations.add(new EmptyReturnsMutator());
//		mutations.add(new VoidMethodCallMutator());

		System.out.println("let's start!");
		for (MutantCreator mutant : mutations) {
			totalMutantsKilled = 0;
			totalMutantsExecuted = 0;
			String nameOfClass = mutant.getClass().getSimpleName();
			writer.write("--------------------START---------------------------"+ "\n");
			writer.write("Operator Name is : " +nameOfClass+ "\n");
			writer.write("--------------------START---------------------------"+ "\n");
			testJsoup(mutant, nameOfClass);
		}
		System.out.println("done mutating!");
		writer.close();
	}

	public static List<File> getSubFolderJavaFiles(String path) {
		File mainFolder = new File(path);
		List<File> subFolderJavaFiles = new ArrayList<>();

		// checking if mainFolder exists then traversing the subfolder to extract javaFiles
		if (mainFolder.exists() && mainFolder.isDirectory()) {
			for (File subFile : mainFolder.listFiles()) {
				if (subFile.isDirectory()) {
					for (File file : subFile.listFiles()) {
						if (file.isFile()) {
							if (file.getName().endsWith(".java")) {
								subFolderJavaFiles.add(file);
							}
						}
					}
				} else if (subFile.isFile()) {
					if (subFile.getName().endsWith(".java")) {
						subFolderJavaFiles.add(subFile);
					}
				}
			}
		}
		return subFolderJavaFiles;
	}

	public static void testJsoup(final MutantCreator mutant, String nameOfClass) throws IOException {
		//add java files to a list
		String path = "src/main/java/org/jsoup";
		List<File> subFolderJavaFiles = getSubFolderJavaFiles(path);

		//compiling the mutants generated and adding it to target/classes
		System.out.println("done with extracting java files");
		System.out.println("starting the process of parsing,mutating,compiling,running");

		mutatedFilesCompiler mutcom = new mutatedFilesCompiler();
		// Store the updated killed count for the class name
		/*
		1) iterate over the java files
		2) decide how many mutators to create in each
		3) run maven clean and test
		4) compile the file
		5) mutate every operator and then run the file for each mutation
		6) run it and check the exit code
		7) if it is not zero then it means mutant has been killed
		8) record the number of mutants killed for each file
		9) sum it to get the number of mutants killed for each operator
		10) create reports
		*/

		System.out.println("length of subFolderJavaFiles : "+(subFolderJavaFiles).size());
//		File file = subFolderJavaFiles.get(0);
		for (File file : subFolderJavaFiles) {
			String fileName = file.getName();
			int killedCount = 0;
			int executeCount = 0;
			//x represents the total mutators to be created in a single file
			for(int x = 0;x<1;x++){
				writer.write("now value of x is : "+x + "\n");
				CompilationUnit cu1 = StaticJavaParser.parse(file);
				//pre run
				try{
					runMavenClean();
					runMavenTest();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}

				Object[] result = mutant.createMutant(cu1, x);
				CompilationUnit obj1 = (CompilationUnit) result[0];
				writer.write("mutation made is "+result[1]+"\n");
				if(result[1].equals(true)) {
					executeCount +=1;
				}

				mutcom.mutatedJavaCompile(obj1, OUTPUT_DIRECTORY);
				try {
					System.out.println("pre run complete");
					int exitValue = runMavenSureFireTest();
					if ((result[1].equals(true))&&(exitValue!=0)){
						killedCount +=1;
					}
					System.out.println("maven run complete");

//					String reportName = nameOfClass + "_" + fileName + "_" + x;
//					createReports(reportName);
//					System.out.println("report generated for : "+reportName);
//					copyHtmlFile(reportName);
//					System.out.println("copied reports generated for: "+ reportName);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			totalMutantsKilled += killedCount;
			totalMutantsExecuted +=executeCount;

			writer.write("file Name is : " +fileName+ "\n");
			writer.write("mutants Killed are : " +killedCount+ "\n");
			writer.write("mutants executed are : " +executeCount+ "\n");
			writer.write("------ "+"\n");
			break;
		}
		//done with all java files for an operator
		writer.write("--------------------END---------------------------"+ "\n");
		writer.write("Net killed for " + nameOfClass + " are: " + totalMutantsKilled +"\n");
		writer.write("Net executed for " + nameOfClass + " are: " + totalMutantsExecuted +"\n");

		double mutationScore = (double) totalMutantsKilled / totalMutantsExecuted * 100;

		writer.write("mutation score for " + nameOfClass + " is: " + mutationScore +"\n");
		writer.write("--------------------END---------------------------"+ "\n");
	}

	public static void runMavenClean() throws IOException, InterruptedException {
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

	public static int runMavenSureFireTest() throws IOException, InterruptedException {
		System.out.println("inside the maven surefire test function");
		ProcessBuilder pb = new ProcessBuilder("sh", "-c", " mvn surefire:test");
		pb.redirectErrorStream(true);
		Process process = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;

		//setting the timeout to make sure the code does not hang
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		long timeout = 20; // timeout in seconds
		executor.schedule(() -> {
			process.destroy();
			System.out.println("Timeout reached. Skipping to next file.");
		}, timeout, TimeUnit.SECONDS);

		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}

		process.waitFor();
		executor.shutdownNow();
		return process.exitValue();
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