import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NooMeTestModule } from '../../../test.module';
import { ServingDetailComponent } from 'app/entities/serving/serving-detail.component';
import { Serving } from 'app/shared/model/serving.model';

describe('Component Tests', () => {
  describe('Serving Management Detail Component', () => {
    let comp: ServingDetailComponent;
    let fixture: ComponentFixture<ServingDetailComponent>;
    const route = ({ data: of({ serving: new Serving(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NooMeTestModule],
        declarations: [ServingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ServingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serving on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serving).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
