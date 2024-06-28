package com.example.miniapp_restaurant.Models.Server.Command;

import java.util.Date;
import java.util.Map;

public class CommandBoundary {

    private CommandId commandId;
    private String command;
    private TargetObject targetObject;
    private Date invocationTimestamp;
    private InvokedBy invokedBy;
    private Map<String, Object> commandAttributes;

    public CommandBoundary() {
    }

    public CommandId getCommandId() {
        return commandId;
    }

    public CommandBoundary setCommandId(CommandId commandId) {
        this.commandId = commandId;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public CommandBoundary setCommand(String command) {
        this.command = command;
        return this;
    }

    public TargetObject getTargetObject() {
        return targetObject;
    }

    public CommandBoundary setTargetObject(TargetObject targetObject) {
        this.targetObject = targetObject;
        return this;
    }

    public Date getInvocationTimestamp() {
        return invocationTimestamp;
    }

    public CommandBoundary setInvocationTimestamp(Date invocationTimestamp) {
        this.invocationTimestamp = invocationTimestamp;
        return this;
    }

    public InvokedBy getInvokedBy() {
        return invokedBy;
    }

    public CommandBoundary setInvokedBy(InvokedBy invokedBy) {
        this.invokedBy = invokedBy;
        return this;
    }

    public Map<String, Object> getCommandAttributes() {
        return commandAttributes;
    }

    public CommandBoundary setCommandAttributes(Map<String, Object> commandAttributes) {
        this.commandAttributes = commandAttributes;
        return this;
    }
}
