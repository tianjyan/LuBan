package org.tianjyan.luban.host;

import org.tianjyan.luban.plugin.ip.InParaModule;
import org.tianjyan.luban.plugin.op.OutParaModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        OutParaModule.class,
        InParaModule.class
})
public interface LBComponent {
}
