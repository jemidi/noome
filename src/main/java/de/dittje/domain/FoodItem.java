package de.dittje.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FoodItem.
 */
@Entity
@Table(name = "food_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fooditem")
public class FoodItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "brand", length = 250, nullable = false)
    private String brand;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @NotNull
    @Column(name = "calories", nullable = false)
    private Double calories;

    @Column(name = "total_fat")
    private Double totalFat;

    @Column(name = "saturated")
    private Double saturated;

    @Column(name = "polyunsaturated")
    private Double polyunsaturated;

    @Column(name = "monounsaturated")
    private Double monounsaturated;

    @Column(name = "trans")
    private Double trans;

    @Column(name = "total_carbs")
    private Double totalCarbs;

    @Column(name = "fibre")
    private Double fibre;

    @Column(name = "sugars")
    private Double sugars;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "cholesterol")
    private Double cholesterol;

    @Column(name = "sodium")
    private Double sodium;

    @Column(name = "potassium")
    private Double potassium;

    @OneToMany(mappedBy = "food")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Ingredient> ingredients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public FoodItem brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public FoodItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCalories() {
        return calories;
    }

    public FoodItem calories(Double calories) {
        this.calories = calories;
        return this;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getTotalFat() {
        return totalFat;
    }

    public FoodItem totalFat(Double totalFat) {
        this.totalFat = totalFat;
        return this;
    }

    public void setTotalFat(Double totalFat) {
        this.totalFat = totalFat;
    }

    public Double getSaturated() {
        return saturated;
    }

    public FoodItem saturated(Double saturated) {
        this.saturated = saturated;
        return this;
    }

    public void setSaturated(Double saturated) {
        this.saturated = saturated;
    }

    public Double getPolyunsaturated() {
        return polyunsaturated;
    }

    public FoodItem polyunsaturated(Double polyunsaturated) {
        this.polyunsaturated = polyunsaturated;
        return this;
    }

    public void setPolyunsaturated(Double polyunsaturated) {
        this.polyunsaturated = polyunsaturated;
    }

    public Double getMonounsaturated() {
        return monounsaturated;
    }

    public FoodItem monounsaturated(Double monounsaturated) {
        this.monounsaturated = monounsaturated;
        return this;
    }

    public void setMonounsaturated(Double monounsaturated) {
        this.monounsaturated = monounsaturated;
    }

    public Double getTrans() {
        return trans;
    }

    public FoodItem trans(Double trans) {
        this.trans = trans;
        return this;
    }

    public void setTrans(Double trans) {
        this.trans = trans;
    }

    public Double getTotalCarbs() {
        return totalCarbs;
    }

    public FoodItem totalCarbs(Double totalCarbs) {
        this.totalCarbs = totalCarbs;
        return this;
    }

    public void setTotalCarbs(Double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public Double getFibre() {
        return fibre;
    }

    public FoodItem fibre(Double fibre) {
        this.fibre = fibre;
        return this;
    }

    public void setFibre(Double fibre) {
        this.fibre = fibre;
    }

    public Double getSugars() {
        return sugars;
    }

    public FoodItem sugars(Double sugars) {
        this.sugars = sugars;
        return this;
    }

    public void setSugars(Double sugars) {
        this.sugars = sugars;
    }

    public Double getProtein() {
        return protein;
    }

    public FoodItem protein(Double protein) {
        this.protein = protein;
        return this;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCholesterol() {
        return cholesterol;
    }

    public FoodItem cholesterol(Double cholesterol) {
        this.cholesterol = cholesterol;
        return this;
    }

    public void setCholesterol(Double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Double getSodium() {
        return sodium;
    }

    public FoodItem sodium(Double sodium) {
        this.sodium = sodium;
        return this;
    }

    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }

    public Double getPotassium() {
        return potassium;
    }

    public FoodItem potassium(Double potassium) {
        this.potassium = potassium;
        return this;
    }

    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public FoodItem ingredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public FoodItem addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setFood(this);
        return this;
    }

    public FoodItem removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        ingredient.setFood(null);
        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodItem)) {
            return false;
        }
        return id != null && id.equals(((FoodItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodItem{" +
            "id=" + getId() +
            ", brand='" + getBrand() + "'" +
            ", name='" + getName() + "'" +
            ", calories=" + getCalories() +
            ", totalFat=" + getTotalFat() +
            ", saturated=" + getSaturated() +
            ", polyunsaturated=" + getPolyunsaturated() +
            ", monounsaturated=" + getMonounsaturated() +
            ", trans=" + getTrans() +
            ", totalCarbs=" + getTotalCarbs() +
            ", fibre=" + getFibre() +
            ", sugars=" + getSugars() +
            ", protein=" + getProtein() +
            ", cholesterol=" + getCholesterol() +
            ", sodium=" + getSodium() +
            ", potassium=" + getPotassium() +
            "}";
    }
}
