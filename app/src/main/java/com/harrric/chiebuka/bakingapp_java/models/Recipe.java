package com.harrric.chiebuka.bakingapp_java.models;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("com.robohorse.robopojogenerator")
public class Recipe extends RealmObject{

	@SerializedName("image")
	private String image;

	@SerializedName("servings")
	private int servings;

	@SerializedName("name")
	@PrimaryKey
	private String name;

	@SerializedName("ingredients")
	private RealmList<IngredientsItem> ingredients;

	@SerializedName("id")
	private int id;

	@SerializedName("steps")
	private RealmList<StepsItem> steps;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setServings(int servings){
		this.servings = servings;
	}

	public int getServings(){
		return servings;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIngredients(RealmList<IngredientsItem> ingredients){
		this.ingredients = ingredients;
	}

	public RealmList<IngredientsItem> getIngredients(){
		return ingredients;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSteps(RealmList<StepsItem> steps){
		this.steps = steps;
	}

	public RealmList<StepsItem> getSteps(){
		return steps;
	}

	@Override
 	public String toString(){
		return 
			"Recipe{" + 
			"image = '" + image + '\'' + 
			",servings = '" + servings + '\'' + 
			",name = '" + name + '\'' + 
			",ingredients = '" + ingredients + '\'' + 
			",id = '" + id + '\'' + 
			",steps = '" + steps + '\'' + 
			"}";
		}
}