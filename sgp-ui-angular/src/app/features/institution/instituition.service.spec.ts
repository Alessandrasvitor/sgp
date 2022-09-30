/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { InstituitionService } from './instituition.service';

describe('Service: Instituition', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [InstituitionService]
    });
  });

  it('should ...', inject([InstituitionService], (service: InstituitionService) => {
    expect(service).toBeTruthy();
  }));
});
