import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { JobHistoryMySuffix } from './job-history-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class JobHistoryMySuffixService {

    private resourceUrl = 'api/job-histories';
    private resourceSearchUrl = 'api/_search/job-histories';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(jobHistory: JobHistoryMySuffix): Observable<JobHistoryMySuffix> {
        const copy = this.convert(jobHistory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(jobHistory: JobHistoryMySuffix): Observable<JobHistoryMySuffix> {
        const copy = this.convert(jobHistory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<JobHistoryMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.startDate = this.dateUtils
            .convertDateTimeFromServer(entity.startDate);
        entity.endDate = this.dateUtils
            .convertDateTimeFromServer(entity.endDate);
    }

    private convert(jobHistory: JobHistoryMySuffix): JobHistoryMySuffix {
        const copy: JobHistoryMySuffix = Object.assign({}, jobHistory);

        copy.startDate = this.dateUtils.toDate(jobHistory.startDate);

        copy.endDate = this.dateUtils.toDate(jobHistory.endDate);
        return copy;
    }
}
