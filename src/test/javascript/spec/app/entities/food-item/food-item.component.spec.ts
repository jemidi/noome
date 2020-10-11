import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NooMeTestModule } from '../../../test.module';
import { FoodItemComponent } from 'app/entities/food-item/food-item.component';
import { FoodItemService } from 'app/entities/food-item/food-item.service';
import { FoodItem } from 'app/shared/model/food-item.model';

describe('Component Tests', () => {
  describe('FoodItem Management Component', () => {
    let comp: FoodItemComponent;
    let fixture: ComponentFixture<FoodItemComponent>;
    let service: FoodItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NooMeTestModule],
        declarations: [FoodItemComponent],
      })
        .overrideTemplate(FoodItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FoodItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FoodItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FoodItem(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.foodItems && comp.foodItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
