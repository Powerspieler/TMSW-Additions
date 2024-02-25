package de.powerspieler.tmswadditions.sitting;

import com.destroystokyo.paper.MaterialSetTag;
import com.destroystokyo.paper.MaterialTags;
import de.powerspieler.tmswadditions.TMSWAdditions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.HashMap;
import java.util.Map;


public class Sitting implements Listener {

    private static final NamespacedKey ARROW_KEY = new NamespacedKey(TMSWAdditions.getPlugin(), "sitting_arrow");
    private final Map<Block, Location> chair_data = new HashMap<>();

    @EventHandler
    private void onStairClick(PlayerInteractEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand().isEmpty() && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND){
            if(event.getClickedBlock() != null && isValidBlock(event.getClickedBlock()) && isChairMountable(event.getClickedBlock(), event.getBlockFace(), event.getPlayer())){
                event.setCancelled(true);
                Player player = event.getPlayer();
                if(isChairBlocked(event.getClickedBlock()) || chair_data.containsKey(event.getClickedBlock())){
                    player.sendActionBar(Component.text("This chair is blocked!", NamedTextColor.RED));
                    return;
                }
                if(player.getVehicle() != null){
                    return;
                }

                Location arrowLocation = event.getClickedBlock().getLocation().add(0.5,-0.1,0.5);
                Arrow arrow = (Arrow) arrowLocation.getWorld().spawnEntity(arrowLocation,
                        EntityType.ARROW, CreatureSpawnEvent.SpawnReason.CUSTOM,
                        entity -> {entity.setGravity(false); entity.setRotation(0f,-90f);});
                arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                arrow.getPersistentDataContainer().set(ARROW_KEY, PersistentDataType.BOOLEAN, true);
                this.chair_data.put(event.getClickedBlock(), player.getLocation());
                arrow.addPassenger(player);
            }
        }
    }

    @EventHandler
    private void onStandup(EntityDismountEvent event){
        if(event.getDismounted().getPersistentDataContainer().has(ARROW_KEY)){
            Entity arrow = event.getDismounted();
            Block block = arrow.getWorld().getBlockAt(arrow.getLocation().add(0,0.5,0));
            event.getEntity().teleport(event.getEntity().getLocation().add(0,0.6,0));
            chair_data.remove(block);
            event.getDismounted().remove();
        }
    }

    private boolean isValidBlock(Block block){
        if(MaterialSetTag.STAIRS.getValues().contains(block.getType())){
            Stairs stair = (Stairs) block.getBlockData();
            return stair.getHalf() == Bisected.Half.BOTTOM;
        } else if(MaterialSetTag.SLABS.getValues().contains(block.getType())){
            Slab slab = (Slab) block.getBlockData();
            return slab.getType() == Slab.Type.BOTTOM;
        }
        return false;
    }

    private boolean isChairBlocked(Block block){
        Block blockAbove = block.getRelative(BlockFace.UP);
        if(blockAbove.isSolid()){
            Material material = blockAbove.getType();
            return !(MaterialTags.TRAPDOORS.isTagged(material)
                    || MaterialTags.FENCE_GATES.isTagged(material)
                    || MaterialTags.DOORS.isTagged(material)
                    || Tag.BANNERS.isTagged(material));
        }
        return false;
    }

    private boolean isChairMountable(Block block, BlockFace face , Player player){
        if(face == BlockFace.DOWN){
            return false;
        }
        try{
            Stairs stair = (Stairs) block.getBlockData();
            if(stair.getFacing() == face){
                return block.getLocation().getY() <= player.getLocation().getY() + 0.25;
            }
        } catch (ClassCastException ignored){}
        return true;
    }
}
