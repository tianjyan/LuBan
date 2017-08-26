package org.tianjyan.luban.infrastructure.client;

import org.tianjyan.luban.infrastructure.abs.IClient;
import org.tianjyan.luban.infrastructure.abs.IClientManager;
import org.tianjyan.luban.infrastructure.abs.ILBApp;
import org.tianjyan.luban.infrastructure.common.consts.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ClientModule {
    @Provides
    @Named(AliasName.CLIENT_MANAGER)
    @Singleton
    public static IClientManager provideClientManager(@Named(AliasName.DEFAULT_CLIENT)IClient client) {
        return new ClientManager(client);
    }

    @Provides
    @Named(AliasName.DEFAULT_CLIENT)
    @Singleton
    public static IClient provideDefaultClient(ILBApp app) {
        return new DefaultClient(app);
    }
}
