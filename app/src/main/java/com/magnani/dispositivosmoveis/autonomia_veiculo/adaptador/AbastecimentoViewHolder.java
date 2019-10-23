package com.magnani.dispositivosmoveis.autonomia_veiculo.adaptador;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.magnani.dispositivosmoveis.autonomia_veiculo.R;
import com.magnani.dispositivosmoveis.autonomia_veiculo.lista_abastecimento;
import com.magnani.dispositivosmoveis.autonomia_veiculo.modelo.Abastecimento;

import java.text.DateFormat;

public class AbastecimentoViewHolder extends RecyclerView.ViewHolder {
    private TextView tvDescricao;
    private TextView tvData;
    private TextView qtd_litro_abastecido;
    private TextView quilometragem;
    private ConstraintLayout clPai;
    private String idAbastecimento;


    public AbastecimentoViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((lista_abastecimento) v.getContext()).modificarAbastecimento(v, idAbastecimento);
            }
        });
        tvDescricao = itemView.findViewById(R.id.tvDescricao);
        tvData = itemView.findViewById(R.id.tvData);
        qtd_litro_abastecido = itemView.findViewById(R.id.qtd_litro_abastecido);
        quilometragem = itemView.findViewById(R.id.quilometragem);
        clPai = (ConstraintLayout) itemView;
    }

    public void atualizaGavetaComOObjetoQueChegou(Abastecimento abastecimento){
        //armazenando a posição do objeto na lista, para usar caso o método modificarAbastecimento seja chamado
        idAbastecimento = abastecimento.getId();
        tvDescricao.setText( abastecimento.getDescricao() );
        quilometragem.setText(String.valueOf(abastecimento.getQuilometragem()));
        qtd_litro_abastecido.setText(String.valueOf(abastecimento.getQtd_litro_abastecido()));
        DateFormat formatador = android.text.format.DateFormat.getDateFormat( tvDescricao.getContext() );
        String dataFormatada = formatador.format( abastecimento.getData().getTime() );
        tvData.setText( dataFormatada );

    }
}

