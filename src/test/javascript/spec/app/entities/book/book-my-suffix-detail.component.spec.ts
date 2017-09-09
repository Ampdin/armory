/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArmoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BookMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/book/book-my-suffix-detail.component';
import { BookMySuffixService } from '../../../../../../main/webapp/app/entities/book/book-my-suffix.service';
import { BookMySuffix } from '../../../../../../main/webapp/app/entities/book/book-my-suffix.model';

describe('Component Tests', () => {

    describe('BookMySuffix Management Detail Component', () => {
        let comp: BookMySuffixDetailComponent;
        let fixture: ComponentFixture<BookMySuffixDetailComponent>;
        let service: BookMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArmoryTestModule],
                declarations: [BookMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BookMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(BookMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BookMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BookMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BookMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.book).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
