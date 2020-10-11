import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServing, Serving } from 'app/shared/model/serving.model';
import { ServingService } from './serving.service';
import { ServingComponent } from './serving.component';
import { ServingDetailComponent } from './serving-detail.component';
import { ServingUpdateComponent } from './serving-update.component';

@Injectable({ providedIn: 'root' })
export class ServingResolve implements Resolve<IServing> {
  constructor(private service: ServingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServing> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((serving: HttpResponse<Serving>) => {
          if (serving.body) {
            return of(serving.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Serving());
  }
}

export const servingRoute: Routes = [
  {
    path: '',
    component: ServingComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Servings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServingDetailComponent,
    resolve: {
      serving: ServingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Servings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServingUpdateComponent,
    resolve: {
      serving: ServingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Servings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServingUpdateComponent,
    resolve: {
      serving: ServingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Servings',
    },
    canActivate: [UserRouteAccessService],
  },
];
