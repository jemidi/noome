export interface IIngredient {
  id?: number;
  quantity?: number;
  foodName?: string;
  foodId?: number;
  servingUnit?: string;
  servingId?: number;
  recipeId?: number;
}

export class Ingredient implements IIngredient {
  constructor(
    public id?: number,
    public quantity?: number,
    public foodName?: string,
    public foodId?: number,
    public servingUnit?: string,
    public servingId?: number,
    public recipeId?: number
  ) {}
}
