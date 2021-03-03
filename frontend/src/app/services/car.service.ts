import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {CarInfo} from '../models/carInfo';
import {apiUrl} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class CarService {

    private carsUrl = `${apiUrl}/cars`;


  constructor(private http: HttpClient) {
    }

  getAllCars(page: number, size: number): Observable<any> {
    const url = `${this.carsUrl}?page=${page}&size=${size}`;
    return this.http.get(url)
      .pipe(
        // tap(_ => console.log(_)),
      );
  }

  getCarDetail(carId: string): Observable<CarInfo> {
    const url = `${this.carsUrl}/car/${carId}`;
    return this.http.get<CarInfo>(url).pipe(
      catchError(_ => {
        console.log("Get Detail Failed");
        return of(new CarInfo());
      })
    );
  }


    /**
     * Handle Http operation that failed.
     * Let the app continue.
     * @param operation - name of the operation that failed
     * @param result - optional value to return as the observable result
     */
    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {

            console.error(error); // log to console instead

            // Let the app keep running by returning an empty result.
            return of(result as T);
        };
    }
}
