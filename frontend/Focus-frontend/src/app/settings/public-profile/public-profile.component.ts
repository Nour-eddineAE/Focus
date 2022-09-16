import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AvatarURL } from 'src/app/model/public-profile.model';
import { EventHolderService } from 'src/app/services/events/event-holder.service';
import { UserService } from 'src/app/services/users/user.service';
@Component({
  selector: 'app-public-profile',
  templateUrl: './public-profile.component.html',
  styleUrls: ['./public-profile.component.scss'],
})
export class PublicProfileComponent implements OnInit, OnDestroy {
  userProfilePictureURL = this.userService.getAvatarUrl();
  editAvatar: boolean = false;
  onAvatarChangedSub!: Subscription;
  constructor(
    public userService: UserService,
    private router: Router,
    private eventHolder: EventHolderService
  ) {}

  ngOnInit(): void {
    //initialize the URL from userService
    this.userProfilePictureURL = this.userService.getAvatarUrl();

    //get the updated data each time we change the avatar
    this.onAvatarChangedSub = this.eventHolder.onAvatarChanged$.subscribe({
      next: ($event: AvatarURL) => {
        this.userProfilePictureURL = $event.avatarUrl;
      },
    });
  }
  ngOnDestroy(): void {
    this.onAvatarChangedSub.unsubscribe();
  }
}
