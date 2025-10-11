package com.nerosoft.aone.identity.seedwork;

import lombok.Getter;

/**
 * A generic class to encapsulate the result of a command execution along with an optional message.
 *
 * @param <R> The type of the result.
 */
public class CommandResult<R> {
    @Getter
    private R result;

    @Getter
    private String message;

    public CommandResult(R result, String message) {
        this.result = result;
        this.message = message;
    }

    public CommandResult(R result) {
        this.result = result;
        this.message = null;
    }
}