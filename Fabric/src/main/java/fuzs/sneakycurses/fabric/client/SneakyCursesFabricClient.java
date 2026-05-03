package fuzs.sneakycurses.fabric.client;

import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.sneakycurses.common.SneakyCurses;
import fuzs.sneakycurses.common.client.SneakyCursesClient;
import net.fabricmc.api.ClientModInitializer;

public class SneakyCursesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(SneakyCurses.MOD_ID, SneakyCursesClient::new);
    }
}
