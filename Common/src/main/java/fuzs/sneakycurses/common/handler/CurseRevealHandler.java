package fuzs.sneakycurses.common.handler;

import fuzs.puzzleslib.common.api.event.v1.core.EventResult;
import fuzs.puzzleslib.common.api.event.v1.data.MutableInt;
import fuzs.puzzleslib.common.api.event.v1.data.MutableValue;
import fuzs.sneakycurses.common.SneakyCurses;
import fuzs.sneakycurses.common.config.ServerConfig;
import fuzs.sneakycurses.common.init.ModRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jspecify.annotations.Nullable;

public class CurseRevealHandler {
    public static final String KEY_ITEM_CURSES_REVEALED = SneakyCurses.id("curses_revealed")
            .toLanguageKey(Registries.elementsDirPath(Registries.ITEM));

    public static EventResult onCreateAnvilResult(Player player, ItemStack primaryItemStack, ItemStack secondaryItemStack, MutableValue<ItemStack> outputItemStack, @Nullable String itemName, MutableInt enchantmentLevelCost, MutableInt repairMaterialCost) {
        if (isAffected(primaryItemStack) && secondaryItemStack.is(ModRegistry.REVEALS_CURSES_ITEM_TAG)
                && !allCursesRevealed(primaryItemStack)) {
            ItemStack itemStack = primaryItemStack.copy();
            revealAllCurses(itemStack);
            outputItemStack.accept(itemStack);
            repairMaterialCost.accept(1);
            enchantmentLevelCost.accept(SneakyCurses.CONFIG.get(ServerConfig.class).revealCursesCost);
            return EventResult.ALLOW;
        } else {
            return EventResult.PASS;
        }
    }

    public static void onEndEntityTick(Entity entity) {
        if (!entity.level().isClientSide() && entity.tickCount % 1200 == 0
                && entity instanceof LivingEntity livingEntity) {
            if (!(entity instanceof Player player) || !player.getAbilities().invulnerable) {
                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    ItemStack itemStack = livingEntity.getItemBySlot(equipmentSlot);
                    if (livingEntity.getEquipmentSlotForItem(itemStack) == equipmentSlot && isItemStackCursed(itemStack)
                            && !allCursesRevealed(itemStack)) {
                        if (entity.getRandom().nextDouble()
                                < SneakyCurses.CONFIG.get(ServerConfig.class).curseRevealChance) {
                            revealAllCurses(itemStack);
                            entity.playSound(SoundEvents.ENCHANTMENT_TABLE_USE,
                                    1.0F,
                                    entity.getRandom().nextFloat() * 0.1F + 0.9F);
                            if (entity instanceof Player player) {
                                player.sendSystemMessage(Component.translatable(KEY_ITEM_CURSES_REVEALED,
                                        itemStack.getDisplayName()).withStyle(ChatFormatting.DARK_PURPLE));
                            }

                            break;
                        }
                    }
                }
            }
        }
    }

    public static void revealAllCurses(ItemStack itemStack) {
        if (isItemStackCursed(itemStack)) {
            itemStack.set(ModRegistry.REVEAL_CURSES_DATA_COMPONENT_TYPE.value(), Unit.INSTANCE);
        }
    }

    public static boolean allCursesRevealed(ItemStack itemStack) {
        return itemStack.has(ModRegistry.REVEAL_CURSES_DATA_COMPONENT_TYPE.value());
    }

    public static boolean isItemStackCursed(ItemStack itemStack) {
        return !itemStack.isEmpty() && isItemStackCursed(EnchantmentHelper.getEnchantmentsForCrafting(itemStack));
    }

    public static boolean isItemStackCursed(DataComponentMap components) {
        return isItemStackCursed(components.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
    }

    private static boolean isItemStackCursed(ItemEnchantments itemEnchantments) {
        return itemEnchantments.keySet()
                .stream()
                .anyMatch((Holder<Enchantment> holder) -> holder.is(EnchantmentTags.CURSE));
    }

    public static boolean isAffected(ItemStack itemStack) {
        if (itemStack.is(Items.ENCHANTED_BOOK) && !SneakyCurses.CONFIG.get(ServerConfig.class).affectBooks) {
            return false;
        } else {
            return isItemStackCursed(itemStack);
        }
    }
}
