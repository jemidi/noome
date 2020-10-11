import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IServing } from 'app/shared/model/serving.model';
import { ServingService } from './serving.service';
import { ServingDeleteDialogComponent } from './serving-delete-dialog.component';

@Component({
  selector: 'jhi-serving',
  templateUrl: './serving.component.html',
})
export class ServingComponent implements OnInit, OnDestroy {
  servings?: IServing[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected servingService: ServingService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.servingService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IServing[]>) => (this.servings = res.body || []));
      return;
    }

    this.servingService.query().subscribe((res: HttpResponse<IServing[]>) => (this.servings = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInServings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IServing): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInServings(): void {
    this.eventSubscriber = this.eventManager.subscribe('servingListModification', () => this.loadAll());
  }

  delete(serving: IServing): void {
    const modalRef = this.modalService.open(ServingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.serving = serving;
  }
}
