package org.example.parsers.CodeOwners;

import org.example.models.Member;
import javafx.util.Pair;

import java.io.FileNotFoundException;

public interface CodeOwnersParser {

    Pair<String, Member> parseCodeOwners(String pathToChangedFile) throws FileNotFoundException;

}
