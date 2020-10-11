import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FoodItemService } from 'app/entities/food-item/food-item.service';
import { IFoodItem, FoodItem } from 'app/shared/model/food-item.model';

describe('Service Tests', () => {
  describe('FoodItem Service', () => {
    let injector: TestBed;
    let service: FoodItemService;
    let httpMock: HttpTestingController;
    let elemDefault: IFoodItem;
    let expectedResult: IFoodItem | IFoodItem[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FoodItemService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new FoodItem(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FoodItem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FoodItem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FoodItem', () => {
        const returnedFromService = Object.assign(
          {
            brand: 'BBBBBB',
            name: 'BBBBBB',
            calories: 1,
            totalFat: 1,
            saturated: 1,
            polyunsaturated: 1,
            monounsaturated: 1,
            trans: 1,
            totalCarbs: 1,
            fibre: 1,
            sugars: 1,
            protein: 1,
            cholesterol: 1,
            sodium: 1,
            potassium: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FoodItem', () => {
        const returnedFromService = Object.assign(
          {
            brand: 'BBBBBB',
            name: 'BBBBBB',
            calories: 1,
            totalFat: 1,
            saturated: 1,
            polyunsaturated: 1,
            monounsaturated: 1,
            trans: 1,
            totalCarbs: 1,
            fibre: 1,
            sugars: 1,
            protein: 1,
            cholesterol: 1,
            sodium: 1,
            potassium: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FoodItem', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
