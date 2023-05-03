package me.nazarxexe.region.command.subcommands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import me.nazarxexe.region.command.SubRegionCommand;
import me.nazarxexe.region.region.TempRegion;

import java.util.HashMap;
import java.util.UUID;

public class FirstPositionCommand extends SubRegionCommand {

    private HashMap<UUID, TempRegion> temp;

    public FirstPositionCommand(HashMap<UUID, TempRegion> temp) {
        super((args) -> args[0].equals("pos1"), "region.pos1");
        this.temp = temp;
    }



    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender.isPlayer())) return;
        Player player = sender.asPlayer();
        if (player == null) return;

        if (!( temp.containsKey(player.getUniqueId()) )) {

            temp.put(player.getUniqueId(), new TempRegion()
                    .setPos1(player.asBlockVector3())
            );

        }else {
            temp.get(player.getUniqueId()).setPos1(player.asBlockVector3());
        }

    }
}
