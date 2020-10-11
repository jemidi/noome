import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFoodItem } from 'app/shared/model/food-item.model';
import { FoodItemService } from './food-item.service';

@Component({
  templateUrl: './food-item-delete-dialog.component.html',
})
export class FoodItemDeleteDialogComponent {
  foodItem?: IFoodItem;

  constructor(protected foodItemService: FoodItemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.foodItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('foodItemListModification');
      this.activeModal.close();
    });
  }
}
