package com.nerosoft.aone.identity.handler;

import an.awesome.pipelinr.Command;
import com.nerosoft.aone.identity.command.TokenCreateCommand;
import com.nerosoft.aone.identity.domain.Token;
import com.nerosoft.aone.identity.repository.TokenRepository;
import com.nerosoft.aone.identity.seedwork.CommandResult;
import org.springframework.stereotype.Component;

/*
 Handler for creating tokens
*/
@Component
public class TokenCommandHandler implements Command.Handler<TokenCreateCommand, CommandResult<Boolean>> {

    private final TokenRepository repository;

    public TokenCommandHandler(TokenRepository repository) {

        this.repository = repository;
    }

    @Override
    public CommandResult<Boolean> handle(TokenCreateCommand command) {
        try {
            var token = Token.create("access_token", command.getKey(), command.getSubject(), command.getIssues(), command.getExpires());
            repository.save(token);
            return new CommandResult<>(true);
        } catch (Exception e) {
            return new CommandResult<>(false, e.getMessage());
        }
    }
}
