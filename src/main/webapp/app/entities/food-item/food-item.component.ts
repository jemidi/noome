import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFoodItem } from 'app/shared/model/food-item.model';
import { FoodItemService } from './food-item.service';
import { FoodItemDeleteDialogComponent } from './food-item-delete-dialog.component';

@Component({
  selector: 'jhi-food-item',
  templateUrl: './food-item.component.html',
})
export class FoodItemComponent implements OnInit, OnDestroy {
  foodItems?: IFoodItem[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected foodItemService: FoodItemService,
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
      this.foodItemService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IFoodItem[]>) => (this.foodItems = res.body || []));
      return;
    }

    this.foodItemService.query().subscribe((res: HttpResponse<IFoodItem[]>) => (this.foodItems = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFoodItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFoodItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFoodItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('foodItemListModification', () => this.loadAll());
  }

  delete(foodItem: IFoodItem): void {
    const modalRef = this.modalService.open(FoodItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.foodItem = foodItem;
  }
}
