package me.nazarxexe.region.region;

import cn.nukkit.math.BlockVector3;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class TempRegion implements Region {

    private String name;
    private BlockVector3 pos1;
    private BlockVector3 pos2;

    public TempRegion setName(String name) {
        this.name = name;
        return this;
    }

    public TempRegion setPos1(BlockVector3 pos1) {
        this.pos1 = pos1;
        return this;
    }

    public TempRegion setPos2(BlockVector3 pos2) {
        this.pos2 = pos2;
        return this;
    }

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
        return null;
    }

    @Override
    public List<Integer> flags() {
        return null;
    }

    @Override
    public int priority() {
        return -1;
    }
}
