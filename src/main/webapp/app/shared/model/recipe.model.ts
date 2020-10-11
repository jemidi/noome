import { IIngredient } from 'app/shared/model/ingredient.model';

export interface IRecipe {
  id?: number;
  name?: string;
  portions?: number;
  ingredients?: IIngredient[];
}

export class Recipe implements IRecipe {
  constructor(public id?: number, public name?: string, public portions?: number, public ingredients?: IIngredient[]) {}
}
