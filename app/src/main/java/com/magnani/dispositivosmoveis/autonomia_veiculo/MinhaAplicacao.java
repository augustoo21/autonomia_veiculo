package com.magnani.dispositivosmoveis.autonomia_veiculo;

import android.app.Application;

import io.realm.Realm;

public class MinhaAplicacao  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
//        Realm.deleteRealm(Realm.getDefaultConfiguration());
    }
}
