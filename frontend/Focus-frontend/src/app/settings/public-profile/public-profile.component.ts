import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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

  constructor(public userService: UserService, private router: Router) {}

  ngOnInit(): void {
    // this.avatarURL.emit({
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
            // window.location.reload();
            this.userService.timeStamp = Date.now();
            this.userProfilePictureURL = this.userService.getAvatarUrl();
            // this.avatarURL.emit({
            //   avatarUrl: this.userService.getAvatarUrl(),
            // });
          }
        },
        error: (error: Error) => {
          alert('Upload problem ');
        },
      });
    }
  }
}
