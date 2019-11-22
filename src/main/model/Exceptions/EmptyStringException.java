/*
 * Copyright (c) 2019. Created by Harvey Huang.
 * It is not allowed to use the project in any course.
 * All rights reserved.
 */

package main.model.Exceptions;

public abstract class EmptyStringException extends Exception{
    public EmptyStringException(String message) {
        super(message);
    }
}
