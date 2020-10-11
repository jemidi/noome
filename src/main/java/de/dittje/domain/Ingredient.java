package de.dittje.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Ingredient.
 */
@Entity
@Table(name = "ingredient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ingredient")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Float quantity;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "ingredients", allowSetters = true)
    private FoodItem food;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "ingredients", allowSetters = true)
    private Serving serving;

    @ManyToOne
    @JsonIgnoreProperties(value = "ingredients", allowSetters = true)
    private Recipe recipe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getQuantity() {
        return quantity;
    }

    public Ingredient quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public FoodItem getFood() {
        return food;
    }

    public Ingredient food(FoodItem foodItem) {
        this.food = foodItem;
        return this;
    }

    public void setFood(FoodItem foodItem) {
        this.food = foodItem;
    }

    public Serving getServing() {
        return serving;
    }

    public Ingredient serving(Serving serving) {
        this.serving = serving;
        return this;
    }

    public void setServing(Serving serving) {
        this.serving = serving;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Ingredient recipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ingredient)) {
            return false;
        }
        return id != null && id.equals(((Ingredient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ingredient{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
