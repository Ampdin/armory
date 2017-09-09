import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RegionMySuffix } from './region-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RegionMySuffixService {

    private resourceUrl = 'api/regions';
    private resourceSearchUrl = 'api/_search/regions';

    constructor(private http: Http) { }

    create(region: RegionMySuffix): Observable<RegionMySuffix> {
        const copy = this.convert(region);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(region: RegionMySuffix): Observable<RegionMySuffix> {
        const copy = this.convert(region);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RegionMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(region: RegionMySuffix): RegionMySuffix {
        const copy: RegionMySuffix = Object.assign({}, region);
        return copy;
    }
}
