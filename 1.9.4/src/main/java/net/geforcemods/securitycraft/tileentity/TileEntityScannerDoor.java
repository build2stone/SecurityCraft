package net.geforcemods.securitycraft.tileentity;

import net.geforcemods.securitycraft.api.CustomizableSCTE;
import net.geforcemods.securitycraft.api.Option;
import net.geforcemods.securitycraft.blocks.BlockScannerDoor;
import net.geforcemods.securitycraft.misc.EnumCustomModules;
import net.geforcemods.securitycraft.util.BlockUtils;
import net.geforcemods.securitycraft.util.PlayerUtils;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class TileEntityScannerDoor extends CustomizableSCTE
{
	public void entityViewed(EntityLivingBase entity)
	{
		IBlockState upperState = worldObj.getBlockState(pos);
		IBlockState lowerState = worldObj.getBlockState(pos.down());

		if(!worldObj.isRemote && upperState.getValue(BlockScannerDoor.HALF) == BlockDoor.EnumDoorHalf.UPPER)
		{
			if(!(entity instanceof EntityPlayer))
				return;
			else
			{
				if(!getOwner().isOwner((EntityPlayer) entity))
				{
					PlayerUtils.sendMessageToPlayer((EntityPlayer) entity, I18n.translateToLocal("item.scannerDoorItem.name"), I18n.translateToLocal("messages.retinalScanner.notOwner").replace("#", getOwner().getName()), TextFormatting.RED);
					return;
				}

				boolean open = !BlockUtils.getBlockPropertyAsBoolean(worldObj, pos.down(), BlockScannerDoor.OPEN);

				worldObj.setBlockState(pos, upperState.withProperty(BlockScannerDoor.OPEN, !upperState.getValue(BlockScannerDoor.OPEN).booleanValue()), 3);
				worldObj.setBlockState(pos.down(), lowerState.withProperty(BlockScannerDoor.OPEN, !lowerState.getValue(BlockScannerDoor.OPEN).booleanValue()), 3);
				worldObj.markBlockRangeForRenderUpdate(pos.down(), pos);
				worldObj.playEvent((EntityPlayer)null, 1006, pos, 0);

				if(open) 
					PlayerUtils.sendMessageToPlayer((EntityPlayer) entity, I18n.translateToLocal("item.scannerDoorItem.name"), I18n.translateToLocal("messages.retinalScanner.hello").replace("#", entity.getName()), TextFormatting.GREEN);
			}
		}
	}

	public int getViewCooldown()
	{
		return 30;
	}

	public EnumCustomModules[] acceptedModules()
	{
		return new EnumCustomModules[]{};
	}

	public Option<?>[] customOptions()
	{
		return new Option[]{};
	}
}