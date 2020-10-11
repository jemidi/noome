import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServing } from 'app/shared/model/serving.model';
import { ServingService } from './serving.service';

@Component({
  templateUrl: './serving-delete-dialog.component.html',
})
export class ServingDeleteDialogComponent {
  serving?: IServing;

  constructor(protected servingService: ServingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.servingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('servingListModification');
      this.activeModal.close();
    });
  }
}
