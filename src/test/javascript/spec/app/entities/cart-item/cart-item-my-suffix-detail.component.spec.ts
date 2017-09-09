/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArmoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CartItemMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/cart-item/cart-item-my-suffix-detail.component';
import { CartItemMySuffixService } from '../../../../../../main/webapp/app/entities/cart-item/cart-item-my-suffix.service';
import { CartItemMySuffix } from '../../../../../../main/webapp/app/entities/cart-item/cart-item-my-suffix.model';

describe('Component Tests', () => {

    describe('CartItemMySuffix Management Detail Component', () => {
        let comp: CartItemMySuffixDetailComponent;
        let fixture: ComponentFixture<CartItemMySuffixDetailComponent>;
        let service: CartItemMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArmoryTestModule],
                declarations: [CartItemMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CartItemMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(CartItemMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CartItemMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartItemMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CartItemMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cartItem).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
