package com.hugman.dawn.mod.mixin;

import com.hugman.dawn.api.util.EnchantmentUtil;
import com.hugman.dawn.mod.init.DawnEffects;
import com.hugman.dawn.mod.init.DawnEnchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(method = "getArmor", at = @At("HEAD"), cancellable = true)
	private void dawn_getArmor(CallbackInfoReturnable<Integer> info) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if(entity.hasStatusEffect(DawnEffects.GUARD)) {
			double d = entity.getAttributeValue(EntityAttributes.GENERIC_ARMOR);
			info.setReturnValue(MathHelper.floor(d + d * 0.15 * entity.getStatusEffect(DawnEffects.GUARD).getAmplifier()));
		}
	}

	@Inject(method = "jump", at = @At("TAIL"), cancellable = true)
	private void dawn_jump(CallbackInfo info) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if(entity.hasStatusEffect(DawnEffects.HEAVINESS)) {
			Vec3d vec3d = entity.getVelocity();
			entity.setVelocity(vec3d.x, vec3d.y - (float) (entity.getStatusEffect(DawnEffects.HEAVINESS).getAmplifier() + 1) * 0.05F, vec3d.z);
		}
	}

	@Inject(method = "dropLoot", at = @At(value = "HEAD"), cancellable = true)
	private void dawn_dropLoot(DamageSource source, boolean causedByPlayer, CallbackInfo info) {
		LivingEntity entity = (LivingEntity) (Object) this;
		World world = entity.getEntityWorld();
		if(causedByPlayer && source.getAttacker() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) source.getAttacker();
			LootTable lootTable = world.getServer().getLootManager().getTable(entity.getLootTable());
			LootContext.Builder builder = this.getLootContextBuilder(true, source);
			if(EnchantmentUtil.hasEnchantment(DawnEnchantments.TELEKINESIS, player.getMainHandStack())) {
				for(ItemStack stack : lootTable.generateLoot(builder.build(LootContextTypes.ENTITY))) {
					player.getInventory().insertStack(stack);
				}
				info.cancel();
			}
		}
	}

	@Inject(method = "dropXp", at = @At(value = "HEAD"), cancellable = true)
	private void dawn_dropXp(CallbackInfo info) {
		LivingEntity entity = (LivingEntity) (Object) this;
		LivingEntity attacker = entity.getAttacker();
		if(attacker instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) attacker;
			if(!player.getMainHandStack().isEmpty()) {
				if(EnchantmentUtil.hasEnchantment(DawnEnchantments.TELEKINESIS, player.getMainHandStack())) {
					player.addExperience(this.getCurrentExperience(player));
					info.cancel();
				}
			}
		}
	}

	@Shadow
	protected abstract int getCurrentExperience(PlayerEntity player);

	@Shadow
	protected abstract LootContext.Builder getLootContextBuilder(boolean causedByPlayer, DamageSource source);
}
