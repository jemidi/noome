import { IIngredient } from 'app/shared/model/ingredient.model';
import { Unit } from 'app/shared/model/enumerations/unit.model';

export interface IServing {
  id?: number;
  quantity?: number;
  unit?: Unit;
  ingredients?: IIngredient[];
}

export class Serving implements IServing {
  constructor(public id?: number, public quantity?: number, public unit?: Unit, public ingredients?: IIngredient[]) {}
}
