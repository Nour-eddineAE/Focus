import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { AvatarURL } from 'src/app/model/public-profile.model';

@Injectable({
  providedIn: 'root',
})
export class EventHolderService {
  onAvatarChanged$ = new Subject<AvatarURL>();
  constructor() {}
}
