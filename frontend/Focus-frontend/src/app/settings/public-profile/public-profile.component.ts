import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EventHolderService } from 'src/app/services/events/event-holder.service';
import { UserService } from 'src/app/services/users/user.service';

@Component({
  selector: 'app-public-profile',
  templateUrl: './public-profile.component.html',
  styleUrls: ['./public-profile.component.scss'],
})
export class PublicProfileComponent implements OnInit {
  userProfilePictureURL = this.userService.getAvatarUrl();
  editAvatar: boolean = false;
  progress!: number;
  private selectedAvatar!: FileList | null;
  private currentUploadedFile!: File;

  constructor(
    public userService: UserService,
    private router: Router,
    private eventHolder: EventHolderService
  ) {}

  ngOnInit(): void {
    // this.eventHolder.onAvatarChanged$.next({
    //   avatarUrl: this.userService.getAvatarUrl(),
    // });
  }
  onEditAvatar() {
    this.editAvatar = true;
  }

  onSelectAvatar(event: Event) {
    this.selectedAvatar = (<HTMLInputElement>event.target).files;
  }

  uploadAvatar() {
    this.progress = 0;
    if (this.selectedAvatar) {
      this.currentUploadedFile = this.selectedAvatar[0];

      this.userService.uploadFile(this.currentUploadedFile).subscribe({
        next: (response) => {
          if (response.type === HttpEventType.UploadProgress) {
            this.progress = Math.round(
              (100 * response.loaded) / response.total!
            );
          } else if (response instanceof HttpResponse) {
            this.userService.timeStamp = Date.now();
            this.userProfilePictureURL = this.userService.getAvatarUrl();

            //report the change to the other components
            this.eventHolder.onAvatarChanged$.next({
              avatarUrl: this.userService.getAvatarUrl(),
            });
          }
        },
        error: (error: Error) => {
          alert('Upload problem ');
        },
      });
    }
  }
}
