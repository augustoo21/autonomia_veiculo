package com.magnani.dispositivosmoveis.autonomia_veiculo.modelo;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Abastecimento extends RealmObject {
    @PrimaryKey
    private String id;
    private String descricao;
    private double quilometragem;
    private double qtd_litro_abastecido;
    private Date dataPura;

    @Ignore
    private Calendar data;

    public Abastecimento() {
        id = UUID.randomUUID().toString();
    }

    public String getId(){
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getQuilometragem(){
        return quilometragem;
    }

    public double getQtd_litro_abastecido(){
        return qtd_litro_abastecido;
    }

    public void setQuilometragem(double quilometragem){
        this.quilometragem = quilometragem;
    }

    public void setQtd_litro_abastecido(double qtd_litro_abastecido){
        this.qtd_litro_abastecido = qtd_litro_abastecido;
    }

    public Calendar getData() {
        if (dataPura != null){
            //gambiarra para manter um atributo do tipo date suportado pelo realm
            data = Calendar.getInstance();
            data.setTime(dataPura);
        }
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
        //gambiarra para manter um atributo do tipo date suportado pelo realm
        this.dataPura = data.getTime();
    }
}
