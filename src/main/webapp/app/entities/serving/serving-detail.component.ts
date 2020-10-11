import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServing } from 'app/shared/model/serving.model';

@Component({
  selector: 'jhi-serving-detail',
  templateUrl: './serving-detail.component.html',
})
export class ServingDetailComponent implements OnInit {
  serving: IServing | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serving }) => (this.serving = serving));
  }

  previousState(): void {
    window.history.back();
  }
}
