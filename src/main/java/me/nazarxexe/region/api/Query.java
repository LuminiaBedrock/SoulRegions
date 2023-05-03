package me.nazarxexe.region.api;

import cn.nukkit.math.BlockVector3;
import cn.nukkit.math.Vector3f;
import me.nazarxexe.region.region.Region;

public interface Query {

    Region getRegionByVector(BlockVector3 vector3f);




}
