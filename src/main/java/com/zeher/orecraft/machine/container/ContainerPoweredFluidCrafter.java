package com.zeher.orecraft.machine.container;

import com.zeher.orecraft.core.handlers.ItemHandler;
import com.zeher.trzlib.client.slot.TRZSlotBucket;
import com.zeher.trzlib.client.slot.TRZSlotMachineUpgrade;
import com.zeher.trzlib.client.slot.TRZSlotNoAccess;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPoweredFluidCrafter extends Container {
	
	private IInventory inventory;
	private int processTime;
	private int totalCookTime;
	private int processBurnTime;
	private int currentItemBurnTime;

	private int power;

	public ContainerPoweredFluidCrafter(InventoryPlayer invPlayer, IInventory tile) {
		this.inventory = tile;
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 148));
		}
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 90 + y * 18));
			}
		}
		
		addSlotToContainer(new Slot(tile, 0, 57, 36));
		addSlotToContainer(new Slot(tile, 1, 57, 58));
		
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 3, 8, 14, ItemHandler.upgrade_speed));
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 4, 8, 36, ItemHandler.upgrade_capacity));
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 5, 8, 58, ItemHandler.upgrade_charge));
		
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 6, 152, 14, ItemHandler.upgrade_fluid_speed));
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 7, 152, 36, ItemHandler.upgrade_fluid_capacity));
		addSlotToContainer(new TRZSlotMachineUpgrade(tile, 8, 152, 58, ItemHandler.upgrade_fluid_efficiency));
		
		addSlotToContainer(new TRZSlotBucket(tile, 9, 108, 14));
		addSlotToContainer(new TRZSlotNoAccess(tile, 10, 108, 58, 16));
	}

	@Override
	public void addListener(IContainerListener containerListener) {
		super.addListener(containerListener);
		containerListener.sendAllWindowProperties(this, this.inventory);
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.listeners.size(); i++) {
			IContainerListener containerlistener = (IContainerListener) this.listeners.get(i);

			if (this.power != this.inventory.getField(4)) {
				containerlistener.sendProgressBarUpdate(this, 4, this.inventory.getField(4));
			}
			if (this.processTime != this.inventory.getField(2)) {
				containerlistener.sendProgressBarUpdate(this, 2, this.inventory.getField(2));
			}

			if (this.processBurnTime != this.inventory.getField(0)) {
				containerlistener.sendProgressBarUpdate(this, 0, this.inventory.getField(0));
			}

			if (this.currentItemBurnTime != this.inventory.getField(1)) {
				containerlistener.sendProgressBarUpdate(this, 1, this.inventory.getField(1));
			}

			if (this.totalCookTime != this.inventory.getField(3)) {
				containerlistener.sendProgressBarUpdate(this, 3, this.inventory.getField(3));
			}
		}
		this.power = this.inventory.getField(0);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		this.inventory.setField(id, value);
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.inventory.isUsableByPlayer(entityplayer);
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 1 && index != 0) {
				if (index >= 3 && index < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}
}
