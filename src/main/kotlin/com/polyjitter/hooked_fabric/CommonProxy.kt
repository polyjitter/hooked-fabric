package thecodewarrior.hooked.common

import com.teamwizardry.librarianlib.features.network.PacketHandler
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.relauncher.Side
import thecodewarrior.hooked.common.items.ModItems
import thecodewarrior.hooked.common.network.*
package thecodewarrior.hooked.common

import com.teamwizardry.librarianlib.features.network.PacketHandler
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.relauncher.Side
import thecodewarrior.hooked.common.items.ModItems
import thecodewarrior.hooked.common.network.*

// Probably can use this with slight modifications for paper.

/**
 * Created by TheCodeWarrior
 * Thanks for all your hard work! ~ taciturasa/polyjitter
 */
open class CommonProxy {
    open fun pre(e: FMLPreInitializationEvent) {
        ModItems
        network()
        HookTickHandler
    }

    open fun init(e: FMLInitializationEvent) {
    }

    open fun post(e: FMLPostInitializationEvent) {
    }

    fun network() {
        // So these are basically all the events the hooks have, right?
        PacketHandler.register(PacketFireHook::class.java, Side.SERVER)
        // Right, you can retract one or two hooks at a time so they may have to be handled differently.
        PacketHandler.register(PacketRetractHook::class.java, Side.SERVER)
        PacketHandler.register(PacketRetractHooks::class.java, Side.SERVER)
        // What's a weight in this context? Weight of the player for momentum?
        PacketHandler.register(PacketUpdateWeights::class.java, Side.SERVER)

        // And this is what keeps everything in sync.
        PacketHandler.register(PacketHookCapSync::class.java, Side.CLIENT)
    }

    // ..huh?
    open fun setAutoJump(entityLiving: EntityLivingBase, value: Boolean) {
    }
}