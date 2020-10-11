import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFoodItem, FoodItem } from 'app/shared/model/food-item.model';
import { FoodItemService } from './food-item.service';

@Component({
  selector: 'jhi-food-item-update',
  templateUrl: './food-item-update.component.html',
})
export class FoodItemUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    brand: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(250)]],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(250)]],
    calories: [null, [Validators.required]],
    totalFat: [],
    saturated: [],
    polyunsaturated: [],
    monounsaturated: [],
    trans: [],
    totalCarbs: [],
    fibre: [],
    sugars: [],
    protein: [],
    cholesterol: [],
    sodium: [],
    potassium: [],
  });

  constructor(protected foodItemService: FoodItemService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ foodItem }) => {
      this.updateForm(foodItem);
    });
  }

  updateForm(foodItem: IFoodItem): void {
    this.editForm.patchValue({
      id: foodItem.id,
      brand: foodItem.brand,
      name: foodItem.name,
      calories: foodItem.calories,
      totalFat: foodItem.totalFat,
      saturated: foodItem.saturated,
      polyunsaturated: foodItem.polyunsaturated,
      monounsaturated: foodItem.monounsaturated,
      trans: foodItem.trans,
      totalCarbs: foodItem.totalCarbs,
      fibre: foodItem.fibre,
      sugars: foodItem.sugars,
      protein: foodItem.protein,
      cholesterol: foodItem.cholesterol,
      sodium: foodItem.sodium,
      potassium: foodItem.potassium,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const foodItem = this.createFromForm();
    if (foodItem.id !== undefined) {
      this.subscribeToSaveResponse(this.foodItemService.update(foodItem));
    } else {
      this.subscribeToSaveResponse(this.foodItemService.create(foodItem));
    }
  }

  private createFromForm(): IFoodItem {
    return {
      ...new FoodItem(),
      id: this.editForm.get(['id'])!.value,
      brand: this.editForm.get(['brand'])!.value,
      name: this.editForm.get(['name'])!.value,
      calories: this.editForm.get(['calories'])!.value,
      totalFat: this.editForm.get(['totalFat'])!.value,
      saturated: this.editForm.get(['saturated'])!.value,
      polyunsaturated: this.editForm.get(['polyunsaturated'])!.value,
      monounsaturated: this.editForm.get(['monounsaturated'])!.value,
      trans: this.editForm.get(['trans'])!.value,
      totalCarbs: this.editForm.get(['totalCarbs'])!.value,
      fibre: this.editForm.get(['fibre'])!.value,
      sugars: this.editForm.get(['sugars'])!.value,
      protein: this.editForm.get(['protein'])!.value,
      cholesterol: this.editForm.get(['cholesterol'])!.value,
      sodium: this.editForm.get(['sodium'])!.value,
      potassium: this.editForm.get(['potassium'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoodItem>>): void {
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
}
