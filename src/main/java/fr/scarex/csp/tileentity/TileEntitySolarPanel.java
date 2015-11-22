package fr.scarex.csp.tileentity;

import java.util.List;

import fr.scarex.csp.CSP;
import fr.scarex.csp.item.CSPItems;
import fr.scarex.csp.item.ISolarCell;
import fr.scarex.csp.item.ISolarUpgrade;
import fr.scarex.csp.item.SolarCellSupport;
import fr.scarex.csp.item.SolarUpgrade;
import fr.scarex.csp.util.energy.CSPEnergyStorage;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySolarPanel extends AbstractTileEntityEnergy implements IInventory, ISidedInventory
{
    public static final int CAPACITY = 700;
    public static final int OUTPUT = 40;
    public final ItemStack[] content = new ItemStack[24];
    public static final int[] topSides = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15,
            16, 17, 18, 19, 20, 21, 22,
            23 };
    public String customName;
    public int numPlayersUsing = 0;
    public final int[] stackProducing = new int[16];
    public int totalProd = 0;
    public long lastWorldTime;
    public int timeLastProd = 1;

    public TileEntitySolarPanel() {
        super(CAPACITY, 0, OUTPUT);
    }

    @Override
    public int receiveEnergy(ForgeDirection arg0, int arg1, boolean arg2) {
        return 0;
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            long time = this.worldObj.getTotalWorldTime();
            boolean noSky = this.worldObj.provider.hasNoSky;
            boolean canSeeTheSky = this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord);
            int amount = this.getAmountOfUpgrade(CSPItems.itemMap.get(SolarUpgrade.class), 0);
            boolean baseGenerate = (amount >= 2 ? true : (amount == 1 ? time % 2 == 0 : time % 20 == 0)) && !noSky && canSeeTheSky;
            this.timeLastProd = (int) (this.worldObj.getTotalWorldTime() - this.lastWorldTime);
            this.lastWorldTime = this.worldObj.getTotalWorldTime();
            int i1 = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord) - this.worldObj.skylightSubtracted;
            float f = this.worldObj.getCelestialAngleRadians(1.0F);

            float ratio = Math.max(0, Math.round((float) i1 * MathHelper.cos(f)));
            int total = 0;
            for (byte i = 4; i < 20; i++) {
                if (this.getStackInSlot(i) == null && this.worldObj.getTotalWorldTime() % 20 == 0) this.stackProducing[i - 4] = 0;
                if (this.getStackInSlot(i) != null && ((ISolarCell) this.getStackInSlot(i).getItem()).canGenerate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getStackInSlot(i), baseGenerate, time, noSky, canSeeTheSky, amount)) {
                    int energy = 0;
                    if (this.getStackInSlot(i).getItem() instanceof ISolarCell) energy = ((ISolarCell) this.getStackInSlot(i).getItem()).amountToGenerate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getStackInSlot(i), f, i1, ratio);
                    for (byte j = 20; j < 24; j++) {
                        if (this.getStackInSlot(j) != null) energy = ((ISolarUpgrade) this.getStackInSlot(j).getItem()).generateWithCell(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getStackInSlot(j), this.getStackInSlot(i), f, i1, ratio, energy);
                    }
                    if (this.worldObj.getTotalWorldTime() % 20 == 0) this.stackProducing[i - 4] = energy;
                    total += energy;
                }
            }
            for (int i = 20; i < 24; i++) {
                if (this.getStackInSlot(i) != null) total = ((ISolarUpgrade) this.getStackInSlot(i).getItem()).generate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getStackInSlot(i), total);
            }
            this.totalProd = total;
            this.storage.modifyEnergyStored(total);
            this.transmit();

            if (this.worldObj.getTotalWorldTime() % 10 == 0) this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public int getAmountOfUpgrade(Item item, int metadata) {
        int amount = 0;
        for (byte i = 20; i < 24; i++) {
            if (this.getStackInSlot(i) != null && this.getStackInSlot(i).getItem() == item && this.getStackInSlot(i).getItemDamage() == metadata) amount += this.getStackInSlot(i).stackSize;
        }
        return amount;
    }

    @Override
    public void readFromNBT(NBTTagCompound comp) {
        this.storage.setCapacity(CAPACITY);
        this.storage.setMaxExtract(OUTPUT);
        if (comp.hasKey("CustomName", Constants.NBT.TAG_STRING)) this.customName = comp.getString("CustomName");

        NBTTagList nbtlist = comp.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbtlist.tagCount(); i++) {
            NBTTagCompound comp1 = nbtlist.getCompoundTagAt(i);
            int j = comp1.getByte("Slot");
            this.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(comp1));
        }

        for (byte i = 20; i < 24; i++) {
            if (this.getStackInSlot(i) != null) ((ISolarUpgrade) this.getStackInSlot(i).getItem()).onReadFromNBT(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this, this.getStackInSlot(i), comp);
        }
        super.readFromNBT(comp);
    }

    @Override
    public void writeToNBT(NBTTagCompound comp) {
        super.writeToNBT(comp);

        if (this.hasCustomInventoryName()) comp.setString("CustomName", this.customName);

        NBTTagList nbtlist = new NBTTagList();
        for (byte i = 0; i < this.getSizeInventory(); i++) {
            if (this.content[i] != null) {
                NBTTagCompound comp1 = new NBTTagCompound();
                comp1.setByte("Slot", i);
                this.content[i].writeToNBT(comp1);
                nbtlist.appendTag(comp1);
            }
        }
        comp.setTag("Inventory", nbtlist);

        for (byte i = 20; i < 24; i++) {
            if (this.getStackInSlot(i) != null) ((ISolarUpgrade) this.getStackInSlot(i).getItem()).onWriteToNBT(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this, this.getStackInSlot(i), comp);
        }
    }

    @Override
    public void writeExtraCompound(NBTTagCompound comp) {
        super.writeExtraCompound(comp);
        if (this.hasCustomInventoryName()) {
            if (comp.hasKey("display", Constants.NBT.TAG_COMPOUND)) {
                comp.getCompoundTag("display").setString("Name", this.customName);
            } else {
                NBTTagCompound comp1 = new NBTTagCompound();
                comp1.setString("Name", this.customName);
                comp.setTag("display", comp1);
            }
        }
    }

    @Override
    public void readExtraCompound(NBTTagCompound comp) {
        super.readExtraCompound(comp);
        if (comp.hasKey("display", Constants.NBT.TAG_COMPOUND) && comp.getCompoundTag("display").hasKey("Name", Constants.NBT.TAG_STRING)) this.customName = comp.getCompoundTag("display").getString("Name");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound comp = new NBTTagCompound();
        this.writeToNBT(comp);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, comp);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }

    @Override
    public int getSizeInventory() {
        return content.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return content[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (this.getStackInSlot(index) != null) {
            ItemStack stack;
            if (this.getStackInSlot(index).stackSize <= amount) {
                stack = this.getStackInSlot(index);
                this.setInventorySlotContents(index, null);
                this.markDirty();
            } else {
                stack = this.getStackInSlot(index).splitStack(amount);
                this.markDirty();
            }
            return stack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        ItemStack stack = this.getStackInSlot(index);
        this.setInventorySlotContents(index, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.content[index] = stack;
        this.markDirty();
        if (this.worldObj != null) this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.updateUpgrades();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "tile.SolarPanelFrame";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {
        if (this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
        }

        ++this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
    }

    @Override
    public void closeInventory() {
        --this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index >= 4 && index < 20 && stack.getItem() instanceof ISolarCell) { return this.getStackInSlot((int) ((index - index % 4F) / 4F) - 1) != null; }
        return (index < 4 && stack.getItem() instanceof SolarCellSupport) || (index >= 20 && index < 24 && stack.getItem() instanceof ISolarUpgrade);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 1 ? null : topSides;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return this.isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        if (slot < 4) {
            for (byte i = 0; i < 4; i++) {
                if (this.getStackInSlot(slot * 4 + i + 4) != null) return false;
            }
            return true;
        } else if (slot >= 4 && slot < 24) { return true; }
        return false;
    }

    @Override
    public boolean receiveClientEvent(int id, int value) {
        if (id == 1) {
            this.numPlayersUsing = value;
            return true;
        }
        return super.receiveClientEvent(id, value);
    }

    public CSPEnergyStorage getEnergyStorage() {
        return this.storage;
    }

    public void updateUpgrades() {
        this.storage.setCapacity(CAPACITY);
        this.storage.setMaxExtract(OUTPUT);
        for (byte i = 20; i < 24; i++) {
            if (this.getStackInSlot(i) != null) ((ISolarUpgrade) this.getStackInSlot(i).getItem()).load(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this, this.getStackInSlot(i));
        }
        this.storage.update();
    }

    public int getAmountProducedBy(int index) {
        return this.stackProducing[index];
    }

    @Override
    public NBTTagCompound getWailaNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        tag = super.getWailaNBTData(player, te, tag, world, x, y, z);
        tag.setIntArray("Production", this.stackProducing);
        tag.setInteger("TotalProduction", this.totalProd);
        tag.setInteger("TimeSinceLastProduction", this.timeLastProd);
        return tag;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        int prod = 0;
        int[] prodArray = accessor.getNBTData().getIntArray("Production");
        if (prodArray != null && prodArray.length > 0) {
            boolean detailed = accessor.getPlayer().isSneaking();
            if (!detailed) currenttip.add(EnumChatFormatting.WHITE + "" + EnumChatFormatting.ITALIC + StatCollector.translateToLocal("misc." + CSP.MODID + "_hold_shift"));
            for (byte i = 0; i < 4; i++) {
                if (prodArray[i * 4] > 0 || prodArray[i * 4 + 1] > 0 || prodArray[i * 4 + 2] > 0 || prodArray[i * 4 + 3] > 0) {
                    for (byte j = 0; j < 4; j++) {
                        prod += prodArray[i * 4 + j];
                    }
                    if (detailed) currenttip.add(EnumChatFormatting.WHITE + "" + (i + 1) + "-" + StatCollector.translateToLocalFormatted("tile.csp_SolarPanelFrame.currentlyProducing", (prodArray[i * 4] + prodArray[i * 4 + 1] + prodArray[i * 4 + 2] + prodArray[i * 4 + 3]) / accessor.getNBTData().getInteger("TimeSinceLastProduction")));
                }
            }
        }
        if (accessor.getNBTData().getInteger("TimeSinceLastProduction") != 0) currenttip.add(EnumChatFormatting.GREEN + StatCollector.translateToLocalFormatted("tile.csp_SolarPanelFrame.currentlyProducing", accessor.getNBTData().getInteger("TotalProduction") / accessor.getNBTData().getInteger("TimeSinceLastProduction")));
        return currenttip;
    }

    public int canInputItem(EntityPlayer player, ItemStack stack) {
        if (stack.getItem() instanceof SolarCellSupport) {
            for (byte i = 0; i < 4; i++) {
                if (this.getStackInSlot(i) == null) {
                    this.setInventorySlotContents(i, stack);
                    return stack.stackSize;
                }
            }
            return 0;
        } else if (stack.getItem() instanceof ISolarCell) {
            for (byte i = 0; i < 4; i++) {
                for (byte j = 0; j < 4; j++) {
                    if (this.getStackInSlot(i * 4 + j + 4) == null && this.getStackInSlot(i) != null) {
                        this.setInventorySlotContents(i * 4 + j + 4, stack);
                        return stack.stackSize;
                    }
                }
            }
            return 0;
        } else if (stack.getItem() instanceof ISolarUpgrade) {
            for (byte i = 20; i < 24; i++) {
                ItemStack invStack = this.getStackInSlot(i);
                if (invStack == null) {
                    this.setInventorySlotContents(i, stack);
                    return stack.stackSize;
                } else if (invStack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || invStack.getItemDamage() == stack.getItemDamage()) && ItemStack.areItemStackTagsEqual(invStack, stack)) {
                    int l = invStack.stackSize + stack.stackSize;

                    if (l <= stack.getMaxStackSize()) {
                        invStack.stackSize = l;
                        return stack.stackSize;
                    } else if (invStack.stackSize < stack.getMaxStackSize()) {
                        stack.stackSize -= stack.getMaxStackSize() - invStack.stackSize;
                        invStack.stackSize = stack.getMaxStackSize();
                        return stack.getMaxStackSize() - stack.stackSize;
                    }
                }
            }
            return 0;
        }
        return 0;
    }
}
