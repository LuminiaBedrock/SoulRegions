package me.nazarxexe.region.command.subcommands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import me.nazarxexe.region.command.SubRegionCommand;
import me.nazarxexe.region.region.Region;
import me.nazarxexe.region.region.TempRegion;
import me.nazarxexe.region.utils.UTILS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RegionClaimCommand extends SubRegionCommand {

    private HashMap<UUID, TempRegion> temp;
    private Config chunk;
    private Config region;
    private Plugin plugin;


    public RegionClaimCommand(HashMap<UUID, TempRegion> temp, Config chunk, Config region, Plugin plugin) {
        super((args) -> args[0].equals("claim"), "region.claim");
        this.temp = temp;
        this.chunk = chunk;
        this.region = region;
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = sender.asPlayer();

        if (player == null) return;

        Region rg = temp.get(player.getUniqueId());

        if (region.exists(args[1])) {
            sender.sendMessage("Region already claimed.");
            return;
        }

        if (rg == null) {
            sender.sendMessage("You didn't set positions for region!");
            return;
        }
        if (rg.firstPosition() == null || rg.secondPosition() == null) {
            sender.sendMessage("You didn't set positions for region!");
            return;
        }

        saveIntoRegions(player, args);

    }


    private void saveIntoRegions(Player player, String[] args) {
        plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
            @Override
            public void onRun() {

                // rg
                Region rg = temp.get(player.getUniqueId());

                region.set(args[1], new ConfigSection());
                ConfigSection section = (ConfigSection) region.get(args[1]);

                section.set("owner", player.getUniqueId().toString());

                section.set("pos1", List.of(
                        rg.firstPosition().x,
                        rg.firstPosition().y,
                        rg.firstPosition().z
                ));

                section.set("pos2", List.of(
                        rg.secondPosition().x,
                        rg.secondPosition().y,
                        rg.secondPosition().z
                ));

                section.set("flags", new ArrayList<>());

                // chunk caching
                int pos1_chunkX = rg.firstPosition().getChunkX();
                int pos1_chunkZ = rg.firstPosition().getChunkZ();

                int pos2_chunkX = rg.secondPosition().getChunkX();
                int pos2_chunkZ = rg.secondPosition().getChunkZ();

                int max_x = Math.max(pos1_chunkX, pos2_chunkX);
                int min_x = Math.min(pos1_chunkX, pos2_chunkX);

                int max_z = Math.max(pos1_chunkZ, pos2_chunkZ);
                int min_z = Math.min(pos1_chunkZ, pos2_chunkZ);

                for (int i=min_x; i <= max_x; i++) {

                    for (int j=min_z; j <= max_z; j++) {

                        if (!(section.exists("chunk"))){
                            section.set("chunk", List.of(UTILS.chunkCoordsIntoString(i,j)));
                        }else {
                            List<String> cachedChunks = section.getStringList("chunk");
                            cachedChunks.add(UTILS.chunkCoordsIntoString(i,j));
                            section.set("chunk", cachedChunks);
                        }

                        List<String> chunkCache = section.getStringList("chunk");
                        chunkCache.add(UTILS.chunkCoordsIntoString(i,j));
                        region.set("chunk", chunkCache);

                        if ( chunk.exists(UTILS.chunkCoordsIntoString(i,j)) ){
                            List<String> chunks = chunk.getStringList(UTILS.chunkCoordsIntoString(i,j));
                            chunks.add(args[1]);
                            chunk.set(UTILS.chunkCoordsIntoString(i,j), chunks);
                        }else {
                            chunk.set(UTILS.chunkCoordsIntoString(i,j), List.of( args[1] ));
                        }
                    }

                }

                chunk.save();
                region.save();

                player.sendMessage("&aSuccessfully created region.");
            }
        });
    }

}
