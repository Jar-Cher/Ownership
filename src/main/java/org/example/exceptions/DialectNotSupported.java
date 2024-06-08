package org.example.exceptions;

public class DialectNotSupported extends OwnershipResolutionException {

    public DialectNotSupported(String dialectName) {
        super(dialectName + "is not a supported CODEOWNERS dialect");
    }

}