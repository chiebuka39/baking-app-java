package com.harrric.chiebuka.bakingapp_java.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harrric.chiebuka.bakingapp_java.R;
import com.harrric.chiebuka.bakingapp_java.models.IngredientsItem;

import io.realm.RealmList;

/**
 * Created by chiebuka on 6/26/17.
 */

public class IngredientsAgapter extends RecyclerView.Adapter<IngredientsAgapter.IngredientsViewHolder> {

    private RealmList<IngredientsItem> ingredientsItems;
    private Context context;
    private LayoutInflater inflater;

    public IngredientsAgapter(RealmList<IngredientsItem> ingredients, Context context){
        ingredientsItems = ingredients;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.ingredients_item_layout, viewGroup, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder ingredientsViewHolder, int i) {
        ingredientsViewHolder.bind(ingredientsItems.get(i));
    }

    @Override
    public int getItemCount() {
        return ingredientsItems.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView ingredients_name, ingredients_quantity, ingredients_measure;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            ingredients_name = (TextView) itemView.findViewById(R.id.ingredient_name);
            ingredients_quantity = (TextView) itemView.findViewById(R.id.ingredient_quantity);
            ingredients_measure = (TextView) itemView.findViewById(R.id.ingredient_measure);
        }

        public void bind(IngredientsItem item){
            ingredients_name.setText(item.getIngredient());
            ingredients_measure.setText(item.getMeasure());
            ingredients_quantity.setText(String.format("%s", item.getQuantity()));
        }
    }
}
