import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IServing, Serving } from 'app/shared/model/serving.model';
import { ServingService } from './serving.service';

@Component({
  selector: 'jhi-serving-update',
  templateUrl: './serving-update.component.html',
})
export class ServingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    unit: [null, [Validators.required]],
  });

  constructor(protected servingService: ServingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serving }) => {
      this.updateForm(serving);
    });
  }

  updateForm(serving: IServing): void {
    this.editForm.patchValue({
      id: serving.id,
      quantity: serving.quantity,
      unit: serving.unit,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serving = this.createFromForm();
    if (serving.id !== undefined) {
      this.subscribeToSaveResponse(this.servingService.update(serving));
    } else {
      this.subscribeToSaveResponse(this.servingService.create(serving));
    }
  }

  private createFromForm(): IServing {
    return {
      ...new Serving(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      unit: this.editForm.get(['unit'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServing>>): void {
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
