package me.nazarxexe.region.region;

import cn.nukkit.math.BlockVector3;
import lombok.Builder;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Builder
public class ConfigRegion implements Region, Comparable {

    private String name;
    private UUID owner;
    private List<Integer> flags;
    private BlockVector3 pos1;
    private BlockVector3 pos2;
    private int priority;

    @Override
    public String name() {
        return name;
    }

    @Override
    public BlockVector3 firstPosition() {
        return pos1;
    }

    @Override
    public BlockVector3 secondPosition() {
        return pos2;
    }

    @Override
    public UUID owner() {
        return owner;
    }

    @Override
    public List<Integer> flags() {
        return flags;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public int compareTo(@NotNull Object o) {

        if (!(o instanceof ConfigRegion)) throw new IllegalArgumentException();

        return priority() - ((ConfigRegion)o).priority();
    }

    @Override
    public String toString() {

        return String.join("\n",
                "name: " + name,
                "pos1: " + pos1.x + ":" + pos1.y + ":" + pos1.z,
                "pos2: " + pos2.x + ":" + pos2.y + ":" + pos2.z,
                "piror: " + priority
        );

    }
}
