package br.com.kenderck.blocodenotas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.kenderck.blocodenotas.R;
import br.com.kenderck.blocodenotas.model.Nota;

public class ListaNotaAdapter extends BaseAdapter {

    private final List<Nota> notas;
    private final Context context;

    public ListaNotaAdapter(List<Nota> notas, Context context) {
        this.notas = notas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notas.size();
    }

    @Override
    public Nota getItem(int position) {
        return notas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false);
        Nota nota = getItem(position);

        TextView titulo = view.findViewById(R.id.item_lista_nome);
        titulo.setText(nota.getTitulo());

        TextView descricao = view.findViewById(R.id.item_lista_descricao);
        descricao.setText(nota.getDescricao());

        return view;
    }

    public void remove(int posicao) {
        notas.remove(posicao);
        notifyDataSetChanged();
    }
}
