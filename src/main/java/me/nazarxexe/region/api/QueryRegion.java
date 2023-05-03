package me.nazarxexe.region.api;

import cn.nukkit.level.generator.populator.impl.structure.utils.math.BoundingBox;
import cn.nukkit.math.BlockVector3;
import cn.nukkit.math.Vector3f;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import me.nazarxexe.region.region.ConfigRegion;
import me.nazarxexe.region.region.Region;
import me.nazarxexe.region.utils.UTILS;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QueryRegion implements Query {

    private Config region;
    private Config chunk;

    public QueryRegion(Config region, Config chunk) {
        this.chunk = chunk;
        this.region = region;
    }

    @Override
    @Nullable
    public Region getRegionByVector(BlockVector3 vector3) {

        List<String> cachedRegions = chunk.getStringList(UTILS.chunkCoordsIntoString( vector3.getChunkX(), vector3.getChunkZ()) );

        if (cachedRegions.isEmpty()) return null;

        List<ConfigRegion> collideRegions = new ArrayList<>();

        for (String cache : cachedRegions) {

            ConfigSection section = (ConfigSection) region.get(cache);

            List<Integer> pos1 = section.getIntegerList("pos1");
            List<Integer> pos2 = section.getIntegerList("pos2");

            collideRegions.add(
                    ConfigRegion.builder()
                            .name(cache)
                            .owner(UUID.fromString(section.get("owner", "")))
                            .flags(section.getIntegerList("flags"))
                            .pos1(new BlockVector3(pos1.get(0), pos1.get(1), pos1.get(2)))
                            .pos2(new BlockVector3(pos2.get(0), pos2.get(1), pos2.get(2)))
                            .priority(section.get("priority", 0))
                            .build()
            );

        }

        collideRegions.removeIf(region -> {
            BoundingBox boundingBox = new BoundingBox(region.firstPosition(), region.secondPosition());
            return (!(boundingBox.intersects(new BoundingBox(vector3, vector3))));
        });

        collideRegions.sort(ConfigRegion::compareTo);
        return collideRegions.isEmpty() ? null : collideRegions.get(0);
    }
}
