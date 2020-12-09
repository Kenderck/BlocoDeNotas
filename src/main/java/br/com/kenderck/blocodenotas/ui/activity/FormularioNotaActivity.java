package br.com.kenderck.blocodenotas.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.kenderck.blocodenotas.R;
import br.com.kenderck.blocodenotas.model.Nota;

import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.CHAVE_NOTA;
import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.CHAVE_POSITION;
import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.DEFAULT_VALUE;
import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.TITULO_APP_BAR_ALTERA_NOTA;
import static br.com.kenderck.blocodenotas.ui.activity.ConstantesActivities.TITULO_APP_BAR_INSERIR_NOTA;

public class FormularioNotaActivity extends AppCompatActivity {



    private int positionRecebida = DEFAULT_VALUE;//-1 para posição invalida
    private TextView titulo;
    private TextView descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APP_BAR_INSERIR_NOTA);
        inicializaCampos();

        Intent intent = getIntent();
        if(intent.hasExtra(CHAVE_NOTA)){// && intent.hasExtra("Position") não precisa, pois se for invalida vem como -1
            setTitle(TITULO_APP_BAR_ALTERA_NOTA);
            Nota notaRecebida = (Nota) intent.getSerializableExtra(CHAVE_NOTA);
            //-1 para posição invalida, caso não exista
            positionRecebida = intent.getIntExtra(CHAVE_POSITION, DEFAULT_VALUE);
            assert notaRecebida != null;

            preencheCampos(notaRecebida);
        }
    }

    private void inicializaCampos(){
        titulo = findViewById(R.id.formulario_titulo);
        descricao = findViewById(R.id.formulario_descricao);
    }

    private void preencheCampos(Nota nota) {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_formulario_nota_salva_ic_salva) {
            if(titulo.getText().toString().equals("")){
                Toast.makeText(this, "Favor, escolha um titulo!", Toast.LENGTH_SHORT).show();
            }else{
                salvarNota();
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarNota() {
        Nota notaCriada = new Nota(titulo.getText().toString(), descricao.getText().toString());
        retornaNota(notaCriada);
    }

    private void retornaNota(Nota nota) {
        Intent intent = new Intent();
        intent.putExtra(CHAVE_NOTA, nota);
        intent.putExtra(CHAVE_POSITION, positionRecebida);
        setResult(Activity.RESULT_OK, intent);
    }
}