package com.harrric.chiebuka.bakingapp_java.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

@Generated("com.robohorse.robopojogenerator")
public class IngredientsItem extends RealmObject implements Serializable{

	@SerializedName("quantity")
	private double quantity;

	@SerializedName("measure")
	private String measure;

	@SerializedName("ingredient")
	private String ingredient;

	public void setQuantity(double quantity){
		this.quantity = quantity;
	}

	public double getQuantity(){
		return quantity;
	}

	public void setMeasure(String measure){
		this.measure = measure;
	}

	public String getMeasure(){
		return measure;
	}

	public void setIngredient(String ingredient){
		this.ingredient = ingredient;
	}

	public String getIngredient(){
		return ingredient;
	}

	@Override
 	public String toString(){
		return 
			"IngredientsItem{" + 
			"quantity = '" + quantity + '\'' + 
			",measure = '" + measure + '\'' + 
			",ingredient = '" + ingredient + '\'' + 
			"}";
		}
}