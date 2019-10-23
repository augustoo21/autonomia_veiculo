package com.magnani.dispositivosmoveis.autonomia_veiculo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.magnani.dispositivosmoveis.autonomia_veiculo.R;
import com.magnani.dispositivosmoveis.autonomia_veiculo.adaptador.AbastecimentoAdapter;
import com.magnani.dispositivosmoveis.autonomia_veiculo.modelo.AbastecimentoDao;

import javax.annotation.Nullable;

public class lista_abastecimento extends AppCompatActivity {
    private AbastecimentoAdapter adaptador;
    private RecyclerView rvAbastecimentos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_abastecimento);

        rvAbastecimentos = findViewById(R.id.rv_abastecimento);

        adaptador = new AbastecimentoAdapter();
        rvAbastecimentos.setLayoutManager( new LinearLayoutManager(this));
        rvAbastecimentos.setAdapter(adaptador);

    }

    public void modificarAbastecimento(View v, String idDoAbastecimento){
        Intent intencao = new Intent( this, editar_abastecimento.class );
        intencao.putExtra("idAbastecimento", idDoAbastecimento);
        startActivityForResult(intencao, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            //significa que estava voltando da tela do formulário
            if (resultCode == 200){
                //atualizou um item
                int posicao = data.getIntExtra("posicaoDoObjetoEditado", -1);
                adaptador.notifyItemChanged( posicao );
                rvAbastecimentos.smoothScrollToPosition(posicao);
            }else if (resultCode == 201){
                //adicionou um item
                Toast.makeText(this, "Abastecimento inserido com sucesso", Toast.LENGTH_LONG).show();
                int posicao = AbastecimentoDao.obterInstancia().obterLista().size()-1;
                adaptador.notifyItemInserted( posicao );
                rvAbastecimentos.smoothScrollToPosition(posicao);
            }else if (resultCode == 202){
                //excluir um item
                Toast.makeText(this, "Abastecimento excluído com sucesso", Toast.LENGTH_LONG).show();
                int posicao = data.getIntExtra("posicaoDoObjetoExcluido", -1);
                adaptador.notifyItemRemoved(posicao);
            }
        }
    }

    public void adicionarAbastecimento(View v){
        Intent intencao = new Intent( this, editar_abastecimento.class );
        startActivityForResult(intencao, 1);
    }

}
