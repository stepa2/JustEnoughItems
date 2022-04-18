package mezz.jei.forge;

import mezz.jei.api.constants.ModIds;
import mezz.jei.core.config.IServerConfig;
import mezz.jei.forge.events.PermanentEventSubscriptions;
import mezz.jei.forge.network.NetworkHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModIds.JEI_ID)
public class JustEnoughItems {
	public JustEnoughItems() {
		IEventBus eventBus = MinecraftForge.EVENT_BUS;
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		PermanentEventSubscriptions subscriptions = new PermanentEventSubscriptions(eventBus, modEventBus);

		NetworkHandler networkHandler = new NetworkHandler();
		JustEnoughItemsCommon jeiCommon = new JustEnoughItemsCommon(networkHandler);
		jeiCommon.register(subscriptions);

		IServerConfig serverConfig = jeiCommon.getServerConfig();
		JustEnoughItemsClient jeiClient = new JustEnoughItemsClient(networkHandler, subscriptions, serverConfig);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> jeiClient::register);
	}
}