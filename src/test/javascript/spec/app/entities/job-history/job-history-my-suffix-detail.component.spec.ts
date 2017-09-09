/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArmoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { JobHistoryMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/job-history/job-history-my-suffix-detail.component';
import { JobHistoryMySuffixService } from '../../../../../../main/webapp/app/entities/job-history/job-history-my-suffix.service';
import { JobHistoryMySuffix } from '../../../../../../main/webapp/app/entities/job-history/job-history-my-suffix.model';

describe('Component Tests', () => {

    describe('JobHistoryMySuffix Management Detail Component', () => {
        let comp: JobHistoryMySuffixDetailComponent;
        let fixture: ComponentFixture<JobHistoryMySuffixDetailComponent>;
        let service: JobHistoryMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArmoryTestModule],
                declarations: [JobHistoryMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    JobHistoryMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(JobHistoryMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobHistoryMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobHistoryMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new JobHistoryMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.jobHistory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
