import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TaskMySuffix } from './task-my-suffix.model';
import { TaskMySuffixPopupService } from './task-my-suffix-popup.service';
import { TaskMySuffixService } from './task-my-suffix.service';
import { JobMySuffix, JobMySuffixService } from '../job';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-task-my-suffix-dialog',
    templateUrl: './task-my-suffix-dialog.component.html'
})
export class TaskMySuffixDialogComponent implements OnInit {

    task: TaskMySuffix;
    isSaving: boolean;

    jobs: JobMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private taskService: TaskMySuffixService,
        private jobService: JobMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.jobService.query()
            .subscribe((res: ResponseWrapper) => { this.jobs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.task.id !== undefined) {
            this.subscribeToSaveResponse(
                this.taskService.update(this.task));
        } else {
            this.subscribeToSaveResponse(
                this.taskService.create(this.task));
        }
    }

    private subscribeToSaveResponse(result: Observable<TaskMySuffix>) {
        result.subscribe((res: TaskMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TaskMySuffix) {
        this.eventManager.broadcast({ name: 'taskListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackJobById(index: number, item: JobMySuffix) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-task-my-suffix-popup',
    template: ''
})
export class TaskMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taskPopupService: TaskMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.taskPopupService
                    .open(TaskMySuffixDialogComponent as Component, params['id']);
            } else {
                this.taskPopupService
                    .open(TaskMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
