package net.bukkitlabs.bukkitlabscloud.console;

import org.jetbrains.annotations.NotNull;

public class Command {

    private CloudCommand cloudCommand;
    private String label;
    private String usage;
    private String description;

    public Command(final CloudCommand cloudCommand, final String usage, final String label, final String description) {
        this.cloudCommand = cloudCommand;
        this.label = label;
        this.usage = usage;
        this.description = description;
    }

    @NotNull
    public CloudCommand getCloudCommand() {
        return cloudCommand;
    }

    @NotNull
    public String getLabel() {
        return label;
    }

    @NotNull
    public String getUsage() {
        return usage;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public static class Builder {

        private final Command currentBuilding;

        public Builder() {
            this.currentBuilding = new Command(null, null, null, null);
        }

        public Builder setCloudCommand(@NotNull CloudCommand cloudCommand) {
            this.currentBuilding.cloudCommand = cloudCommand;
            return this;
        }

        public Builder setLabel(@NotNull String label) {
            this.currentBuilding.label = label;
            return this;
        }

        public Builder setUsage(@NotNull String usage) {
            this.currentBuilding.usage = usage;
            return this;
        }

        public Builder getDescription(@NotNull String description) {
            this.currentBuilding.description = description;
            return this;
        }

        public Command build() {
            return this.currentBuilding;
        }
    }
}
