import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { SideNavToggle } from '../model/sidenavToggle.model';
import { EventHolderService } from '../services/events/event-holder.service';
import { UserService } from '../services/users/user.service';

@Component({
  selector: 'app-user-template',
  templateUrl: './user-template.component.html',
  styleUrls: ['./user-template.component.scss'],
})
export class UserTemplateComponent implements OnInit {
  progress!: number;
  private selectedAvatar!: FileList | null;
  private currentUploadedFile!: File;

  constructor(
    private eventHolder: EventHolderService,
    private userService: UserService
  ) {}

  ngOnInit(): void {}
  isSideNavCollapsed = false;
  screenWidth = 0;
  onToggleSideNav(data: SideNavToggle): void {
    this.screenWidth = data.screenWidth;
    this.isSideNavCollapsed = data.collapsed;
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
