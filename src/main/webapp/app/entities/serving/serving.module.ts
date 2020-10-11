import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NooMeSharedModule } from 'app/shared/shared.module';
import { ServingComponent } from './serving.component';
import { ServingDetailComponent } from './serving-detail.component';
import { ServingUpdateComponent } from './serving-update.component';
import { ServingDeleteDialogComponent } from './serving-delete-dialog.component';
import { servingRoute } from './serving.route';

@NgModule({
  imports: [NooMeSharedModule, RouterModule.forChild(servingRoute)],
  declarations: [ServingComponent, ServingDetailComponent, ServingUpdateComponent, ServingDeleteDialogComponent],
  entryComponents: [ServingDeleteDialogComponent],
})
export class NooMeServingModule {}
