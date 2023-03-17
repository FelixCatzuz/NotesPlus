package com.feliscatus909.notesplus.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.feliscatus909.notesplus.MainActivity;
import com.feliscatus909.notesplus.NotesClickListener;
import com.feliscatus909.notesplus.R;
import com.feliscatus909.notesplus.models.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private List<Notes> notesList;
    private final Context context;
    private final NotesClickListener listener;

    public NotesListAdapter(List<Notes> notesList, Context context, NotesClickListener listener) {
        this.notesList = notesList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.textView_title.setText(notesList.get(position).getTitle());
        holder.textView_title.setSelected(true);
        holder.textView_note.setText(notesList.get(position).getNote());
        holder.textView_date.setText(notesList.get(position).getDate());
        holder.textView_date.setSelected(true);

        if (notesList.get(position).isPinned()) {
            holder.imageView_pin.setImageResource(R.drawable.ic_pin);
        } else holder.imageView_pin.setImageResource(0);

        int color_code = getRandomColor();

        holder.imageView_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu themesMenu = new PopupMenu(holder.imageView_theme.getContext(),
                        holder.imageView_theme);
                themesMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id){
                            case R.id.card_theme_blue:
                                int card_color_code1 = getBlueThemeCardColor();
                                holder.notes_container.setCardBackgroundColor(holder.itemView.getResources()
                                        .getColor(card_color_code1));
                                return true;
                            case R.id.card_theme_purple:
                                int card_color_code2 = getPurpleThemeCardColor();
                                holder.notes_container.setCardBackgroundColor(holder.itemView.getResources()
                                        .getColor(card_color_code2));
                                return true;
                            case R.id.card_theme_green:
                                int card_color_code3 = getGreenThemeCardColor();
                                holder.notes_container.setCardBackgroundColor(holder.itemView.getResources()
                                        .getColor(card_color_code3));
                                return true;
                            case R.id.card_theme_orange:
                                int card_color_code4 = getOrangeThemeCardColor();
                                holder.notes_container.setCardBackgroundColor(holder.itemView.getResources()
                                        .getColor(card_color_code4));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                themesMenu.inflate(R.menu.card_backgrounds);
                themesMenu.show();
            }
        });

        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources()
                 .getColor(color_code));

        holder.notes_container.setOnClickListener(v -> listener.onClick(notesList
                .get(holder.getAdapterPosition())));
        holder.notes_container.setOnLongClickListener(v -> {
            listener.onLongClick(notesList.get(holder.getAdapterPosition()), holder.notes_container);
            return true;
        });
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);
        colorCode.add(R.color.color6);

        Random random = new Random();
        int randomColor = random.nextInt(colorCode.size());
        return colorCode.get(randomColor);
    }

    private int getBlueThemeCardColor() {
        List<Integer> themeCodeBlue = new ArrayList<>();

        themeCodeBlue.add(R.color.blue_1);
        themeCodeBlue.add(R.color.blue_2);
        themeCodeBlue.add(R.color.blue_3);
        themeCodeBlue.add(R.color.blue_4);
        themeCodeBlue.add(R.color.blue_5);
        themeCodeBlue.add(R.color.blue_6);

        Random cardRandom1 = new Random();
        int randomCardColor = cardRandom1.nextInt(themeCodeBlue.size());
        return themeCodeBlue.get(randomCardColor);
    }

    private int getPurpleThemeCardColor() {
        List<Integer> themeCodePurple = new ArrayList<>();

        themeCodePurple.add(R.color.purple_1);
        themeCodePurple.add(R.color.purple_2);
        themeCodePurple.add(R.color.purple_3);
        themeCodePurple.add(R.color.purple_4);
        themeCodePurple.add(R.color.purple_5);
        themeCodePurple.add(R.color.purple_6);;

        Random cardRandom2 = new Random();
        int randomCardColor = cardRandom2.nextInt(themeCodePurple.size());
        return themeCodePurple.get(randomCardColor);
    }

    private int getGreenThemeCardColor() {
        List<Integer> themeCodeGreen = new ArrayList<>();

        themeCodeGreen.add(R.color.green_1);
        themeCodeGreen.add(R.color.green_2);
        themeCodeGreen.add(R.color.green_3);
        themeCodeGreen.add(R.color.green_4);
        themeCodeGreen.add(R.color.green_5);
        themeCodeGreen.add(R.color.green_6);

        Random cardRandom3 = new Random();
        int randomCardColor = cardRandom3.nextInt(themeCodeGreen.size());
        return themeCodeGreen.get(randomCardColor);
    }

    private int getOrangeThemeCardColor() {
        List<Integer> themeCodeOrange = new ArrayList<>();

        themeCodeOrange.add(R.color.orange_1);
        themeCodeOrange.add(R.color.orange_2);
        themeCodeOrange.add(R.color.orange_3);
        themeCodeOrange.add(R.color.orange_4);
        themeCodeOrange.add(R.color.orange_5);
        themeCodeOrange.add(R.color.orange_6);

        Random cardRandom4 = new Random();
        int randomCardColor = cardRandom4.nextInt(themeCodeOrange.size());
        return themeCodeOrange.get(randomCardColor);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void filterList(List<Notes> filteredList) {
        notesList = filteredList;
        notifyDataSetChanged();
    }

}
class NotesViewHolder extends RecyclerView.ViewHolder{
        final CardView notes_container;
        final TextView textView_title;
        final TextView textView_note;
        final TextView textView_date;
        final ImageView imageView_pin;
        final ImageView imageView_theme;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            notes_container = itemView.findViewById(R.id.notes_container);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_note = itemView.findViewById(R.id.textView_note);
            textView_date = itemView.findViewById(R.id.textView_date);
            imageView_pin = itemView.findViewById(R.id.imageView_pin);
            imageView_theme = itemView.findViewById(R.id.imageView_theme);
        }

    public CardView getNotes_container() {
        return notes_container;
    }
}
