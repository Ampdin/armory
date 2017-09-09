/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArmoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TaskMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/task/task-my-suffix-detail.component';
import { TaskMySuffixService } from '../../../../../../main/webapp/app/entities/task/task-my-suffix.service';
import { TaskMySuffix } from '../../../../../../main/webapp/app/entities/task/task-my-suffix.model';

describe('Component Tests', () => {

    describe('TaskMySuffix Management Detail Component', () => {
        let comp: TaskMySuffixDetailComponent;
        let fixture: ComponentFixture<TaskMySuffixDetailComponent>;
        let service: TaskMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArmoryTestModule],
                declarations: [TaskMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TaskMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(TaskMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaskMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaskMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TaskMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.task).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
