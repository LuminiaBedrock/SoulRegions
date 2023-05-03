package me.nazarxexe.region;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import me.nazarxexe.region.command.RegionCommand;
import me.nazarxexe.region.command.subcommands.FirstPositionCommand;
import me.nazarxexe.region.command.subcommands.RegionClaimCommand;
import me.nazarxexe.region.command.subcommands.SecondPositionCommand;
import me.nazarxexe.region.listener.PlayerListener;
import me.nazarxexe.region.region.TempRegion;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class SoulRegions extends PluginBase {

    private Config regions;
    private Config chunk;

    private volatile HashMap<UUID, TempRegion> temp = new HashMap<>();


    @Override
    public void onEnable() {
        // TODO Пока что YAML потому что проблемы
        regions = new Config(
                new File(this.getDataFolder(), "regions.yaml"),
                Config.YAML
        );
        chunk = new Config(
                new File(this.getDataFolder(), "chunk.yaml"),
                Config.YAML
        );
        // TODO Пока что YAML потому что проблемы

        getServer().getCommandMap().register(
                "region",
                new RegionCommand()
                        .addSubCommand(new FirstPositionCommand(temp))
                        .addSubCommand(new SecondPositionCommand(temp))
                        .addSubCommand(new RegionClaimCommand(temp, chunk, regions, this))
        );
        getServer().getPluginManager().registerEvents(new PlayerListener(regions, chunk), this);
    }
}
