package com.magnani.dispositivosmoveis.autonomia_veiculo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.magnani.dispositivosmoveis.autonomia_veiculo.modelo.Abastecimento;
import com.magnani.dispositivosmoveis.autonomia_veiculo.modelo.AbastecimentoDao;

import java.text.DateFormat;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

public class editar_abastecimento extends AppCompatActivity {
    private Abastecimento objetoAbastecimento;
    private String idDoAbastecimento;
    private TextInputEditText etDescricao;
    private TextInputEditText etData;
    private TextInputEditText etQuilometragem;
    private TextInputEditText etQtdLitroAbastecido;
    Button btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_abastecimento);
        btnExcluir = findViewById(R.id.btn_excluir);
        etData = findViewById(R.id.data_abastecimento);
        etDescricao = findViewById(R.id.tipo_posto_material);
        etQuilometragem = findViewById(R.id.atual_km_material);
        etQtdLitroAbastecido = findViewById(R.id.abastecido_litro_material);
        etData.setKeyListener(null);

        String[] ITEMS = {"Shell", "Ipiranga", "Texaco", "Petrobras", "Outros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.tipo_posto_spinner);
        spinner.setAdapter(adapter);

        idDoAbastecimento = getIntent().getStringExtra("idDoAbastecimento");

        objetoAbastecimento = AbastecimentoDao.obterInstancia().obterObjetoPeloId(idDoAbastecimento);
        etDescricao.setText(objetoAbastecimento.getDescricao());
        etQtdLitroAbastecido.setText(String.valueOf(objetoAbastecimento.getQtd_litro_abastecido()));
        etQuilometragem.setText(String.valueOf(objetoAbastecimento.getQuilometragem()));
        etDescricao.setText(objetoAbastecimento.getDescricao());
        DateFormat formatador = android.text.format.DateFormat.getDateFormat( getApplicationContext() );
        String dataSelecionadaFormatada = formatador.format( objetoAbastecimento.getData().getTime() );
        etData.setText( dataSelecionadaFormatada );
    }
    public void salvar (View v){
        objetoAbastecimento.setDescricao(etDescricao.getText().toString());

        if(idDoAbastecimento == null) {
            //está salvando
            AbastecimentoDao.obterInstancia().adicionarNaLista(objetoAbastecimento);
            setResult(201);
        }else{
            //está editando
            int posicaoDoObjeto = AbastecimentoDao.obterInstancia().atualizaNaLista(objetoAbastecimento);
            Intent intencaoDeFechamentoDaActivityFormulario = new Intent();
            intencaoDeFechamentoDaActivityFormulario.putExtra("posicaoDoObjetoEditado", posicaoDoObjeto);
            setResult(200, intencaoDeFechamentoDaActivityFormulario);
        }
        finish();

    }
    public void selecionarData(View v){
        Calendar dataPadrao = objetoAbastecimento.getData();;
        if(dataPadrao == null){
            dataPadrao = Calendar.getInstance();
        }

        DatePickerDialog dialogoParaPegarData = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar dataSelecionada = Calendar.getInstance();
                        dataSelecionada.set(year,month,dayOfMonth);
                        objetoAbastecimento.setData(dataSelecionada);

                        DateFormat formatador = android.text.format.DateFormat.getDateFormat( getApplicationContext() );
                        String dataSelecionadaFormatada = formatador.format( dataSelecionada.getTime() );
                        etData.setText( dataSelecionadaFormatada );
                    }
                },
                dataPadrao.get(Calendar.YEAR),
                dataPadrao.get(Calendar.MONTH),
                dataPadrao.get(Calendar.DAY_OF_MONTH)
        );
        dialogoParaPegarData.show();
    }

    public void excluir(View v){


    }
}
