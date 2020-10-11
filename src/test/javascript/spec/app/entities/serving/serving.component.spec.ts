import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NooMeTestModule } from '../../../test.module';
import { ServingComponent } from 'app/entities/serving/serving.component';
import { ServingService } from 'app/entities/serving/serving.service';
import { Serving } from 'app/shared/model/serving.model';

describe('Component Tests', () => {
  describe('Serving Management Component', () => {
    let comp: ServingComponent;
    let fixture: ComponentFixture<ServingComponent>;
    let service: ServingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NooMeTestModule],
        declarations: [ServingComponent],
      })
        .overrideTemplate(ServingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Serving(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.servings && comp.servings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
