package com.nerosoft.aone.identity.command;

import an.awesome.pipelinr.Command;
import com.nerosoft.aone.identity.seedwork.CommandResult;
import lombok.Data;

import java.util.Date;

/*
 Command for creating tokens
*/
@Data
public class TokenCreateCommand implements Command<CommandResult<Boolean>> {

    private long subject;

    private String key;

    private Date issues;

    /* expiration time in seconds */
    private Date expires;
}
