import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NooMeTestModule } from '../../../test.module';
import { ServingUpdateComponent } from 'app/entities/serving/serving-update.component';
import { ServingService } from 'app/entities/serving/serving.service';
import { Serving } from 'app/shared/model/serving.model';

describe('Component Tests', () => {
  describe('Serving Management Update Component', () => {
    let comp: ServingUpdateComponent;
    let fixture: ComponentFixture<ServingUpdateComponent>;
    let service: ServingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NooMeTestModule],
        declarations: [ServingUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ServingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Serving(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Serving();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
