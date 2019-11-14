package polyjitter.hooked_fabric.common.items

/**
 * Created by TheCodeWarrior
 * Thanks for all your hard work! ~ taciturasa/polyjitter
 */
class ItemHook : Item {

    override fun use(world : World, playerEntity : PlayerEntity, hand : Hand) : TypedActionResult<ItemStack> {
        if(playerEntity.isSneaking) {
            val type = getType(playerEntity.getHeldItem(hand))
            if(type?.count ?: 0 > 1) {
                val item = playerEntity.getHeldItem(hand).copy()
                if(isInhibited(item)) {
                    item.nbt["inhibited"] = null
                } else {
                    item.nbt["inhibited"] = NBTTagByte(1.toByte())
                }
                return TypedActionResult(ActionResult.SUCCESS, item)
            }
        }
        return super.onItemRightClick(worldIn, playerEntity, hand)
    }

    override fun addTooltip(itemStack : ItemStack, world : World, tooltip : List<Text>, tooltipContext : TooltipContext) {
        tooltip.add(TranslatableText("item.hooked_fabric.hook.general_help"))
    }

    companion object {
        fun isInhibited(stack: ItemStack): Boolean {
            return (stack.nbt["inhibited"] as? NBTTagByte)?.byte == 1.toByte()
        }

        fun getType(stack: ItemStack?): HookType? {
            if(stack == null || stack.item != ModItems.hook) return null
            return HookType.values()[stack.itemDamage % HookType.values().size]
        }

        fun getItem(player: EntityPlayer): ItemStack? {
            val stacks = mutableListOf<ItemStack>()
            if(HookedConfig.searchLocations and HookedConfig.SEARCH_BAUBLES != 0) {
                stacks.addAll(baubles(player))
            }
            if(HookedConfig.searchLocations and HookedConfig.SEARCH_HANDS != 0) {
                stacks.add(player.heldItemMainhand)
                stacks.add(player.heldItemOffhand)
            }
            if(HookedConfig.searchLocations and HookedConfig.SEARCH_HOTBAR != 0) {
                stacks.addAll(hotbar.map { player.inventory.getStackInSlot(it) })
            }
            if(HookedConfig.searchLocations and HookedConfig.SEARCH_INVENTORY != 0) {
                stacks.addAll(main.map { player.inventory.getStackInSlot(it) })
            }
            return stacks.find { it.item == ModItems.hook }
        }
}