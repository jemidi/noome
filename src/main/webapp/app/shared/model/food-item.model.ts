import { IIngredient } from 'app/shared/model/ingredient.model';

export interface IFoodItem {
  id?: number;
  brand?: string;
  name?: string;
  calories?: number;
  totalFat?: number;
  saturated?: number;
  polyunsaturated?: number;
  monounsaturated?: number;
  trans?: number;
  totalCarbs?: number;
  fibre?: number;
  sugars?: number;
  protein?: number;
  cholesterol?: number;
  sodium?: number;
  potassium?: number;
  ingredients?: IIngredient[];
}

export class FoodItem implements IFoodItem {
  constructor(
    public id?: number,
    public brand?: string,
    public name?: string,
    public calories?: number,
    public totalFat?: number,
    public saturated?: number,
    public polyunsaturated?: number,
    public monounsaturated?: number,
    public trans?: number,
    public totalCarbs?: number,
    public fibre?: number,
    public sugars?: number,
    public protein?: number,
    public cholesterol?: number,
    public sodium?: number,
    public potassium?: number,
    public ingredients?: IIngredient[]
  ) {}
}
