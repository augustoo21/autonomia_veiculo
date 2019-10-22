package com.magnani.dispositivosmoveis.autonomia_veiculo.modelo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.magnani.dispositivosmoveis.autonomia_veiculo.R;

import fr.ganfra.materialspinner.MaterialSpinner;

public class AdicionarAbastecimento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_abastecimento);

        String[] ITEMS = {"Shell", "Ipiranga", "Texaco", "Petrobras", "Outros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.tipo_posto_spinner);
        spinner.setAdapter(adapter);
    }
}
