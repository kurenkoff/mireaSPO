import lexer.Lexer;
import lexer.Token;
import optimizer.Optimiser;
import parser.Parser;
import stackmaschine.Executor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void main(String[] args) throws IOException {
        String program = readFile("program.txt", StandardCharsets.US_ASCII);
        System.out.println(program);

        Lexer lexer = new Lexer(program);
        List<Token> tokens = lexer.getTokens();

        Parser parser = new Parser(tokens);

        if (parser.lang()) {
            Optimiser optimiser = new Optimiser(parser.tableOfVariables, parser.reversePolishNotation);
            optimiser.execute();

            Executor executor = new Executor();
            System.out.println("Вывод программы:");
            executor.start(parser);
        }
    }
}
