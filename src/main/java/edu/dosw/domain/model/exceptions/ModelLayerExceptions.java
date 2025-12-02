package edu.dosw.domain.model.exceptions;

public class ModelLayerExceptions extends RuntimeException{

    private static final String MESSAGE_CANT_BE_NULL = "Cannot have empty message";

    public ModelLayerExceptions(String message) {
        super(message);
    }

    public static ModelLayerExceptions emptyMessage(){
        return new ModelLayerExceptions(MESSAGE_CANT_BE_NULL);
    }

}
