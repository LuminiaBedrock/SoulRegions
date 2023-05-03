package me.nazarxexe.region.region;


import cn.nukkit.math.BlockVector3;

import java.util.List;
import java.util.UUID;

public interface Region {

    String name();
    BlockVector3 firstPosition();
    BlockVector3 secondPosition();

    UUID owner();

    List<Integer> flags();

    int priority();

}
