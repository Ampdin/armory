/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArmoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DepartmentMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/department/department-my-suffix-detail.component';
import { DepartmentMySuffixService } from '../../../../../../main/webapp/app/entities/department/department-my-suffix.service';
import { DepartmentMySuffix } from '../../../../../../main/webapp/app/entities/department/department-my-suffix.model';

describe('Component Tests', () => {

    describe('DepartmentMySuffix Management Detail Component', () => {
        let comp: DepartmentMySuffixDetailComponent;
        let fixture: ComponentFixture<DepartmentMySuffixDetailComponent>;
        let service: DepartmentMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArmoryTestModule],
                declarations: [DepartmentMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DepartmentMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(DepartmentMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepartmentMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepartmentMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DepartmentMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.department).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
