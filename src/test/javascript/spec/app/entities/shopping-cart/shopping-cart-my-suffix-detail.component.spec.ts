/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArmoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ShoppingCartMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart-my-suffix-detail.component';
import { ShoppingCartMySuffixService } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart-my-suffix.service';
import { ShoppingCartMySuffix } from '../../../../../../main/webapp/app/entities/shopping-cart/shopping-cart-my-suffix.model';

describe('Component Tests', () => {

    describe('ShoppingCartMySuffix Management Detail Component', () => {
        let comp: ShoppingCartMySuffixDetailComponent;
        let fixture: ComponentFixture<ShoppingCartMySuffixDetailComponent>;
        let service: ShoppingCartMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArmoryTestModule],
                declarations: [ShoppingCartMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ShoppingCartMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ShoppingCartMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShoppingCartMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShoppingCartMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ShoppingCartMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shoppingCart).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
