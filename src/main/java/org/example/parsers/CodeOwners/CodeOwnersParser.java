package org.example.parsers.CodeOwners;

import org.example.models.Member;
import javafx.util.Pair;

public interface CodeOwnersParser {

    public Pair<String, Member> parseCodeOwners();
}
