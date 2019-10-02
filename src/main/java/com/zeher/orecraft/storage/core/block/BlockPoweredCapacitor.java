package com.zeher.orecraft.storage.core.block;

import java.util.ArrayList;
import java.util.Random;

import com.zeher.orecraft.Orecraft;
import com.zeher.orecraft.storage.core.tileentity.TileEntityPoweredCapacitor;
import com.zeher.orecraft.storage.core.util.StorageUtil;
import com.zeher.zeherlib.api.connection.EnumSide;
import com.zeher.zeherlib.api.util.ModBlockPos;
import com.zeher.zeherlib.api.util.ModUtil;
import com.zeher.zeherlib.fluid.ModFluidUtil;
import com.zeher.zeherlib.storage.BlockStorage;
import com.zeher.zeherlib.transfer.BlockEnergyPipe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPoweredCapacitor extends BlockStorage {

	private final Random capacitor_random = new Random();
	private boolean keepCapacitorInventory;
	protected boolean needsRandomTick = true;

	public static final PropertyInteger NORTH = PropertyInteger.create("north", 0, 2);
	public static final PropertyInteger EAST = PropertyInteger.create("east", 0, 2);
	public static final PropertyInteger SOUTH = PropertyInteger.create("south", 0, 2);
	public static final PropertyInteger WEST = PropertyInteger.create("west", 0, 2);
	public static final PropertyInteger UP = PropertyInteger.create("up", 0, 2);
	public static final PropertyInteger DOWN = PropertyInteger.create("down", 0, 2);

	public BlockPoweredCapacitor(String name, Material material, String tool, int harvest, int hardness, int resistance, CreativeTabs tab) {
		super(tool, material, name, harvest, hardness, resistance, tab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, 0).withProperty(EAST, 0).withProperty(SOUTH, 0).withProperty(WEST, 0).withProperty(UP, 0).withProperty(DOWN, 0));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		Item item = Item.getItemFromBlock(this);
		ItemStack stack = new ItemStack(this, 1, 1000);
		return stack.getItem();
	}
	
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityPoweredCapacitor();
	}

	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (!(worldIn.isRemote) && !(playerIn.isSneaking()) && (ModUtil.isHoldingHammer(playerIn))) {
			FMLNetworkHandler.openGui(playerIn, Orecraft.INSTANCE, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		if (!worldIn.isRemote && playerIn.isSneaking()) {
			ArrayList<String> side_list = this.checkSides(pos, worldIn);
			String up = "null";
			String down = "null";
			String north = "null";
			String south = "null";
			String east = "null";
			String west = "null";

			for (int i = 0; i < side_list.size(); i++) {
				String check = side_list.get(i);
				if (check.contains("up_")) {
					up = check.substring(3);
				}
				if (check.contains("down_")) {
					down = check.substring(5);
				}
				if (check.contains("north_")) {
					north = check.substring(6);
				}
				if (check.contains("south_")) {
					south = check.substring(6);
				}
				if (check.contains("east_")) {
					east = check.substring(5);
				}
				if (check.contains("west_")) {
					west = check.substring(5);
				}
			}
			
			System.out.println("Up: " + up);
			System.out.println("Down: " + down);
			System.out.println("North: " + north);
			System.out.println("South: " + south);
			System.out.println("East: " + east);
			System.out.println("West: " + west);
		}
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		worldIn.setBlockState(pos, state);
		worldIn.notifyBlockUpdate(pos, state, state, 3);

		this.getActualState(state, worldIn, pos);
		if (!(worldIn.isRemote) && !(playerIn.isSneaking()) && !(ModUtil.isHoldingHammer(playerIn))) {
			FMLNetworkHandler.openGui(playerIn, Orecraft.INSTANCE, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		} else if ((ModUtil.isHoldingHammer(playerIn)) && (playerIn.isSneaking()) && !(worldIn.isRemote)) {
			playerIn.swingArm(EnumHand.MAIN_HAND);
			
			TileEntityPoweredCapacitor tile = (TileEntityPoweredCapacitor) worldIn.getTileEntity(pos);
			
			this.spawnAsEntity(worldIn, pos, StorageUtil.getCapacitorStackFromTile(tile.getSideArray(), this, tile.getStored()));
			
			worldIn.destroyBlock(pos, false);
			return false;
		}
		
		TileEntityPoweredCapacitor tile = (TileEntityPoweredCapacitor) worldIn.getTileEntity(pos);
		if ((ModUtil.isHoldingHammer(playerIn)) && (!playerIn.isSneaking())) {
			playerIn.swingArm(EnumHand.MAIN_HAND);
			tile.cycleside(side);
			
			StorageUtil.syncBlockAndRerender(worldIn, pos);
			
			return false;
		}
		return true;
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, WEST, SOUTH, UP, DOWN });
	}

	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntityPoweredCapacitor tile = (TileEntityPoweredCapacitor) worldIn.getTileEntity(pos);

		return state.withProperty(NORTH, tile.getSide(EnumFacing.NORTH).getIndex())
				.withProperty(EAST, tile.getSide(EnumFacing.EAST).getIndex())
				.withProperty(SOUTH, tile.getSide(EnumFacing.SOUTH).getIndex())
				.withProperty(WEST, tile.getSide(EnumFacing.WEST).getIndex())
				.withProperty(UP, tile.getSide(EnumFacing.UP).getIndex())
				.withProperty(DOWN, tile.getSide(EnumFacing.DOWN).getIndex());
	}

	public ArrayList<String> checkSides(BlockPos pos, World worldIn) {
		ArrayList<String> side_list = new ArrayList<String>();
		ModBlockPos posUp = new ModBlockPos(pos);
		posUp.orientation = EnumFacing.UP;
		posUp.moveForwards(1);
		Block blockUp = worldIn.getBlockState(posUp.pos).getBlock();
		if (blockUp instanceof BlockEnergyPipe || blockUp instanceof BlockStorage) {
			side_list.add("up_true");
		} else {
			side_list.add("up_false");
		}

		ModBlockPos posDown = new ModBlockPos(pos);
		posDown.orientation = EnumFacing.DOWN;
		posDown.moveForwards(1);
		Block blockDown = worldIn.getBlockState(posDown.pos).getBlock();
		if (blockDown instanceof BlockEnergyPipe || blockDown instanceof BlockStorage) {
			side_list.add("down_true");
		} else {
			side_list.add("down_false");
		}

		ModBlockPos posNorth = new ModBlockPos(pos);
		posNorth.orientation = EnumFacing.NORTH;
		posNorth.moveForwards(1);
		Block blockNorth = worldIn.getBlockState(posNorth.pos).getBlock();
		if (blockNorth instanceof BlockEnergyPipe || blockNorth instanceof BlockStorage) {
			side_list.add("north_true");
		} else {
			side_list.add("north_false");
		}

		ModBlockPos posSouth = new ModBlockPos(pos);
		posSouth.orientation = EnumFacing.SOUTH;
		posSouth.moveForwards(1);
		Block blockSouth = worldIn.getBlockState(posSouth.pos).getBlock();
		if (blockSouth instanceof BlockEnergyPipe || blockSouth instanceof BlockStorage) {
			side_list.add("south_true");
		} else {
			side_list.add("south_false");
		}

		ModBlockPos posEast = new ModBlockPos(pos);
		posEast.orientation = EnumFacing.EAST;
		posEast.moveForwards(1);
		Block blockEast = worldIn.getBlockState(posEast.pos).getBlock();
		if (blockEast instanceof BlockEnergyPipe || blockEast instanceof BlockStorage) {
			side_list.add("east_true");
		} else {
			side_list.add("east_false");
		}

		ModBlockPos posWest = new ModBlockPos(pos);
		posWest.orientation = EnumFacing.WEST;
		posWest.moveForwards(1);
		Block blockWest = worldIn.getBlockState(posWest.pos).getBlock();
		if (blockWest instanceof BlockEnergyPipe || blockWest instanceof BlockStorage) {
			side_list.add("west_true");
		} else {
			side_list.add("west_false");
		}
		return side_list;
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!keepCapacitorInventory) {
			TileEntityPoweredCapacitor tileentityenergystorage_1 = (TileEntityPoweredCapacitor) worldIn.getTileEntity(pos);
			if (tileentityenergystorage_1 != null) {
				for (int j1 = 0; j1 < tileentityenergystorage_1.getSizeInventory(); j1++) {
					ItemStack itemstack = tileentityenergystorage_1.getStackInSlot(j1);
					if (itemstack != null) {
						float f = this.capacitor_random.nextFloat() * 0.8F + 0.1F;
						float f1 = this.capacitor_random.nextFloat() * 0.8F + 0.1F;
						float f2 = this.capacitor_random.nextFloat() * 0.8F + 0.1F;
						while (itemstack.getCount() > 0) {
							int k1 = this.capacitor_random.nextInt(21) + 10;
							if (k1 > itemstack.getCount()) {
								k1 = itemstack.getCount();
							}
							itemstack.setCount(itemstack.getCount() - k1);
							EntityItem entityitem = new EntityItem(worldIn, pos.getX() + f, pos.getY() + f1,
									pos.getZ() + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
							if (itemstack.hasTagCompound()) {
								entityitem.getItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}
							float f3 = 0.05F;
							entityitem.posX = ((float) this.capacitor_random.nextGaussian() * f3);
							entityitem.posY = ((float) this.capacitor_random.nextGaussian() * f3 + 0.2F);
							entityitem.posZ = ((float) this.capacitor_random.nextGaussian() * f3);
							worldIn.spawnEntity(entityitem);
						}
					}
				}
			}
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return (layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.TRANSLUCENT);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
	
	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
}