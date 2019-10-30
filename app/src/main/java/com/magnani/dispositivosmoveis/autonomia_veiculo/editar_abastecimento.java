package com.magnani.dispositivosmoveis.autonomia_veiculo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.magnani.dispositivosmoveis.autonomia_veiculo.modelo.Abastecimento;
import com.magnani.dispositivosmoveis.autonomia_veiculo.modelo.AbastecimentoDao;

import java.text.DateFormat;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

public class editar_abastecimento extends AppCompatActivity {
    private Abastecimento abastecimento;
    private String idDoAbastecimento;
    private EditText etKm;
    private EditText etLitros;
    private EditText etData;
    private Spinner postos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_abastecimento);
        etKm = findViewById(R.id.etKM);
        etLitros = findViewById(R.id.etLitros);
        etData = findViewById(R.id.etData);
        postos = findViewById(R.id.spPostos);
        etData.setKeyListener(null);

        String[] postos_opcoes = getResources().getStringArray(R.array.posto_opcoes);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, postos_opcoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        postos.setAdapter(adapter);

        idDoAbastecimento = getIntent().getStringExtra("idAbastecimento");

        if(idDoAbastecimento == null){
            abastecimento = new Abastecimento();
            Button btnExcluir = findViewById(R.id.btnExcluir);
            btnExcluir.setVisibility(View.INVISIBLE);
        }else{
            abastecimento = AbastecimentoDao.obterInstancia().obterObjetoPeloId(idDoAbastecimento);
            etKm.setText(String.valueOf(abastecimento.getQuilometragem()));
            etLitros.setText(String.valueOf(abastecimento.getQtd_litro_abastecido()));

            for(int i = 0; i < postos.getAdapter().getCount(); i++){
                if (postos.getAdapter().getItem(i).toString() == abastecimento.getPosto()){
                    postos.setSelection(i+1);
                    break;
                }
            }

            DateFormat formatador = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            String dataSelecionadaFormatada = formatador.format(abastecimento.getData().getTime());
            etData.setText(dataSelecionadaFormatada);
        }
    }
    public void salvar(View v){
        abastecimento.setQuilometragem(Double.parseDouble(etKm.getText().toString()));
        abastecimento.setQtd_litro_abastecido(Double.parseDouble(etLitros.getText().toString()));
        abastecimento.setPosto(postos.getSelectedItem().toString());

        if(idDoAbastecimento == null) {
            AbastecimentoDao.obterInstancia().adicionarNaLista(abastecimento);
            setResult(201);
        }else{
            int posicaoDoObjeto = AbastecimentoDao.obterInstancia().atualizaNaLista(abastecimento);
            Intent intencaoDeFechamentoDaActivityFormulario = new Intent();
            intencaoDeFechamentoDaActivityFormulario.putExtra("posicaoDoObjetoEditado", posicaoDoObjeto);
            setResult(200, intencaoDeFechamentoDaActivityFormulario);
        }
        finish();

    }

    public void selecionarData(View v){
        Calendar dataPadrao = abastecimento.getData();;
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
                        abastecimento.setData(dataSelecionada);

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
        new AlertDialog.Builder(this)
                .setTitle("Você tem certeza?")
                .setMessage("Você quer mesmo excluir?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int posicaoDoObjeto = AbastecimentoDao.obterInstancia().excluiDaLista(abastecimento);
                        Intent intencaoDeFechamentoDaActivityFormulario = new Intent();
                        intencaoDeFechamentoDaActivityFormulario.putExtra("posicaoDoObjetoExcluido", posicaoDoObjeto);
                        setResult(202, intencaoDeFechamentoDaActivityFormulario);
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
