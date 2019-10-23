package com.magnani.dispositivosmoveis.autonomia_veiculo.modelo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class AbastecimentoDao {
    private ArrayList<Abastecimento> bancoDeDados;

    public ArrayList<Abastecimento> obterLista(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults lista = realm.where(Abastecimento.class).findAll();
        bancoDeDados.clear();
        bancoDeDados.addAll(realm.copyFromRealm(lista));
        return bancoDeDados;
    }

    public void adicionarNaLista(Abastecimento abastecimento){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(abastecimento);
        realm.commitTransaction();
    }

    public int atualizaNaLista(Abastecimento abastecimento){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(abastecimento);
        realm.commitTransaction();

        for(int i = 0; i < bancoDeDados.size(); i++){
            if(bancoDeDados.get(i).getId().equals(abastecimento.getId())){
                bancoDeDados.set(i, abastecimento);
                return i;
            }
        }
        return -1; //
    }

    public int excluiDaLista(Abastecimento abastecimento){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Abastecimento.class).equalTo("id", abastecimento.getId()).findFirst().deleteFromRealm();
        realm.commitTransaction();

        for(int i = 0; i < bancoDeDados.size(); i++){
            if(bancoDeDados.get(i).getId().equals(abastecimento.getId())){
                bancoDeDados.remove(i);
                return i;
            }
        }
        return -1;
    }

    public Abastecimento obterObjetoPeloId(String id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Abastecimento abastecimento = realm.copyFromRealm(realm.where(Abastecimento.class).equalTo("id", id).findFirst());
        realm.commitTransaction();
        return abastecimento;
    }

    private static AbastecimentoDao INSTANCIA;

    public static AbastecimentoDao obterInstancia(){
        if (INSTANCIA == null){
            INSTANCIA = new AbastecimentoDao();
        }
        return INSTANCIA;
    }

    private AbastecimentoDao(){
        bancoDeDados = new ArrayList<Abastecimento>();
    }
}
