package com.zeher.orecraft.machine.tileentity;

import com.zeher.orecraft.core.handlers.recipe.MachineProcessorCleaningRecipes;
import com.zeher.orecraft.core.handlers.recipe.MachineProcessorRefiningRecipes;
import com.zeher.orecraft.machine.block.BlockPoweredOreProcessor;
import com.zeher.orecraft.machine.container.ContainerPoweredOreProcessor;
import com.zeher.orecraft.storage.util.StorageUtil;
import com.zeher.trzlib.api.TRZUtil;
import com.zeher.trzlib.api.value.TRZItemPowerValues;
import com.zeher.trzlib.api.value.TRZMachineValues;
import com.zeher.trzlib.machine.TRZIMachine;
import com.zeher.trzlib.machine.TRZTileEntityMachine;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityPoweredOreProcessor extends TRZTileEntityMachine implements ITickable, ISidedInventory, TRZIMachine, IFluidHandler, IInventory {
	
	private static final int[] SLOTS_TOP = new int[1];
	private static final int[] SLOTS_BOTTOM = { 2, 1 };
	private static final int[] SLOTS_SIDES = { 1 };
	private NonNullList<ItemStack> process_stacks = NonNullList.<ItemStack>withSize(11, ItemStack.EMPTY);
	
	private int fluid_capacity = 16000;
	
	public FluidTank tank = new FluidTank(this.fluid_capacity);
	private boolean needsUpdate = false;
	private int updateTimer = 0;
	
	public int fill_level;
	
	private int processBurnTime;
	private int currentItemBurnTime;
	private int processTime;
	private int totalProcessTime;
	private String processCustomName;
	
	public int stored;
	public int capacity = TRZMachineValues.energizedBasicStored(0);
	public int input_rate = TRZMachineValues.energizedInputRate();
	public int processSpeed = 80;
	
	public int mode = 0;

	public int getSizeInventory() {
		return this.process_stacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack itemstack : this.process_stacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.process_stacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.process_stacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.process_stacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.process_stacks.get(index);

		boolean flag = (!stack.isEmpty()) && (stack.isItemEqual(itemstack))&& (ItemStack.areItemStackTagsEqual(stack, itemstack));
		
		this.process_stacks.set(index, stack);
		
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
		
		if ((index == 0) && (!flag)) {
			this.totalProcessTime = getProcessTime(stack);
			this.processTime = 0;
			this.markDirty();
		}
	}

	public String getName() {
		return hasCustomName() ? this.processCustomName : "Powered Ore Processor";
	}

	public boolean hasCustomName() {
		return (this.processCustomName != null) && (!this.processCustomName.isEmpty());
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.processCustomName = p_145951_1_;
	}

	public static void registerFixesFurnace(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityPoweredOreProcessor.class, new String[] { "Items" }));
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.process_stacks = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.process_stacks);
		this.processBurnTime = compound.getInteger("BurnTime");
		this.processTime = compound.getInteger("ProcessTime");
		this.totalProcessTime = compound.getInteger("ProcessTimeTotal");
		this.stored = compound.getInteger("power");
		if (compound.hasKey("CustomName", 8)) {
			this.processCustomName = compound.getString("CustomName");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) this.processBurnTime);
		compound.setInteger("ProcessTime", (short) this.processTime);
		compound.setInteger("ProcessTimeTotal", (short) this.totalProcessTime);
		compound.setInteger("power", this.stored);
		ItemStackHelper.saveAllItems(compound, this.process_stacks);
		if (this.hasCustomName()) {
			compound.setString("CustomName", this.processCustomName);
		}
		return compound;
	}

	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(this.pos, 0, tag);
	}

	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound tag = pkt.getNbtCompound();
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isBurning() {
		return this.processBurnTime > 0;
	}

	public boolean isProcessing() {
		return this.processTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory) {
		return inventory.getField(0) > 0;
	}

	public void update() {
		boolean flag = !isBurning();
		boolean flag1 = false;
		processSpeed();
		powerSlot();
	
		if ((hasPower()) && (isBurning())) {
			this.stored -= (TRZMachineValues.energizedDrain() / 10);
			--this.processBurnTime;
		}
		
		if (!this.world.isRemote) {
			if ((isItemPower(this.process_stacks.get(1))) && (this.stored <= this.capacity - TRZItemPowerValues.getItemPower(this.process_stacks.get(1).getItem()))) {
				if (!this.process_stacks.get(1).isItemStackDamageable()) {
					this.stored += TRZItemPowerValues.getItemPower(this.process_stacks.get(1).getItem());

					flag1 = true;
					if (this.process_stacks.get(1) != null) {
						this.process_stacks.get(1).setCount(this.process_stacks.get(1).getCount());
						if (this.process_stacks.get(1).getCount() == 0) {
							this.process_stacks.set(1, this.process_stacks.get(1).getItem().getContainerItem(this.process_stacks.get(1)));
						}
					}
				} else if (this.process_stacks.get(1).getItemDamage() < this.process_stacks.get(1).getMaxDamage()) {
					this.stored += TRZItemPowerValues.getItemPower(this.process_stacks.get(1).getItem());
					this.process_stacks.set(1, new ItemStack(this.process_stacks.get(1).getItem(), this.process_stacks.get(1).getCount(), this.process_stacks.get(1).getItemDamage() + 1));
				}
			}
			
			if ((!isBurning()) && (hasPower()) && (canProcess())) {
				this.processBurnTime = this.processSpeed;
			}
			
			if (isBurning()) {
				flag1 = true;
			}
			
			if ((hasPower()) && (canProcess())) {
				this.processTime += 1;
				if (this.processTime == this.processSpeed) {
					this.processTime = 0;
					processItem();
					processSpeed();
					powerSlot();
					flag1 = true;
				}
			} else {
				this.processTime = 0;
			}
			
			if (flag == isBurning()) {
				flag1 = true;
				BlockPoweredOreProcessor.setState(!isBurning(), this.world, this.pos);
			}
			
			if (flag1) {
				this.markDirty();
			}
			
			if ((this.stored > this.capacity) && (((ItemStack) this.process_stacks.get(6)).getCount() == 0)) {
				this.stored = this.capacity;
			}
			
			if ((this.stored > this.capacity) && (((ItemStack) this.process_stacks.get(6)).getCount() == 1)) {
				this.stored = TRZMachineValues.energizedBasicStored(1);
			}
			
			if ((this.stored > this.capacity) && (((ItemStack) this.process_stacks.get(6)).getCount() == 2)) {
				this.stored = TRZMachineValues.energizedBasicStored(2);
			}
			
			if ((this.stored > this.capacity) && (((ItemStack) this.process_stacks.get(6)).getCount() == 3)) {
				this.stored = TRZMachineValues.energizedBasicStored(3);
			}
			
			if ((this.stored > this.capacity) && (((ItemStack) this.process_stacks.get(6)).getCount() == 4)) {
				this.stored = TRZMachineValues.energizedBasicStored(4);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public int getProcessProgressScaled(int par1) {
		return this.processTime * par1 / this.processSpeed;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentItemBurnTime == 0) {
			this.currentItemBurnTime = this.processSpeed;
		}
		return this.processBurnTime * par1 / this.currentItemBurnTime;
	}

	public int getPowerRemainingScaled(int par1) {
		return this.stored * par1 / this.capacity;
	}

	public boolean hasPower() {
		return this.stored > 0;
	}

	public void processSpeed() {
		int i = ((ItemStack) this.process_stacks.get(5)).getCount();
		if (i == 0) {
			this.processSpeed = 80;
		}
		if (i == 1) {
			this.processSpeed = 75;
		}
		if (i == 2) {
			this.processSpeed = 60;
		}
		if (i == 3) {
			this.processSpeed = 65;
		}
		if (i == 4) {
			this.processSpeed = 60;
		} else {
			this.processSpeed = 80;
		}
	}

	public void powerSlot() {
		int i = ((ItemStack) this.process_stacks.get(6)).getCount();
		if (i == 0) {
			this.capacity = TRZMachineValues.energizedBasicStored(0);
		} else if (i == 1) {
			this.capacity = TRZMachineValues.energizedBasicStored(1);
		} else if (i == 2) {
			this.capacity = TRZMachineValues.energizedBasicStored(2);
		} else if (i == 3) {
			this.capacity = TRZMachineValues.energizedBasicStored(3);
		} else if (i == 4) {
			this.capacity = TRZMachineValues.energizedBasicStored(4);
		} else {
			this.capacity = TRZMachineValues.energizedBasicStored(0);
		}
	}

	public int getProcessTime(ItemStack stack) {
		return this.processSpeed;
	}

	private boolean canProcess() {
		if (this.mode == 0) {
			if (((ItemStack) this.process_stacks.get(0)).isEmpty()) {
				return false;
			} else {
				ItemStack itemstack = MachineProcessorCleaningRecipes.instance().getCleaningResult((ItemStack) this.process_stacks.get(0));
				
				if (itemstack.isEmpty()) {
					return false;
				}
				
				ItemStack itemstack1 = (ItemStack) this.process_stacks.get(2);
				if (itemstack1.isEmpty()) {
					return true;
				}
				
				if (!itemstack1.isItemEqual(itemstack)) {
					return false;
				}
				
				int result = itemstack1.getCount() + itemstack.getCount();
				return (result <= this.getInventoryStackLimit()) && (result <= itemstack1.getMaxStackSize());
			}
		}
		
		if (this.mode == 1) {
			if (((ItemStack) this.process_stacks.get(0)).isEmpty()) {
				return false;
			}
			
			ItemStack itemstack = MachineProcessorRefiningRecipes.instance().getRefiningResult((ItemStack) this.process_stacks.get(0));
			if (itemstack.isEmpty()) {
				return false;
			}
			
			ItemStack itemstack1 = (ItemStack) this.process_stacks.get(2);
			if (itemstack1.isEmpty()) {
				return true;
			}
			
			if (!itemstack1.isItemEqual(itemstack)) {
				return false;
			}
			
			int result = itemstack1.getCount() + itemstack.getCount();
			return (result <= this.getInventoryStackLimit()) && (result <= itemstack1.getMaxStackSize());
		}
		
		return false;
	}

	public void processItem() {
		if ((this.mode == 0) && (canProcess())) {
			this.stored -= TRZUtil.machinePowerDrain();
			ItemStack itemstack = (ItemStack) this.process_stacks.get(0);
			ItemStack itemstack1 = MachineProcessorCleaningRecipes.instance().getCleaningResult(itemstack);
			ItemStack itemstack2 = (ItemStack) this.process_stacks.get(2);
			
			if (itemstack2.isEmpty()) {
				this.process_stacks.set(2, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}
			
			if ((itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE)) && (itemstack.getMetadata() == 1) && (!((ItemStack) this.process_stacks.get(1)).isEmpty()) && (((ItemStack) this.process_stacks.get(1)).getItem() == Items.BUCKET)) {
				this.process_stacks.set(1, new ItemStack(Items.WATER_BUCKET));
			}
			
			itemstack.grow(1);
		}
		if ((this.mode == 1) && (canProcess())) {
			this.stored -= TRZUtil.machinePowerDrain();
			ItemStack itemstack = (ItemStack) this.process_stacks.get(0);
			ItemStack itemstack1 = MachineProcessorRefiningRecipes.instance().getRefiningResult(itemstack);
			ItemStack itemstack2 = (ItemStack) this.process_stacks.get(2);
			
			if (itemstack2.isEmpty()) {
				this.process_stacks.set(2, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}
			
			if ((itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE)) && (itemstack.getMetadata() == 1) && (!((ItemStack) this.process_stacks.get(1)).isEmpty()) && (((ItemStack) this.process_stacks.get(1)).getItem() == Items.BUCKET)) {
				this.process_stacks.set(1, new ItemStack(Items.WATER_BUCKET));
			}
			
			itemstack.shrink(1);
		}
	}

	public static boolean isItemPower(ItemStack par0ItemStack) {
		return TRZItemPowerValues.getItemPower(par0ItemStack.getItem()) > 0;
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void openInventory(EntityPlayer player) {}

	public void closeInventory(EntityPlayer player) {}

	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.DOWN ? SLOTS_BOTTOM : side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
	}

	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if ((direction == EnumFacing.DOWN) && (index == 1)) {
			Item item = stack.getItem();
			
			if ((item != Items.WATER_BUCKET) && (item != Items.BUCKET)) {
				return false;
			}
		}
		return true;
	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerPoweredOreProcessor(playerInventory, this);
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return this.processBurnTime;
		case 1:
			return this.currentItemBurnTime;
		case 2:
			return this.processTime;
		case 3:
			return this.totalProcessTime;
		case 4:
			return this.stored;
		}
		return 0;
	}

	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.processBurnTime = value;
			break;
		case 1:
			this.currentItemBurnTime = value;
			break;
		case 2:
			this.processTime = value;
			break;
		case 3:
			this.totalProcessTime = value;
			break;
		case 4:
			this.stored = value;
		}
	}

	public int getFieldCount() {
		return 5;
	}

	public void clear() {
		this.process_stacks.clear();
	}

	IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
	IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
	IItemHandler handlerSide = new SidedInvWrapper(this, EnumFacing.WEST);

	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if ((facing != null) && (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
			if (facing == EnumFacing.DOWN) {
				return (T) this.handlerBottom;
			}
			
			if (facing == EnumFacing.UP) {
				return (T) this.handlerTop;
			}
			return (T) this.handlerSide;
		}
		return (T) super.getCapability(capability, facing);
	}

	public String getGuiID() {
		return null;
	}

	public int getStored() {
		return this.stored;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getInputRate() {
		return this.input_rate;
	}

	public boolean hasStored() {
		return this.stored > 0;
	}

	public void addStored(int add) {
		this.stored += add;
	}

	public void checkFluidInputSlot() {
		if (!((ItemStack) this.process_stacks.get(7)).isEmpty()) {
			if ((this.process_stacks.get(7).getItem().equals(Items.BUCKET)) && (this.tank.getFluidAmount() > 0)) {
				ItemStack fillStack = FluidUtil.tryFillContainer((ItemStack) this.process_stacks.get(7), this.tank, 1000, null, false).result;
				if ((this.process_stacks.get(8).isEmpty()) && (fillStack != null)) {
					this.tank.drain(FluidUtil.getFluidContained(fillStack).amount, true);
					this.process_stacks.get(7).shrink(1);
					this.process_stacks.set(8, fillStack);
					StorageUtil.syncBlockAndRerender(this.world, this.pos);
				}
			}
			FluidStack fluid = FluidUtil.getFluidContained((ItemStack) this.process_stacks.get(7));
			if (fluid != null) {
				int amount = this.tank.fill(fluid, false);
				if (amount == fluid.amount) {
					if ((((ItemStack) this.process_stacks.get(8)).getItem().equals(Items.BUCKET)) && (((ItemStack) this.process_stacks.get(8)).getCount() < 16) && (fluid.getFluid().getTemperature() > 1000)) {
						this.tank.fill(fluid, true);
						this.process_stacks.get(7).shrink(1);
						this.process_stacks.get(8).grow(1);
						StorageUtil.syncBlockAndRerender(this.world, this.pos);
					}
					if ((this.process_stacks.get(8).isEmpty()) && (fluid.getFluid().getTemperature() > 1000)) {
						this.tank.fill(fluid, true);
						this.process_stacks.get(7).shrink(1);
						this.process_stacks.set(8, new ItemStack(Items.BUCKET));
						StorageUtil.syncBlockAndRerender(this.world, this.pos);
					}
				}
			}
		}
		this.markDirty();
	}

	public void checkFluidCapacity() {
		int i = ((ItemStack) this.process_stacks.get(5)).getCount();
		if (i == 0) {
			this.tank.setCapacity(this.fluid_capacity);
		} else if (i == 1) {
			this.tank.setCapacity(this.fluid_capacity + 4000);
		}
	}

	public int getFluidLevelScaled(int one) {
		return this.tank.getFluidAmount() * one / this.tank.getCapacity();
	}

	public int getFillLevel() {
		return this.fill_level;
	}

	public void updateFillLevel() {
		this.fill_level = (getCurrentStoredAmount() / 1000);
	}

	public Fluid getCurrentStoredFluid() {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(this.world, this.pos);
		if (!isFluidEmpty()) {
			return this.tank.getFluid().getFluid();
		}
		return null;
	}

	public boolean isFluidEmpty() {
		return this.tank.getFluidAmount() == 0;
	}

	public int getCurrentStoredAmount() {
		StorageUtil.syncBlockAndRerender(this.world, this.pos);
		return this.tank.getFluidAmount();
	}

	public int fill(FluidStack resource, boolean doFill) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(this.world, this.pos);
		this.needsUpdate = true;
		this.markDirty();
		return this.tank.fill(resource, doFill);
	}

	public FluidStack drain(FluidStack resource, boolean doDrain) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(this.world, this.pos);
		this.markDirty();
		return this.tank.drain(resource.amount, doDrain);
	}

	public FluidStack drain(int maxDrain, boolean doDrain) {
		updateFillLevel();
		StorageUtil.syncBlockAndRerender(this.world, this.pos);
		this.needsUpdate = true;
		this.markDirty();
		return this.tank.drain(maxDrain, doDrain);
	}

	public boolean canFill(EnumFacing from, Fluid fluid) {
		return true;
	}

	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return true;
	}

	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		StorageUtil.syncBlockAndRerender(this.world, this.pos);
		return new FluidTankInfo[] { this.tank.getInfo() };
	}

	public IFluidTankProperties[] getTankProperties() {
		return this.tank.getTankProperties();
	}

	public FluidTank getTank() {
		return this.tank;
	}

	public int getMode() {
		return this.mode;
	}

	public void setMode(int set) {
		this.mode = set;
	}

	public void cycleMode() {
		if (this.mode < 2) {
			this.mode += 1;
		} else {
			this.mode = 0;
		}
	}
}
