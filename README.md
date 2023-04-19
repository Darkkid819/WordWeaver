# WordWeaver
A Java-based text generator application that processes and analyzes the input text, storing all the words and their occurrences in a custom data structure. The program generates a random paragraph of user-defined length based on the input text.

# Features
Processes input text files and analyzes word frequencies
Utilizes custom data structure (linked lists) for efficient storage and retrieval
Filters and censors blacklisted words from the input text
Generates random paragraphs based on input text
Saves generated paragraphs to an output file
Supports processing multiple text files using a stack

# Getting Started
## Prerequisites
JDK 11 or later
Maven (optional)

## Building and Running
1. Clone this repository.
```console
git clone https://github.com/Darkkid819/WordWeaver.git
cd WordWeaver
```

2. Compile the project.
Using Maven:
```console
mvn compile
```

Or using javac:
```console
javac -d target/classes src/*.java
```

3. Run the application.

Using Maven:
```console
mvn exec:java -Dexec.mainClass="com.wordweaver.Main"
```

Or using java:
```console
java -cp target/classes com.wordweaver.Main
```

4. The generated output will be saved to the output folder as output.txt.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
