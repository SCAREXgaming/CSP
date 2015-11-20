package fr.scarex.csp.tileentity;

import fr.scarex.csp.CSPConfiguration;
import fr.scarex.csp.item.ISolarConvertable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.Constants;

public class TileEntitySolarCellConverter extends AbstractCSPTileEntity implements IInventory, ISidedInventory
{
    public String customName;
    public ItemStack convertable;

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote && CSPConfiguration.allowSolarConversion && this.canConvert() && this.convertable != null) {
            ((ISolarConvertable) this.convertable.getItem()).update(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord, this.convertable);
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public boolean canConvert() {
        return this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord) && Math.round(15F * MathHelper.cos(this.worldObj.getCelestialAngleRadians(1.0F))) >= 6 && !this.worldObj.provider.hasNoSky;
    }

    @Override
    public void readFromNBT(NBTTagCompound comp) {
        if (comp.hasKey("CustomName", Constants.NBT.TAG_STRING)) this.customName = comp.getString("CustomName");
        if (comp.hasKey("Convertable", Constants.NBT.TAG_COMPOUND)) this.convertable = ItemStack.loadItemStackFromNBT(comp.getCompoundTag("Convertable"));
        super.readFromNBT(comp);
    }

    @Override
    public void writeToNBT(NBTTagCompound comp) {
        super.writeToNBT(comp);
        if (this.hasCustomInventoryName()) comp.setString("CustomName", this.customName);
        NBTTagCompound stackComp = new NBTTagCompound();
        if (this.convertable != null) this.convertable.writeToNBT(stackComp);
        comp.setTag("Convertable", stackComp);
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
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "tile.SolarPanelFrame";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index == 0 ? this.convertable : null;
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (index == 0 && this.convertable != null) {
            ItemStack stack;
            if (this.convertable.stackSize <= amount) {
                stack = this.convertable;
                this.setInventorySlotContents(index, null);
                this.markDirty();
            } else {
                stack = this.convertable.splitStack(amount);
                this.markDirty();
            }
            return stack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        ItemStack stack = this.convertable;
        this.setInventorySlotContents(0, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index == 0) this.convertable = stack;
        this.markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public int getInventoryStackLimit() {
        return this.convertable != null ? ((ISolarConvertable) this.convertable.getItem()).getMaxStackSize(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord, this.convertable) : 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return stack.getItem() instanceof ISolarConvertable;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 1 ? null : new int[] {
                0 };
    }

    @Override
    public boolean canInsertItem(int side, ItemStack stack, int index) {
        return this.convertable != null ? ((ISolarConvertable) this.convertable.getItem()).canAddItem(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord, this.convertable, stack) : true;
    }

    @Override
    public boolean canExtractItem(int side, ItemStack stack, int index) {
        return this.canExtractItem(side, stack, index, false);
    }

    public boolean canExtractItem(int side, ItemStack stack, int index, boolean player) {
        return this.convertable != null ? ((ISolarConvertable) this.convertable.getItem()).canExtractItem(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord, this.convertable, player) : false;
    }

    public int canInputItem(EntityPlayer player, int side, ItemStack stack) {
        if (stack.getItem() instanceof ISolarConvertable && this.canInsertItem(side, stack, 0)) {
            if (this.convertable == null) {
                this.setInventorySlotContents(0, stack);
                return stack.stackSize;
            }
            if (this.convertable.getItem() == stack.getItem() && (!stack.getHasSubtypes() || this.convertable.getItemDamage() == stack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.convertable, stack)) {
                int l = this.convertable.stackSize + stack.stackSize;

                if (l <= stack.getMaxStackSize()) {
                    this.convertable.stackSize = l;
                    this.markDirty();
                    return stack.stackSize;
                } else if (this.convertable.stackSize < stack.getMaxStackSize()) {
                    stack.stackSize -= stack.getMaxStackSize() - this.convertable.stackSize;
                    this.convertable.stackSize = stack.getMaxStackSize();
                    this.markDirty();
                    return stack.getMaxStackSize() - stack.stackSize;
                }
            }
        }
        return 0;
    }
}
