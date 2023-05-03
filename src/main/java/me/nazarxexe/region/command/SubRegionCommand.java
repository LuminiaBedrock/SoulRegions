package me.nazarxexe.region.command;

import cn.nukkit.command.CommandSender;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class SubRegionCommand {

    private @Getter Predicate<String[]> condition;
    private @Getter String permission;

    public SubRegionCommand (Predicate<String[]> consumer, String perm){
        this.condition = consumer;
        this.permission = perm;
    }

    public abstract void execute(CommandSender sender, String[] args);

}
