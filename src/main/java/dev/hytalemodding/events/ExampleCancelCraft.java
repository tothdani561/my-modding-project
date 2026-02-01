package dev.hytalemodding.events;

import com.hypixel.hytale.component.Archetype;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
import com.hypixel.hytale.server.core.event.events.ecs.CraftRecipeEvent;
import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Example event system that listens to crafting events and cancels the craft
 * when a specific ingredient is present in the recipe input.
 *
 * Purpose:
 * - Demonstrates how to extend EntityEventSystem to react to
 *   CraftRecipeEvent.Pre events (fired before a crafting recipe is completed).
 * - Shows a simple use-case: block crafting if the recipe contains a
 *   banned ingredient (here: "Ingredient_Fibre").
 *
 * Notes:
 * - This class does not filter entities via an Archetype query (getQuery returns
 *   an empty archetype). If you need to restrict processing to certain entities
 *   or components, refine the query returned by getQuery().
 */
public class ExampleCancelCraft extends EntityEventSystem<EntityStore, CraftRecipeEvent.Pre> {

    public ExampleCancelCraft() {
        super(CraftRecipeEvent.Pre.class);
    }

    /**
     * Handle method called for each matching event.
     *
     * Parameters:
     * - i: internal chunk index used by the ECS (not used here).
     * - archetypeChunk: the chunk of entities this call is processing (not used here).
     * - store: the EntityStore instance for accessing entity data (not used here).
     * - commandBuffer: a buffer to enqueue commands that should run later (not used here).
     * - pre: the CraftRecipeEvent.Pre instance; provides access to the crafted recipe
     *        and allows cancelling the event.
     *
     * Behavior:
     * - Retrieves the crafted recipe from the event.
     * - Iterates the recipe input list and checks each MaterialQuantity.
     * - If an input item has itemId "Ingredient_Fibre", the event is cancelled
     *   (pre.setCancelled(true)) so the craft will not complete.
     */
    @Override
    public void handle(int i, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull CraftRecipeEvent.Pre pre) {
        // Get the recipe being crafted
        CraftingRecipe recipe = pre.getCraftedRecipe();

        // Defensive check - some recipes may not have an input list
        if (recipe.getInput() != null) {
            // Iterate inputs and look for the banned ingredient
            for (MaterialQuantity mq : recipe.getInput()) {
                // Compare item ids safely using Objects.equals
                if (Objects.equals(mq.getItemId(), "Ingredient_Fibre")) {
                    pre.setCancelled(true);
                    break;
                }
            }
        }
    }

    /**
     * Provide an Archetype/Query to filter which entities this system should run for.
     *
     * Return value:
     * - Currently returns Archetype.empty(), which means no specific component-based
     *   filtering is applied. The system will receive events globally.
     * - If you want to limit processing to entities that have certain components,
     *   return a Query constructed for that archetype.
     */
    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return Archetype.empty();
    }
}
