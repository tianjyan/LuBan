package org.tianjyan.luban.host.infrastructure.client;

import android.content.Context;

import org.tianjyan.luban.host.infrastructure.abs.IClient;
import org.tianjyan.luban.host.infrastructure.abs.IClientManager;
import org.tianjyan.luban.host.plugin.common.AliasName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
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
    public static IClient provideDefaultClient(@ApplicationContext Context context) {
        return new DefaultClient(context);
    }
}
