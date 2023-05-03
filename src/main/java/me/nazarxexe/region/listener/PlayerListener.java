package me.nazarxexe.region.listener;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockExplodeEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityExplodeEvent;
import cn.nukkit.event.entity.ExplosionPrimeEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.utils.Config;
import me.nazarxexe.region.api.QueryRegion;
import me.nazarxexe.region.region.Flags;
import me.nazarxexe.region.region.Region;

public class PlayerListener implements Listener {

    private final Config region;
    private final Config chunk;
    public PlayerListener(Config region, Config chunk) {
        this.region = region;
        this.chunk = chunk;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Region rg = new QueryRegion(region, chunk).getRegionByVector(e.getPlayer().asBlockVector3());
        if (rg == null) return;

        if (!(rg.owner().equals(e.getPlayer().getUniqueId()))) {
            if (!(rg.flags().contains(Flags.BREAKABLE.getId()))) {
                e.setCancelled();
                return;
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        Region rg = new QueryRegion(region, chunk).getRegionByVector(e.getPlayer().asBlockVector3());
        if (rg == null) return;

        if (!(rg.owner().equals(e.getPlayer().getUniqueId()))) {
            if (!(rg.flags().contains(Flags.BUILDABLE.getId()))) {
                e.setCancelled();
                return;
            }
        }

    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player player)) return;
        if (!(e.getDamager() instanceof Player damager)) return;

        Region rg_damager = new QueryRegion(region, chunk).getRegionByVector(damager.asBlockVector3());
        Region rg_player = new QueryRegion(region,chunk).getRegionByVector(player.asBlockVector3());

        if (rg_damager != null) {

            if (rg_player == null) return;

            if (!(rg_damager.flags().contains(Flags.PVPABLE.getId())) || (!(rg_player.flags().contains(Flags.PVPABLE.getId()))))
                e.setCancelled();

        }

    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        System.out.println("explode");
        QueryRegion query = new QueryRegion(region, chunk);
        // TODO remove

        event.getBlockList()
                .removeIf((block -> {
                    Region rg = query.getRegionByVector(block.asBlockVector3());
                    if (rg == null) return false;

                    if (!(rg.flags().contains(Flags.EXPLODEABLE.getId()))) return true;

                    return false;
                } ));

    }

    @EventHandler
    public void onContainerOpen(PlayerInteractEvent e) {

        Region rg = new QueryRegion(region, chunk).getRegionByVector(e.getPlayer().asBlockVector3());

        if (rg == null) return;

        if (!(e.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) return;

        if (e.getBlock() == null) return;

        int id= e.getBlock().getId();

        if ( id == Block.CHEST || id == Block.ENDER_CHEST || id == Block.HOPPER_BLOCK ||
            id == Block.DISPENSER || id == Block.DROPPER || id == Block.TRAPPED_CHEST ) {

            if (!(rg.owner().equals(e.getPlayer().getUniqueId()))) {
                if (!(rg.flags().contains(Flags.BUILDABLE.getId()))) {
                    e.setCancelled();
                }
            }

        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Region rg =new QueryRegion(region, chunk).getRegionByVector(e.getPlayer().asBlockVector3());

        e.getPlayer().sendPopup(
                rg == null ? "null" : rg.toString()
        );

    }




}
