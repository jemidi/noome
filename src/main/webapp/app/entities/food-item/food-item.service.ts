import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IFoodItem } from 'app/shared/model/food-item.model';

type EntityResponseType = HttpResponse<IFoodItem>;
type EntityArrayResponseType = HttpResponse<IFoodItem[]>;

@Injectable({ providedIn: 'root' })
export class FoodItemService {
  public resourceUrl = SERVER_API_URL + 'api/food-items';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/food-items';

  constructor(protected http: HttpClient) {}

  create(foodItem: IFoodItem): Observable<EntityResponseType> {
    return this.http.post<IFoodItem>(this.resourceUrl, foodItem, { observe: 'response' });
  }

  update(foodItem: IFoodItem): Observable<EntityResponseType> {
    return this.http.put<IFoodItem>(this.resourceUrl, foodItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFoodItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFoodItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFoodItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
