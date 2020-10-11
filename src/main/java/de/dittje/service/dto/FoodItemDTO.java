package de.dittje.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link de.dittje.domain.FoodItem} entity.
 */
public class FoodItemDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    private String brand;

    @NotNull
    @Size(min = 1, max = 250)
    private String name;

    @NotNull
    private Double calories;

    private Double totalFat;

    private Double saturated;

    private Double polyunsaturated;

    private Double monounsaturated;

    private Double trans;

    private Double totalCarbs;

    private Double fibre;

    private Double sugars;

    private Double protein;

    private Double cholesterol;

    private Double sodium;

    private Double potassium;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(Double totalFat) {
        this.totalFat = totalFat;
    }

    public Double getSaturated() {
        return saturated;
    }

    public void setSaturated(Double saturated) {
        this.saturated = saturated;
    }

    public Double getPolyunsaturated() {
        return polyunsaturated;
    }

    public void setPolyunsaturated(Double polyunsaturated) {
        this.polyunsaturated = polyunsaturated;
    }

    public Double getMonounsaturated() {
        return monounsaturated;
    }

    public void setMonounsaturated(Double monounsaturated) {
        this.monounsaturated = monounsaturated;
    }

    public Double getTrans() {
        return trans;
    }

    public void setTrans(Double trans) {
        this.trans = trans;
    }

    public Double getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(Double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public Double getFibre() {
        return fibre;
    }

    public void setFibre(Double fibre) {
        this.fibre = fibre;
    }

    public Double getSugars() {
        return sugars;
    }

    public void setSugars(Double sugars) {
        this.sugars = sugars;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Double getSodium() {
        return sodium;
    }

    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }

    public Double getPotassium() {
        return potassium;
    }

    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodItemDTO)) {
            return false;
        }

        return id != null && id.equals(((FoodItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodItemDTO{" +
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
