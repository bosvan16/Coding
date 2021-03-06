package net.wildbill22.draco.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.wildbill22.draco.entities.EntityMyRock;
import net.wildbill22.draco.lib.BALANCE;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMyRock extends ModItems 
{	
	public static float rockDamage = (float) BALANCE.MOBPROP.ROCK_DAMAGE;
	public static final String name = "rock";
	
	public ItemMyRock() {
		super(name, name);
		setMaxStackSize(64);
	}
	
	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		// Only spawn new entities on the server!
		if (!world.isRemote) {
			//Decrease an item from the stack because you just used it!
			if (!player.capabilities.isCreativeMode)
				--stack.stackSize;
	
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
			/* This method will spawn an entity in the World, you can use with anything that extends
			* the Entity class, in this case it's the EntitySpear class
			*/
			world.spawnEntityInWorld(new EntityMyRock(world, player));
		}
		return stack;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add("Description: Rock");
		list.add("Ability: Causes " + rockDamage + " throw damage.");
	}
}