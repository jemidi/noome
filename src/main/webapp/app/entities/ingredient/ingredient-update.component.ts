import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIngredient, Ingredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from './ingredient.service';
import { IFoodItem } from 'app/shared/model/food-item.model';
import { FoodItemService } from 'app/entities/food-item/food-item.service';
import { IServing } from 'app/shared/model/serving.model';
import { ServingService } from 'app/entities/serving/serving.service';
import { IRecipe } from 'app/shared/model/recipe.model';
import { RecipeService } from 'app/entities/recipe/recipe.service';

type SelectableEntity = IFoodItem | IServing | IRecipe;

@Component({
  selector: 'jhi-ingredient-update',
  templateUrl: './ingredient-update.component.html',
})
export class IngredientUpdateComponent implements OnInit {
  isSaving = false;
  fooditems: IFoodItem[] = [];
  servings: IServing[] = [];
  recipes: IRecipe[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    foodId: [null, Validators.required],
    servingId: [null, Validators.required],
    recipeId: [],
  });

  constructor(
    protected ingredientService: IngredientService,
    protected foodItemService: FoodItemService,
    protected servingService: ServingService,
    protected recipeService: RecipeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredient }) => {
      this.updateForm(ingredient);

      this.foodItemService.query().subscribe((res: HttpResponse<IFoodItem[]>) => (this.fooditems = res.body || []));

      this.servingService.query().subscribe((res: HttpResponse<IServing[]>) => (this.servings = res.body || []));

      this.recipeService.query().subscribe((res: HttpResponse<IRecipe[]>) => (this.recipes = res.body || []));
    });
  }

  updateForm(ingredient: IIngredient): void {
    this.editForm.patchValue({
      id: ingredient.id,
      quantity: ingredient.quantity,
      foodId: ingredient.foodId,
      servingId: ingredient.servingId,
      recipeId: ingredient.recipeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ingredient = this.createFromForm();
    if (ingredient.id !== undefined) {
      this.subscribeToSaveResponse(this.ingredientService.update(ingredient));
    } else {
      this.subscribeToSaveResponse(this.ingredientService.create(ingredient));
    }
  }

  private createFromForm(): IIngredient {
    return {
      ...new Ingredient(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      foodId: this.editForm.get(['foodId'])!.value,
      servingId: this.editForm.get(['servingId'])!.value,
      recipeId: this.editForm.get(['recipeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIngredient>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
