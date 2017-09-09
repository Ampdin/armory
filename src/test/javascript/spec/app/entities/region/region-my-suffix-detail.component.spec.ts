/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArmoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RegionMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/region/region-my-suffix-detail.component';
import { RegionMySuffixService } from '../../../../../../main/webapp/app/entities/region/region-my-suffix.service';
import { RegionMySuffix } from '../../../../../../main/webapp/app/entities/region/region-my-suffix.model';

describe('Component Tests', () => {

    describe('RegionMySuffix Management Detail Component', () => {
        let comp: RegionMySuffixDetailComponent;
        let fixture: ComponentFixture<RegionMySuffixDetailComponent>;
        let service: RegionMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArmoryTestModule],
                declarations: [RegionMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RegionMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(RegionMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RegionMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegionMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RegionMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.region).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
