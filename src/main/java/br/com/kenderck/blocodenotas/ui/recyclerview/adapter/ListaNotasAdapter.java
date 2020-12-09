package br.com.kenderck.blocodenotas.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import br.com.kenderck.blocodenotas.R;
import br.com.kenderck.blocodenotas.model.Nota;
import br.com.kenderck.blocodenotas.ui.recyclerview.adapter.listener.OnItemClickListener;
import br.com.kenderck.blocodenotas.ui.recyclerview.adapter.listener.OnLongItemClickListener;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private final List<Nota> notas;
    private OnLongItemClickListener onLongItemClickListener;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ListaNotasAdapter(List<Nota> notas, Context context) {
        this.notas = notas;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    @NonNull
    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.NotaViewHolder holder, int position) {

        Nota nota = notas.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void altera(int position, Nota nota) {
        notas.set(position, nota);
        notifyDataSetChanged();
    }

    public void remove(int posicao) {
        notas.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public void troca(int posicaoInicio, int posicaoFim) {
        Collections.swap(notas, posicaoInicio, posicaoFim);//trocar de posição na lista depois da movimentação
        notifyItemMoved(posicaoInicio, posicaoFim);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {//classe interna para criar a viewHolder

        private final TextView titulo;
        private final TextView descricao;
        private Nota nota;

        private NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_lista_nome);
            descricao = itemView.findViewById(R.id.item_lista_descricao);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(nota, getAdapterPosition());//getAdapterPosition sabe extamente a posição dele
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongItemClickListener.OnLongClick(nota, getAdapterPosition());
                    return true;
                }
            });
        }

        private void vincula(Nota nota) {
            this.nota = nota;
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }

    public void adiciona(Nota nota) {
        notas.add(nota);
        notifyDataSetChanged();
    }
}
