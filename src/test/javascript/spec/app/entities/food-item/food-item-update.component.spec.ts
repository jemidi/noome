import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NooMeTestModule } from '../../../test.module';
import { FoodItemUpdateComponent } from 'app/entities/food-item/food-item-update.component';
import { FoodItemService } from 'app/entities/food-item/food-item.service';
import { FoodItem } from 'app/shared/model/food-item.model';

describe('Component Tests', () => {
  describe('FoodItem Management Update Component', () => {
    let comp: FoodItemUpdateComponent;
    let fixture: ComponentFixture<FoodItemUpdateComponent>;
    let service: FoodItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NooMeTestModule],
        declarations: [FoodItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FoodItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FoodItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoodItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FoodItem(123);
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
        const entity = new FoodItem();
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
