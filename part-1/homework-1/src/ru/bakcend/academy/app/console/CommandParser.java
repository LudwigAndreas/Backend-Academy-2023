package ru.bakcend.academy.app.console;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    private CommandParser() {}

    static public Optional<Command> parse(String input) {
        ArrayList<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"([^\"]*)\"|\\S+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String token = matcher.group(1);
            if (token != null) {
                tokens.add(token);
            } else {
                tokens.add(matcher.group());
            }
        }
        if (tokens.isEmpty())
            return Optional.empty();
        return Optional.of(new Command(tokens.get(0), tokens.subList(1, tokens.size())));
    }
}
