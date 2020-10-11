import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NooMeSharedModule } from 'app/shared/shared.module';
import { FoodItemComponent } from './food-item.component';
import { FoodItemDetailComponent } from './food-item-detail.component';
import { FoodItemUpdateComponent } from './food-item-update.component';
import { FoodItemDeleteDialogComponent } from './food-item-delete-dialog.component';
import { foodItemRoute } from './food-item.route';

@NgModule({
  imports: [NooMeSharedModule, RouterModule.forChild(foodItemRoute)],
  declarations: [FoodItemComponent, FoodItemDetailComponent, FoodItemUpdateComponent, FoodItemDeleteDialogComponent],
  entryComponents: [FoodItemDeleteDialogComponent],
})
export class NooMeFoodItemModule {}
