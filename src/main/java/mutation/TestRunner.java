package mutation;
import java.io.File;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class TestRunner {
    public static void main(String[] args) {
        // Set the project directory and the goal
        File projectDir = new File("/path/to/project/directory");
        String goal = "surefire-report:report-only";

        // Set the request and the invoker
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(projectDir, "pom.xml"));
        request.setGoals(Collections.singletonList(goal));
        DefaultInvoker invoker = new DefaultInvoker();

        // Invoke the goal and handle the exceptions
        try {
            InvocationResult result = invoker.execute(request);
            if (result.getExitCode() != 0) {
                throw new RuntimeException("Failed to generate test report");
            }
        } catch (MavenInvocationException e) {
            throw new RuntimeException("Failed to generate test report", e);
        }
    }
}
