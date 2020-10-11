import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IServing } from 'app/shared/model/serving.model';

type EntityResponseType = HttpResponse<IServing>;
type EntityArrayResponseType = HttpResponse<IServing[]>;

@Injectable({ providedIn: 'root' })
export class ServingService {
  public resourceUrl = SERVER_API_URL + 'api/servings';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/servings';

  constructor(protected http: HttpClient) {}

  create(serving: IServing): Observable<EntityResponseType> {
    return this.http.post<IServing>(this.resourceUrl, serving, { observe: 'response' });
  }

  update(serving: IServing): Observable<EntityResponseType> {
    return this.http.put<IServing>(this.resourceUrl, serving, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServing>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServing[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServing[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
