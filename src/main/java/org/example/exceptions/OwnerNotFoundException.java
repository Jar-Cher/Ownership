package org.example.exceptions;

public class OwnerNotFoundException extends OwnershipResolutionException {

    public OwnerNotFoundException(String name) {
        super(name + " has no owner!");
    }

}
