package com.magnani.dispositivosmoveis.autonomia_veiculo.adaptador;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.magnani.dispositivosmoveis.autonomia_veiculo.R;
import com.magnani.dispositivosmoveis.autonomia_veiculo.modelo.Abastecimento;
import com.magnani.dispositivosmoveis.autonomia_veiculo.modelo.AbastecimentoDao;

public class AbastecimentoAdapter extends RecyclerView.Adapter{
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //1: inflar XML
        ConstraintLayout elementoPrincipalXML = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.abastecimento_item, parent, false
        );

        //2: associar os objetos inflados a um novo view holder
        AbastecimentoViewHolder gaveta = new AbastecimentoViewHolder(elementoPrincipalXML);


        return gaveta;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Abastecimento abastecimento = AbastecimentoDao.obterInstancia().obterLista().get(position);
        AbastecimentoViewHolder gaveta = (AbastecimentoViewHolder) holder;

        gaveta.atualizaGavetaComOObjetoQueChegou(abastecimento);
    }

    @Override
    public int getItemCount() {
        return AbastecimentoDao.obterInstancia().obterLista().size();
    }
}
