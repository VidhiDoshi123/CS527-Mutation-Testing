# CS527 Project- Mutation Testing

**Mutation Testing** is a technique through which we bring artificial changes to the source code in order to enable the test suite to detect those changes. In this project we plan to implement a tool that can implement all the mutators available with Pitest tool and perform mutation testing on the jsoup project.



## Getting started
1. Clone the repository.
2. Run mvn clean, mvn test, to verify the code is in its original state.
3. Run CodeModifierTest.java file.
4. See mutationScores.txt to find mutants killed, executed and generated for respective mutators.
5. Check finalStatistics.txt to find the comparison of our tool with Pitest.

## Approach
Our main aim is to generate the mutants and execute them for the mutators implemented by Pitest. For doing so, we are first genrating mutatnts, and then executing them. After execution phase, we are running the test suite of jsoup and checking if the tests are able to detect those changes. If yes, that means we have successfully killed the mutant. Below is the step by step explanation of the process we are following:
1. Create a Java file for each mutator 
2. Iterate over all the categories of mutators
3. Extract all the Java files 
4. Iterate over all Java files
5. Parse Java files
6. Generate Mutants
7. Select the mutants to execute
8. Execute Mutants
9. Record the statistics for the respective mutator
10. Generate maven surefire-reports
11. Copy the reports to a different directory
12. Repeat steps 4 to 11 for all mutators
13. Print the consolidated statistics.


