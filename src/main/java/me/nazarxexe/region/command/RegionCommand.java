package me.nazarxexe.region.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class RegionCommand extends Command {


    private List<SubRegionCommand> subRegionCommands;

    public RegionCommand() {
        super("rg");

        subRegionCommands = new ArrayList<>();
    }

    public RegionCommand addSubCommand(SubRegionCommand command) {
        subRegionCommands.add(command);
        return this;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        subRegionCommands.forEach((subRegionCommand) -> {

            if (!(subRegionCommand.getCondition().test(args))) return;
            if (!(sender.hasPermission(subRegionCommand.getPermission()))) return;

            subRegionCommand.execute(sender, args);

        });
        return true;
    }
}
