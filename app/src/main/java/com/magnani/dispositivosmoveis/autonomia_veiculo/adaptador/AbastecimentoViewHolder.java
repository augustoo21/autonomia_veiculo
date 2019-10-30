package com.magnani.dispositivosmoveis.autonomia_veiculo.adaptador;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.magnani.dispositivosmoveis.autonomia_veiculo.R;
import com.magnani.dispositivosmoveis.autonomia_veiculo.lista_abastecimento;
import com.magnani.dispositivosmoveis.autonomia_veiculo.modelo.Abastecimento;

import java.text.DateFormat;

public class AbastecimentoViewHolder extends RecyclerView.ViewHolder {
    private TextView data;
    private TextView info;
    private ImageView img;
    private ConstraintLayout clPai;
    private String idDoAbastecimento;


    public AbastecimentoViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((lista_abastecimento) v.getContext()).modificarAbastecimento(v, idDoAbastecimento);
            }
        });
        data = itemView.findViewById(R.id.textData);
        info = itemView.findViewById(R.id.textInfo);
        img = itemView.findViewById(R.id.imageView);
        clPai = (ConstraintLayout) itemView;
    }

    public void atualizaGavetaComOObjetoQueChegou(Abastecimento abastecimento){
        //armazenando a posição do objeto na lista, para usar caso o método modificarAbastecimento seja chamado
        idDoAbastecimento = abastecimento.getId();
        data.setText("24/09/1998");
        DateFormat formatador = android.text.format.DateFormat.getDateFormat(data.getContext());
        String dataFormatada = formatador.format(abastecimento.getData().getTime());
        data.setText(dataFormatada);
        String valorAbastecido = String.valueOf(abastecimento.getQtd_litro_abastecido());
        String valorKm = String.valueOf(abastecimento.getQuilometragem());
        SetIconePosto(abastecimento.getPosto());
        info.setText("Abastecidos: " + valorAbastecido + " litros | KM do Veículo: " + valorKm);

    }

    private void SetIconePosto(String posto){
        Log.d("Icone posto", posto);

        if(posto.equals("Ipiranga")){
            img.setImageResource(R.mipmap.ic_ipiranga);
        }else if(posto.equals("Texaco")){
            img.setImageResource(R.mipmap.ic_texaco);
        }else if(posto.equals("Petrobras")){
            img.setImageResource(R.mipmap.ic_petrobras);
        }else if(posto.equals("Shell")){
            img.setImageResource(R.mipmap.ic_shell);
        }else{
            img.setImageResource(R.mipmap.ic_outros);
        }



    }
}

