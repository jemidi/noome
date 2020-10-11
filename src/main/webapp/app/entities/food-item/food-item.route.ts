import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFoodItem, FoodItem } from 'app/shared/model/food-item.model';
import { FoodItemService } from './food-item.service';
import { FoodItemComponent } from './food-item.component';
import { FoodItemDetailComponent } from './food-item-detail.component';
import { FoodItemUpdateComponent } from './food-item-update.component';

@Injectable({ providedIn: 'root' })
export class FoodItemResolve implements Resolve<IFoodItem> {
  constructor(private service: FoodItemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFoodItem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((foodItem: HttpResponse<FoodItem>) => {
          if (foodItem.body) {
            return of(foodItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FoodItem());
  }
}

export const foodItemRoute: Routes = [
  {
    path: '',
    component: FoodItemComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FoodItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FoodItemDetailComponent,
    resolve: {
      foodItem: FoodItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FoodItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FoodItemUpdateComponent,
    resolve: {
      foodItem: FoodItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FoodItems',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FoodItemUpdateComponent,
    resolve: {
      foodItem: FoodItemResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'FoodItems',
    },
    canActivate: [UserRouteAccessService],
  },
];
